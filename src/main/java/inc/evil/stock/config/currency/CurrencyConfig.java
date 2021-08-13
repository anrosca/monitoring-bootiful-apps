package inc.evil.stock.config.currency;

import inc.evil.stock.currency.CurrencyProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(CurrencyProperties.class)
public class CurrencyConfig {
}
