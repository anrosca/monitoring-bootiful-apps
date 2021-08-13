package inc.evil.stock.investment;

import inc.evil.stock.user.UserNotFoundException;
import inc.evil.stock.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class InvestmentRepositoryTest {

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
    private InvestmentRepository investmentRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void shouldBeAbleToFindUserInvestments() {
        List<Investment> expectedInvestments = List.of(
                Investment.builder()
                        .id("1234f3d2-56f3-518a-5d24-949bd074669a")
                        .name("Tesla investment")
                        .build(),
                Investment.builder()
                        .id("1da1f05d-344e-477c-b095-6529f0d756c1")
                        .name("Apple investment")
                        .build(),
                Investment.builder()
                        .id("7ddff3d2-56f3-518a-5d24-949bd07466ab")
                        .name("Endava investment")
                        .build(),
                Investment.builder()
                        .id("5ddff3d2-56f3-518a-5d24-949bd074669b")
                        .name("Google investment")
                        .build(),
                Investment.builder()
                        .id("6ddff3d2-56f3-518a-5d24-949bd07466bb")
                        .name("Facebook investment")
                        .build(),
                Investment.builder()
                        .id("99dff3d2-56f3-518a-5d24-949bd07466ab")
                        .name("Netflix investment")
                        .build(),
                Investment.builder()
                        .id("88dff3d2-56f3-518a-5d24-949bd07466ab")
                        .name("Amazon investment")
                        .build()
                );

        List<Investment> actualInvestments = investmentRepository.findAllInvestmentsForUser("5284f3d2-92d6-438a-8e24-949bd074662a");

        assertThat(actualInvestments).isEqualTo(expectedInvestments);
    }

    @Test
    public void shouldBeAbleToFindUserInvestmentsWithPagination() {
        List<Investment> investments = List.of(
                Investment.builder()
                        .id("1234f3d2-56f3-518a-5d24-949bd074669a")
                        .name("Tesla investment")
                        .build(),
                Investment.builder()
                        .id("1da1f05d-344e-477c-b095-6529f0d756c1")
                        .name("Apple investment")
                        .build()
        );
        Page<Investment> expectedPage = new PageImpl<>(investments, PageRequest.of(0, 2), 7);

        Page<Investment> actualPage = investmentRepository.findAllInvestmentsForUser("5284f3d2-92d6-438a-8e24-949bd074662a", PageRequest.of(0, 2));

        assertThat(actualPage).isEqualTo(expectedPage);
    }

    @Test
    public void whenUserHasNoInvestments_shouldReturnEmptyList() {
        List<Investment> actualInvestments = investmentRepository.findAllInvestmentsForUser("<non-existing-user-id>");

        assertThat(actualInvestments).isEqualTo(Collections.emptyList());
    }

    @Test
    public void whenUserHasNoInvestments_shouldReturnEmptyListWithPagination() {
        Page<Investment> actualPage = investmentRepository.findAllInvestmentsForUser("<non-existing-user-id>", PageRequest.of(0, 2));

        assertThat(actualPage).isEqualTo(new PageImpl<>(Collections.emptyList(), PageRequest.of(0, 2), 0));
    }

    @Test
    public void shouldBeAbleToSaveNewInvestments() {
        Investment investment = Investment.builder()
                .name("SpaceX investment")
                .symbol("TYO")
                .user(userRepository.findByUserName("mike").orElseThrow(() -> new UserNotFoundException("User with username 'mike' was not found. Check test data")))
                .build();

        Investment savedInvestment = investmentRepository.saveAndFlush(investment);

        assertThat(savedInvestment.getId()).isNotNull();
    }
}
