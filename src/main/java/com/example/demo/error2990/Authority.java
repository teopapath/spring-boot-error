package com.example.demo.error2990;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@EntityListeners({AuditingEntityListener.class})
@Table(name = "au_authority")
@NamedQuery(
        name = Authority.NAMED_QUERY_FIND_NON_PENDING_AUTHORITY_ROLE_LIST,
        query = "select new com.example.demo.error2990.AuthorityRoleDTO("
                + "au.userId, au.status, r.name, r.code, au.creationDate) "
                + "from Authority au "
                + "join Role r on r.code = au.code "
                + "where au.status <> 'PENDING' ")
public class Authority {
    public static final String NAMED_QUERY_FIND_NON_PENDING_AUTHORITY_ROLE_LIST = "Authority.findNonPendingAuthorityRoleList";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The keycloak userid.
     */
    @EqualsAndHashCode.Include
    @NotNull
    @Column(name = "user_id")
    private String userId;

    @EqualsAndHashCode.Include
    @Column(name = "code")
    private String code;

    /**
     * The status of the authority.
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private AuthorityStatus status;

    @NotNull
    @Column(name = "creation_date")
    @CreatedDate
    private LocalDateTime creationDate;

}
