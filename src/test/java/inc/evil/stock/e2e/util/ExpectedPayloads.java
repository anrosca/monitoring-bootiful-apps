package inc.evil.stock.e2e.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
public class ExpectedPayloads implements ParameterResolver {
    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().isAnnotationPresent(ExpectedPayload.class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        ExpectedPayload expectedPayload = parameterContext.getParameter().getAnnotation(ExpectedPayload.class);
        String payloadLocation = expectedPayload.value();
        return getPayload(payloadLocation);
    }

    private String getPayload(String payloadLocation) {
        try {
            return StreamUtils.copyToString(getClass().getResourceAsStream(payloadLocation), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
