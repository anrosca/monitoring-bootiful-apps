package inc.evil.stock.investment;

import inc.evil.stock.user.Email;
import inc.evil.stock.user.User;
import inc.evil.stock.util.jpa.AbstractEqualityCheckTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Testcontainers
public class InvestmentTest extends AbstractEqualityCheckTest<Investment> {
    @Container
    public static PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>(DockerImageName.parse("postgres:13.3"));

    private final User user = User.builder()
            .firstName("Mike")
            .lastName("Smith")
            .userName("pikey")
            .password("1234")
            .email(new Email("pikey@gmail.com"))
            .enabled(true)
            .build();

    @DynamicPropertySource
    public static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
    }

    @BeforeEach
    public void setUp() {
        doInJPA(entityManager -> {
            entityManager.persist(user);
        });
    }

    @Test
    public void equalsAndHashCodeShouldBeJpaCompliant() {
        Investment investment = Investment.builder()
                .user(user)
                .symbol("SHIB")
                .name("Shiba inu investment")
                .build();

        assertEqualityConsistency(Investment.class, investment);
    }
}
