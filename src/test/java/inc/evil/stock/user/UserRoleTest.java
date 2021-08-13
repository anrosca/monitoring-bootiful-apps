package inc.evil.stock.user;

import inc.evil.stock.util.jpa.AbstractEqualityCheckTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Testcontainers
public class UserRoleTest extends AbstractEqualityCheckTest<UserRole> {

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
    private UserRepository userRepository;

    @Test
    public void equalsAndHashCodeShouldBeJpaCompliant() {
        UserRole user = UserRole.builder()
                .roleName("read")
                .user(userRepository.findByUserName("mike")
                        .orElseThrow(() -> new UserNotFoundException("No user with username 'mike' was found. Check test data")))
                .build();

        assertEqualityConsistency(UserRole.class, user);
    }
}
