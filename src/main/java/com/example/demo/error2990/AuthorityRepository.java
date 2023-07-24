package com.example.demo.error2990;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Long> {

    @Transactional(readOnly = true)
    @Query(name =  Authority.NAMED_QUERY_FIND_NON_PENDING_AUTHORITY_ROLE_LIST)
    List<AuthorityRoleDTO> findNonPendingAuthorityRoleList();
}
