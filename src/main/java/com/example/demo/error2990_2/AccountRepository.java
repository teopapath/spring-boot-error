package com.example.demo.error2990_2;

import com.example.demo.error2990.Authority;
import com.example.demo.error2990.AuthorityRoleDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    @Transactional(readOnly = true)
    @Query(name =  Account.NAMED_QUERY_FIND_ACCOUNT_CONTACTS_BY_ACCOUNT_IDS_AND_CONTACT_TYPE)
    List<AccountContactInfoDTO> findAccountContactsByAccountIdsAndContactType(List<Long> accountIds, AccountContactType contactType);

    @Transactional(readOnly = true)
    @Query(name = Account.NAMED_QUERY_FIND_ACCOUNT_CONTACTS_BY_ACCOUNT_IDS_AND_CONTACT_TYPE)
    Page<AccountContactInfoDTO> findPageAccountContactsByAccountIdsAndContactType(
            Pageable pageable, List<Long> accountIds, AccountContactType contactType);
}
