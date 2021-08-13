package inc.evil.stock.investment;

import inc.evil.stock.stock.Price;

import java.math.BigDecimal;

public class InvestmentProfitDto {
    private String stockSymbol;
    private BigDecimal totalAmount;
    private BigDecimal totalSpent;
    private Price currentPrice;
    private MonetaryAmount pureProfit;
    private String investmentId;

    public InvestmentProfitDto() {
    }

    private InvestmentProfitDto(InvestmentProfitDtoBuilder builder) {
        this.stockSymbol = builder.stockSymbol;
        this.totalAmount = builder.totalAmount;
        this.totalSpent = builder.totalSpent;
        this.currentPrice = builder.currentPrice;
        this.pureProfit = builder.pureProfit;
        this.investmentId = builder.investmentId;
    }

    public String getStockSymbol() {
        return stockSymbol;
    }

    public void setStockSymbol(String stockSymbol) {
        this.stockSymbol = stockSymbol;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getTotalSpent() {
        return totalSpent;
    }

    public void setTotalSpent(BigDecimal totalSpent) {
        this.totalSpent = totalSpent;
    }

    public Price getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(Price currentPrice) {
        this.currentPrice = currentPrice;
    }

    public MonetaryAmount getPureProfit() {
        return pureProfit;
    }

    public void setPureProfit(MonetaryAmount pureProfit) {
        this.pureProfit = pureProfit;
    }

    public String getInvestmentId() {
        return investmentId;
    }

    public void setInvestmentId(String investmentId) {
        this.investmentId = investmentId;
    }

    public static InvestmentProfitDtoBuilder builder() {
        return new InvestmentProfitDtoBuilder();
    }

    public static InvestmentProfitDto from(InvestmentProfit investmentProfit) {
        return InvestmentProfitDto.builder()
                .stockSymbol(investmentProfit.getStockSymbol())
                .totalAmount(investmentProfit.getTotalAmount())
                .totalSpent(investmentProfit.getTotalSpent())
                .currentPrice(investmentProfit.getCurrentPrice())
                .pureProfit(investmentProfit.getPureProfit())
                .investmentId(investmentProfit.getInvestmentId())
                .build();
    }

    public static class InvestmentProfitDtoBuilder {
        private String stockSymbol;
        private BigDecimal totalAmount;
        private BigDecimal totalSpent;
        private Price currentPrice;
        private MonetaryAmount pureProfit;
        private String investmentId;

        public InvestmentProfitDtoBuilder stockSymbol(String stockSymbol) {
            this.stockSymbol = stockSymbol;
            return this;
        }

        public InvestmentProfitDtoBuilder totalAmount(BigDecimal totalAmount) {
            this.totalAmount = totalAmount;
            return this;
        }

        public InvestmentProfitDtoBuilder totalSpent(BigDecimal totalSpent) {
            this.totalSpent = totalSpent;
            return this;
        }

        public InvestmentProfitDtoBuilder currentPrice(Price currentPrice) {
            this.currentPrice = currentPrice;
            return this;
        }

        public InvestmentProfitDtoBuilder pureProfit(MonetaryAmount pureProfit) {
            this.pureProfit = pureProfit;
            return this;
        }

        public InvestmentProfitDtoBuilder investmentId(String investmentId) {
            this.investmentId = investmentId;
            return this;
        }

        public InvestmentProfitDto build() {
            return new InvestmentProfitDto(this);
        }
    }
}
