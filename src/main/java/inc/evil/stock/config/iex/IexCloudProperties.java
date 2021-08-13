package inc.evil.stock.config.iex;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConfigurationProperties("iex")
@ConstructorBinding
public class IexCloudProperties {
    private final String baseUrl;
    private final String apiKey;
    private final String apiKeyParameterName;

    public IexCloudProperties(String baseUrl, String apiKey, String apiKeyParameterName) {
        this.baseUrl = baseUrl;
        this.apiKey = apiKey;
        this.apiKeyParameterName = apiKeyParameterName;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public String getApiKey() {
        return apiKey;
    }

    public String getApiKeyParameterName() {
        return apiKeyParameterName;
    }

    @Override
    public String toString() {
        return "IexCloudProperties{baseUrl='" + baseUrl +
                "', apiKey='******', apiKeyParameterName='" +
                apiKeyParameterName + "'}";
    }
}
