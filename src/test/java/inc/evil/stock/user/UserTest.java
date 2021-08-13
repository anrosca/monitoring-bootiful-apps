package inc.evil.stock.user;

import inc.evil.stock.util.jpa.AbstractEqualityCheckTest;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Testcontainers
public class UserTest extends AbstractEqualityCheckTest<User> {

    @Container
    public static PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>(DockerImageName.parse("postgres:13.3"));

    @DynamicPropertySource
    public static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
    }

    @Test
    public void equalsAndHashCodeShouldBeJpaCompliant() {
        User user = User.builder()
                .firstName("Mike")
                .lastName("Smith")
                .userName("msmith")
                .password("1234")
                .email(new Email("pikey@gmail.com"))
                .enabled(true)
                .build();

        assertEqualityConsistency(User.class, user);
    }
}
