package com.example.demo.error3133;

import com.example.demo.error2990_2.Account;
import com.example.demo.error2990_2.AccountContactInfoDTO;
import com.example.demo.error2990_2.AccountContactType;
import com.example.demo.error2990_2.AccountRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AccountRepositoryTest {
    @Container
    private static PostgreSQLContainer postgresqlContainer = new PostgreSQLContainer()
            .withDatabaseName("tests-db")
            .withUsername("sa")
            .withPassword("sa");



    @DynamicPropertySource
    static void postgresqlProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgresqlContainer::getJdbcUrl);
        registry.add("spring.datasource.password", postgresqlContainer::getPassword);
        registry.add("spring.datasource.username", postgresqlContainer::getUsername);
    }

    @Autowired
    private AccountRepository repo;

    @Autowired
    private EntityManager entityManager;

    @Test
    void findAccountContactsByAccountIdsAndContactType() {
        Account account1 = createAccount(1L, "account1");
        account1.getContacts().put(AccountContactType.PRIMARY, "primary1");
        account1.getContacts().put(AccountContactType.SECONDARY, "secondary1");
        repo.save(account1);

        Account account2 = createAccount(2L, "account2");
        account2.getContacts().put(AccountContactType.PRIMARY, "primary2");
        account2.getContacts().put(AccountContactType.SECONDARY, "secondary2");
        repo.save(account2);

        Account account3 = createAccount(3L, "account3");
        account3.getContacts().put(AccountContactType.PRIMARY, "primary3");
        account3.getContacts().put(AccountContactType.SECONDARY, "secondary2");
        repo.save(account3);

        List<Long> accountIds = List.of(account1.getId(), account2.getId(), account3.getId());

        flushAndClear();

        //invoke
        Page<AccountContactInfoDTO> result = repo.findPageAccountContactsByAccountIdsAndContactType(PageRequest.of(0, 2), accountIds,
                AccountContactType.PRIMARY);


        //verify
        assertThat(result).hasSize(2);
    }

    private Account createAccount(Long id, String accountName) {
        Account account = Account.builder()
                .id(id)
                .name(accountName)
                .build();
        entityManager.persist(account);
        return account;
    }

    private void flushAndClear() {
        entityManager.flush();
        entityManager.clear();
    }
}
