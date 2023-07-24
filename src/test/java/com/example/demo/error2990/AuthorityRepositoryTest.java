package com.example.demo.error2990;

import com.example.demo.error2990.AuthorityRepository;
import com.example.demo.error2990.AuthorityRoleDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static com.example.demo.error2990.AuthorityStatus.ACTIVE;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AuthorityRepositoryTest {
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
    private AuthorityRepository repo;


    @Sql(statements = {
            "INSERT INTO au_role (id, name, code) VALUES (1, 'name', 'code')",
            "INSERT INTO au_authority (id, user_id, code, status, creation_date) VALUES (1, 'user1', 'code', 'ACTIVE', NOW())",
            "INSERT INTO au_authority (id, user_id, code, status, creation_date) VALUES (2, 'user2', 'code', 'PENDING', NOW())",

    })
    @Test
    void findNonPendingAuthorityRoleList() {
        List<AuthorityRoleDTO> actualAuthorityRoleList = repo.findNonPendingAuthorityRoleList();

        //assert
        assertThat(actualAuthorityRoleList).hasSize(1);
        assertThat(actualAuthorityRoleList.get(0).getUserId()).isEqualTo("user1");
        assertThat(actualAuthorityRoleList.get(0).getRoleName()).isEqualTo("name");
        assertThat(actualAuthorityRoleList.get(0).getRoleCode()).isEqualTo("code");
        assertThat(actualAuthorityRoleList.get(0).getAuthorityStatus()).isEqualTo(ACTIVE);
        assertThat(actualAuthorityRoleList.get(0).getCreationDate()).isNotNull();
    }
}
