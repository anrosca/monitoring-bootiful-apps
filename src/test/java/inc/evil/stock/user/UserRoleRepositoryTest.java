package inc.evil.stock.user;

import inc.evil.stock.util.assertions.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.List;

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRoleRepositoryTest {

    @Container
    public static PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>(DockerImageName.parse("postgres:13.3"));

    @DynamicPropertySource
    public static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
    }

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Test
    public void shouldBeAbleToFindUserRolesByUserName() {
        List<UserRole> expectedRoles = List.of(
                UserRole.builder()
                        .roleName("read")
                        .build(),
                UserRole.builder()
                        .roleName("write")
                        .build()
        );

        List<UserRole> actualRoles = userRoleRepository.findRolesForUserName("mike");

        Assertions.assertEquals(expectedRoles, actualRoles, UserRole.ROLE_NAME_COMPARATOR);
    }
}
