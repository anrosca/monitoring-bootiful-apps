package inc.evil.stock.e2e;

import inc.evil.stock.e2e.util.ExpectedPayload;
import inc.evil.stock.e2e.util.ExpectedPayloads;
import inc.evil.stock.investment.InvestmentDto;
import inc.evil.stock.util.assertions.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@Testcontainers
@ExtendWith(ExpectedPayloads.class)
public class InvestmentsEndToEndTest {
    private static final String BASIC_AUTHENTICATION_HEADER_VALUE = "Basic bWlrZTpzaGliYQ==";
    private static final int REDIS_PORT = 6379;

    @Container
    public static PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>(DockerImageName.parse("postgres:13.3"));

    @Container
    public static GenericContainer<?> redis = new GenericContainer<>(DockerImageName.parse("redis:6.2.5"))
            .withExposedPorts(REDIS_PORT);

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    @DynamicPropertySource
    public static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.redis.host", redis::getHost);
        registry.add("spring.redis.port", redis::getFirstMappedPort);
    }

    @Test
    public void whenCreatingAnInvalidInvestmentWithNullNameField_shouldReturnErrorResponse() {
        InvestmentDto investmentToCreate = InvestmentDto.builder()
                .symbol("BTC")
                .build();
        RequestEntity<InvestmentDto> request = postRequestFor("/api/v1/investments", investmentToCreate);

        ResponseEntity<ErrorResponse> response = restTemplate.exchange(request, ErrorResponse.class);

        ErrorResponse expectedErrorResponse = ErrorResponse.builder()
                .error("Bad Request")
                .status(400)
                .path("/api/v1/investments")
                .message("Validation failed for object='createInvestmentDto'. Error count: 1")
                .errors(Set.of("Field 'name' should not be null but value was 'null'"))
                .build();
        assertThat(response.getStatusCode().value()).isEqualTo(400);
        assertThat(response.getBody()).isEqualTo(expectedErrorResponse);
    }

    @Test
    public void whenCreatingAnInvalidInvestmentWithNullSymbolField_shouldReturnErrorResponse() {
        InvestmentDto investmentToCreate = InvestmentDto.builder()
                .name("TSLA test investment")
                .build();
        RequestEntity<InvestmentDto> request = postRequestFor("/api/v1/investments", investmentToCreate);

        ResponseEntity<ErrorResponse> response = restTemplate.exchange(request, ErrorResponse.class);

        ErrorResponse expectedErrorResponse = ErrorResponse.builder()
                .error("Bad Request")
                .status(400)
                .path("/api/v1/investments")
                .message("Validation failed for object='createInvestmentDto'. Error count: 1")
                .errors(Set.of("Field 'symbol' should not be null but value was 'null'"))
                .build();
        assertThat(response.getStatusCode().value()).isEqualTo(400);
        assertThat(response.getBody()).isEqualTo(expectedErrorResponse);
    }

    @Test
    public void whenCreatingAnInvalidInvestmentWithIncorrectLengthSymbolField_shouldReturnErrorResponse() {
        InvestmentDto investmentToCreate = InvestmentDto.builder()
                .name("TSLA test investment")
                .symbol("")
                .build();
        RequestEntity<InvestmentDto> request = postRequestFor("/api/v1/investments", investmentToCreate);

        ResponseEntity<ErrorResponse> response = restTemplate.exchange(request, ErrorResponse.class);

        ErrorResponse expectedErrorResponse = ErrorResponse.builder()
                .error("Bad Request")
                .status(400)
                .path("/api/v1/investments")
                .message("Validation failed for object='createInvestmentDto'. Error count: 1")
                .errors(Set.of("Field 'symbol' should be between 1 and 10 characters but value was ''"))
                .build();
        assertThat(response.getStatusCode().value()).isEqualTo(400);
        assertThat(response.getBody()).isEqualTo(expectedErrorResponse);
    }

    @Test
    public void whenCreatingAnInvalidInvestmentWithIncorrectLengthNameField_shouldReturnErrorResponse() {
        InvestmentDto investmentToCreate = InvestmentDto.builder()
                .name("")
                .symbol("ETH")
                .build();
        RequestEntity<InvestmentDto> request = postRequestFor("/api/v1/investments", investmentToCreate);

        ResponseEntity<ErrorResponse> response = restTemplate.exchange(request, ErrorResponse.class);

        ErrorResponse expectedErrorResponse = ErrorResponse.builder()
                .error("Bad Request")
                .status(400)
                .path("/api/v1/investments")
                .message("Validation failed for object='createInvestmentDto'. Error count: 1")
                .errors(Set.of("Field 'name' should be between 1 and 200 characters but value was ''"))
                .build();
        assertThat(response.getStatusCode().value()).isEqualTo(400);
        assertThat(response.getBody()).isEqualTo(expectedErrorResponse);
    }

    @Test
    public void whenCreatingAnInvalidInvestmentWithAllFieldsAsNull_shouldReturnErrorResponse() {
        InvestmentDto investmentToCreate = InvestmentDto.builder()
                .build();
        RequestEntity<InvestmentDto> request = postRequestFor("/api/v1/investments", investmentToCreate);

        ResponseEntity<ErrorResponse> response = restTemplate.exchange(request, ErrorResponse.class);

        ErrorResponse expectedErrorResponse = ErrorResponse.builder()
                .error("Bad Request")
                .status(400)
                .path("/api/v1/investments")
                .message("Validation failed for object='createInvestmentDto'. Error count: 2")
                .errors(Set.of(
                        "Field 'symbol' should not be null but value was 'null'",
                        "Field 'name' should not be null but value was 'null'"
                ))
                .build();
        assertThat(response.getStatusCode().value()).isEqualTo(400);
        assertThat(response.getBody()).isEqualTo(expectedErrorResponse);
    }

    @Test
    public void shouldBeAbleToGetInvestmentById(@ExpectedPayload("/e2e/investment-by-id.json") String expectedPayload) {
        RequestEntity<Void> request = getRequestFor("/api/v1/investments/1234f3d2-56f3-518a-5d24-949bd074669a");

        ResponseEntity<String> response = restTemplate.exchange(request, String.class);

        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        Assertions.assertJsonEquals(expectedPayload, response.getBody());
    }

    @Test
    public void shouldBeAbleToGetAllInvestments(@ExpectedPayload("/e2e/all-investments-response.json") String expectedPayload) {
        RequestEntity<Void> request = getRequestFor("/api/v1/investments");

        ResponseEntity<String> response = restTemplate.exchange(request, String.class);

        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        Assertions.assertJsonEquals(expectedPayload, response.getBody());
    }

    @Test
    public void shouldBeAbleToGetTheFirstPageOfAllInvestments_withSizeTwo(@ExpectedPayload("/e2e/all-paginated-investments-response.json") String expectedPayload) {
        RequestEntity<Void> request = getRequestFor("/api/v1/investments?size=2");

        ResponseEntity<String> response = restTemplate.exchange(request, String.class);

        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        Assertions.assertJsonEquals(expectedPayload, response.getBody());
    }

    private RequestEntity<Void> getRequestFor(String urlTemplate) {
        return RequestEntity.get("http://localhost:{port}" + urlTemplate, port)
                .header(HttpHeaders.AUTHORIZATION, BASIC_AUTHENTICATION_HEADER_VALUE)
                .build();
    }

    private <T> RequestEntity<T> postRequestFor(String urlTemplate, T payload) {
        return RequestEntity.post("http://localhost:{port}" + urlTemplate, port)
                .header(HttpHeaders.AUTHORIZATION, BASIC_AUTHENTICATION_HEADER_VALUE)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(payload);
    }
}
