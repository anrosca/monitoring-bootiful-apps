package inc.evil.stock.currency;

import org.hibernate.validator.constraints.Currency;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties("currency")
@Validated
public class CurrencyProperties {
    private final String defaultCurrency;

    @ConstructorBinding
    public CurrencyProperties(@Currency({"EUR", "USD"}) String defaultCurrency) {
        this.defaultCurrency = defaultCurrency;
    }

    public String getDefaultCurrency() {
        return defaultCurrency;
    }
}
