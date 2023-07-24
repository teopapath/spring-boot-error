package com.example.demo.error2990_2;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapKeyColumn;
import jakarta.persistence.MapKeyEnumerated;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.EnumMap;
import java.util.Map;

/**
 * The Account Entity.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "account")
@NamedQuery(
        name = Account.NAMED_QUERY_FIND_ACCOUNT_CONTACTS_BY_ACCOUNT_IDS_AND_CONTACT_TYPE,
        query = "select new com.example.demo.error2990_2.AccountContactInfoDTO(acc.id, acc.name, VALUE(contacts)) "
                + "from Account acc "
                + "join acc.contacts contacts on KEY(contacts) = :contactType "
                + "where acc.id in (:accountIds)")
public class Account {

    public static final String NAMED_QUERY_FIND_ACCOUNT_CONTACTS_BY_ACCOUNT_IDS_AND_CONTACT_TYPE = "Account.findAccountContactsByAccountIdsAndContactType";

    @Id
    private Long id;

    @Column(name = "name")
    @NotBlank
    private String name;

    @Builder.Default
    @ElementCollection
    @MapKeyColumn(name="contact_type")
    @MapKeyEnumerated(EnumType.STRING)
    @Column(name="user_id")
    @CollectionTable(name = "account_contact", joinColumns = @JoinColumn(name = "account_id"))
    private Map<AccountContactType, String> contacts = new EnumMap<>(AccountContactType.class);

}
