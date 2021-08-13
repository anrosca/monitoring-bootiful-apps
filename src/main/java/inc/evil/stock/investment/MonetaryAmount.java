package inc.evil.stock.investment;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.Objects;

public class MonetaryAmount {
    private final BigDecimal amount;
    private final String currency;

    @JsonCreator
    public MonetaryAmount(@JsonProperty("amount") BigDecimal amount, @JsonProperty("currency") String currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }

    public MonetaryAmount plus(MonetaryAmount other) {
        return new MonetaryAmount(amount.add(other.getAmount()), currency);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MonetaryAmount that = (MonetaryAmount) o;
        return amount.equals(that.amount) && currency.equals(that.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, currency);
    }

    @Override
    public String toString() {
        return amount + " " + currency;
    }
}
