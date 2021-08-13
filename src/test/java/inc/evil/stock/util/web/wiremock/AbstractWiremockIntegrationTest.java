package inc.evil.stock.util.web.wiremock;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.AfterEach;
import org.springframework.boot.autoconfigure.web.client.RestTemplateAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;

@WebMvcTest
@ContextConfiguration(initializers = WireMockInitializer.class, classes = RestTemplateAutoConfiguration.class)
public class AbstractWiremockIntegrationTest {

    @AutowireWireMockServer
    protected WireMockServer wireMockServer;

    @AfterEach
    public void afterEach() {
        this.wireMockServer.resetAll();
    }
}
