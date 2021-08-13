package inc.evil.stock.util.web.wiremock;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.http.MediaType;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;

@Slf4j
public class StubEndpoints implements BeforeTestExecutionCallback {

    @Override
    public void beforeTestExecution(ExtensionContext extensionContext) throws Exception {
        StubEndpoint stubEndpoint = getStubEndpointAnnotation(extensionContext);
        if (stubEndpoint != null) {
            Object testInstance = extensionContext.getTestInstance()
                    .orElseThrow(() -> new TestInstanceNotFoundException("Test instance not found. "
                            + getClass().getSimpleName() + " is supposed to be used in a junit 5 context."));
            WireMockServer wireMockServer = getWireMockServer(testInstance);
            setUpStubEndpoint(stubEndpoint, wireMockServer);
        }
    }

    private StubEndpoint getStubEndpointAnnotation(ExtensionContext extensionContext) {
        return extensionContext.getTestMethod()
                .map(method -> method.getAnnotation(StubEndpoint.class))
                .orElse(null);
    }

    private void setUpStubEndpoint(StubEndpoint stubEndpoint, WireMockServer wireMockServer) throws IOException {
        boolean isErrorResponse = !stubEndpoint.payloadLocation().contains("success");
        wireMockServer.stubFor(WireMock.get(stubEndpoint.endpoint())
                        .willReturn(aResponse()
                                .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                                .withStatus(isErrorResponse ? 404 : 200)
                                .withBody(StreamUtils.copyToString(getClass().getResourceAsStream(stubEndpoint.payloadLocation()), StandardCharsets.UTF_8))));
    }

    private WireMockServer getWireMockServer(Object testInstance) throws Exception {
        Class<?> clazz = testInstance.getClass();
        do {
            for (Field field : clazz.getDeclaredFields()) {
                if (field.isAnnotationPresent(AutowireWireMockServer.class)) {
                    field.setAccessible(true);
                    return (WireMockServer) field.get(testInstance);
                }
            }
        } while ((clazz = clazz.getSuperclass()) != null);
        throw new WireMockServerNotFoundException("WireMockServer was not found. " +
                "Consider making your test extend AbstractWireMockIntegrationTest");
    }

    public static class WireMockServerNotFoundException extends RuntimeException {
        public WireMockServerNotFoundException(String message) {
            super(message);
        }
    }

    public static class TestInstanceNotFoundException extends RuntimeException {
        public TestInstanceNotFoundException(String message) {
            super(message);
        }
    }
}
