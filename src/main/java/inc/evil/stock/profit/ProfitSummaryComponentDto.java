package inc.evil.stock.profit;

import inc.evil.stock.investment.InvestmentProfit;
import inc.evil.stock.investment.MonetaryAmount;

import java.util.Objects;

public class ProfitSummaryComponentDto {
    private String stockSymbol;
    private MonetaryAmount profit;

    public ProfitSummaryComponentDto() {
    }

    public ProfitSummaryComponentDto(String stockSymbol, MonetaryAmount profit) {
        this.stockSymbol = stockSymbol;
        this.profit = profit;
    }

    public String getStockSymbol() {
        return stockSymbol;
    }

    public void setStockSymbol(String stockSymbol) {
        this.stockSymbol = stockSymbol;
    }

    public MonetaryAmount getProfit() {
        return profit;
    }

    public void setProfit(MonetaryAmount profit) {
        this.profit = profit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProfitSummaryComponentDto that = (ProfitSummaryComponentDto) o;
        return stockSymbol.equals(that.stockSymbol) && profit.equals(that.profit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stockSymbol, profit);
    }

    @Override
    public String toString() {
        return "ProfitSummaryComponentDto{" +
                "stockSymbol='" + stockSymbol + '\'' +
                ", profit=" + profit +
                '}';
    }

    public static ProfitSummaryComponentDto from(InvestmentProfit investmentProfit) {
        return new ProfitSummaryComponentDto(investmentProfit.getStockSymbol(), investmentProfit.getPureProfit());
    }
}
