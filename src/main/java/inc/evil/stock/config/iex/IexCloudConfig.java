package inc.evil.stock.config.iex;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.support.HttpRequestWrapper;
import org.springframework.lang.NonNull;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.List;

@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(IexCloudProperties.class)
public class IexCloudConfig {

    @Bean
    public RestTemplate iexRestTemplate(RestTemplateBuilder restTemplateBuilder, IexCloudProperties iexCloudProperties) {
        return restTemplateBuilder
                .uriTemplateHandler(new DefaultUriBuilderFactory(iexCloudProperties.getBaseUrl()))
                .interceptors(List.of(new IexCloudApiKeyInterceptor(iexCloudProperties)))
                .errorHandler(new NoOpRestTemplateErrorHandler())
                .build();
    }

    private static class IexCloudApiKeyInterceptor implements ClientHttpRequestInterceptor {
        private final IexCloudProperties iexCloudProperties;

        private IexCloudApiKeyInterceptor(IexCloudProperties iexCloudProperties) {
            this.iexCloudProperties = iexCloudProperties;
        }

        @Override
        @NonNull
        public ClientHttpResponse intercept(@NonNull HttpRequest request, @NonNull byte[] body, @NonNull ClientHttpRequestExecution execution) throws IOException {
            URI uri = UriComponentsBuilder.fromHttpRequest(request)
                    .queryParam(iexCloudProperties.getApiKeyParameterName(), iexCloudProperties.getApiKey())
                    .build().toUri();
            HttpRequest modifiedRequest = new HttpRequestWrapper(request) {
                @Override
                @NonNull
                public URI getURI() {
                    return uri;
                }
            };
            return execution.execute(modifiedRequest, body);
        }
    }

    private static class NoOpRestTemplateErrorHandler implements ResponseErrorHandler {
        @Override
        public boolean hasError(@NonNull ClientHttpResponse clientHttpResponse) {
            return false;
        }

        @Override
        public void handleError(@NonNull ClientHttpResponse clientHttpResponse) {
        }
    }
}
