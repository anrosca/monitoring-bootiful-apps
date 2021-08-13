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

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Testcontainers
public class InvestmentRecordTest extends AbstractEqualityCheckTest<InvestmentRecord> {

    @Container
    public static PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>(DockerImageName.parse("postgres:13.3"));

    private final User user = User.builder()
            .firstName("Mike")
            .lastName("Smith")
            .userName("mikey")
            .password("1234")
            .email(new Email("pikey@gmail.com"))
            .enabled(true)
            .build();

    private final Investment investment = Investment.builder()
            .user(user)
            .symbol("ETH")
            .name("Pumpkin investment")
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
            entityManager.persist(investment);
        });
    }

    @Test
    public void equalsAndHashCodeShouldBeJpaCompliant() {
        InvestmentRecord investmentRecord = InvestmentRecord.builder()
                .amountBought(new BigDecimal("12.5"))
                .investmentDate(LocalDateTime.of(2020, 5, 19, 13, 15, 16))
                .spent(new BigDecimal("120.5"))
                .unitPrice(new BigDecimal("4096.336"))
                .symbol("ETH")
                .investment(investment)
                .build();

        assertEqualityConsistency(InvestmentRecord.class, investmentRecord);
    }
}
