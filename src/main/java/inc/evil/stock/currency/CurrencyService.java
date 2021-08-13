package inc.evil.stock.currency;

import org.springframework.stereotype.Component;

import java.util.Currency;

@Component
public class CurrencyService {
    private final CurrencyProperties currencyProperties;

    public CurrencyService(CurrencyProperties currencyProperties) {
        this.currencyProperties = currencyProperties;
    }

    public Currency getConfiguredCurrency() {
        return Currency.getInstance(currencyProperties.getDefaultCurrency());
    }
}
