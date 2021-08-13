package inc.evil.stock.investment;

import inc.evil.stock.stock.StockMetaData;
import inc.evil.stock.stock.Price;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Objects;

public class InvestmentProfit {
    private final String stockSymbol;
    private final BigDecimal totalAmount;
    private final BigDecimal totalSpent;
    private final StockMetaData stockMetaData;
    private final String investmentId;
    private final Currency currency;

    private InvestmentProfit(InvestmentProfitBuilder builder) {
        this.stockSymbol = builder.stockSymbol;
        this.totalAmount = builder.totalAmount;
        this.totalSpent = builder.totalSpent;
        this.stockMetaData = builder.stockMetaData;
        this.investmentId = builder.investmentId;
        this.currency = builder.currency;
    }

    public String getStockSymbol() {
        return stockSymbol;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public BigDecimal getTotalSpent() {
        return totalSpent;
    }

    public StockMetaData getStockMetaData() {
        return stockMetaData;
    }

    public Price getCurrentPrice() {
        if (totalAmount.equals(BigDecimal.ZERO)) {
            return new Price(BigDecimal.ZERO, currency.toString());
        }
        Price currentPrice = stockMetaData.getPrice();
        return new Price(currentPrice.getValue().multiply(totalAmount), currentPrice.getCurrency());
    }

    public MonetaryAmount getPureProfit() {
        Price currentPrice = getCurrentPrice();
        return new MonetaryAmount(currentPrice.getValue().subtract(getTotalSpent()), currentPrice.getCurrency());
    }

    public String getInvestmentId() {
        return investmentId;
    }

    public Currency getCurrency() {
        return currency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InvestmentProfit that = (InvestmentProfit) o;
        return stockSymbol.equals(that.stockSymbol) && totalAmount.equals(that.totalAmount) && totalSpent.equals(that.totalSpent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stockSymbol, totalAmount, totalSpent);
    }

    @Override
    public String toString() {
        return "InvestmentProfit{" +
                "symbol='" + stockSymbol + '\'' +
                ", totalAmount=" + totalAmount +
                ", totalSpent=" + totalSpent +
                '}';
    }

    public static InvestmentProfitBuilder builder() {
        return new InvestmentProfitBuilder();
    }

    public static class InvestmentProfitBuilder {
        private String stockSymbol;
        private BigDecimal totalAmount = BigDecimal.ZERO;
        private BigDecimal totalSpent = BigDecimal.ZERO;
        private StockMetaData stockMetaData;
        private String investmentId;
        private Currency currency;

        public InvestmentProfitBuilder stockSymbol(String stockSymbol) {
            this.stockSymbol = stockSymbol;
            return this;
        }

        public InvestmentProfitBuilder totalAmount(BigDecimal totalAmount) {
            this.totalAmount = totalAmount;
            return this;
        }

        public InvestmentProfitBuilder totalSpent(BigDecimal totalSpent) {
            this.totalSpent = totalSpent;
            return this;
        }

        public InvestmentProfitBuilder stockMetaData(StockMetaData stockMetaData) {
            this.stockMetaData = stockMetaData;
            return this;
        }

        public InvestmentProfitBuilder investmentId(String investmentId) {
            this.investmentId = investmentId;
            return this;
        }

        public InvestmentProfitBuilder currency(Currency currency) {
            this.currency = currency;
            return this;
        }

        public InvestmentProfit build() {
            return new InvestmentProfit(this);
        }
    }
}
