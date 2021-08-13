package inc.evil.stock.profit;

import inc.evil.stock.stock.StockMetaData;
import inc.evil.stock.stock.Price;
import inc.evil.stock.investment.Investment;
import inc.evil.stock.investment.InvestmentProfit;
import inc.evil.stock.investment.MonetaryAmount;

import java.math.BigDecimal;
import java.util.*;

public class ProfitSummary {
    private final Map<Investment, InvestmentProfit> profits;

    public ProfitSummary(Map<Investment, InvestmentProfit> profits) {
        this.profits = profits;
    }

    public MonetaryAmount getTotalProfit() {
        return profits.values()
                .stream()
                .map(InvestmentProfit::getPureProfit)
                .reduce(new MonetaryAmount(BigDecimal.ZERO, getCurrency(profits.values())), MonetaryAmount::plus);
    }

    public MonetaryAmount getCurrentWorth() {
        return profits.values()
                .stream()
                .map(InvestmentProfit::getCurrentPrice)
                .map(price -> new MonetaryAmount(price.getValue(), price.getCurrency()))
                .reduce(new MonetaryAmount(BigDecimal.ZERO, getCurrency(profits.values())), MonetaryAmount::plus);
    }

    public MonetaryAmount getTotalSpent() {
        return profits.values()
                .stream()
                .map(profit -> new MonetaryAmount(profit.getTotalSpent(), getCurrency(profits.values())))
                .reduce(new MonetaryAmount(BigDecimal.ZERO, getCurrency(profits.values())), MonetaryAmount::plus);
    }

    private String getCurrency(Collection<InvestmentProfit> values) {
        return values.stream()
                .map(InvestmentProfit::getStockMetaData)
                .map(StockMetaData::getPrice)
                .map(Price::getCurrency)
                .distinct()
                .findAny()
                .orElse("USD");
    }

    public List<InvestmentProfit> getProfitComponents() {
        return new ArrayList<>(profits.values());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProfitSummary that = (ProfitSummary) o;
        return profits.equals(that.profits);
    }

    @Override
    public int hashCode() {
        return Objects.hash(profits);
    }
}
