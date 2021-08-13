package inc.evil.stock.profit;

import inc.evil.stock.investment.MonetaryAmount;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ProfitSummaryDto {
    private MonetaryAmount totalProfit;
    private List<ProfitSummaryComponentDto> profitSummaryComponents;
    private MonetaryAmount totalSpent;
    private MonetaryAmount currentWorth;

    public ProfitSummaryDto() {
    }

    private ProfitSummaryDto(ProfitSummaryDtoBuilder builder) {
        this.totalProfit = builder.totalProfit;
        this.profitSummaryComponents = builder.profitSummaryComponents;
        this.totalSpent = builder.totalSpent;
        this.currentWorth = builder.currentWorth;
    }

    public MonetaryAmount getTotalProfit() {
        return totalProfit;
    }

    public void setTotalProfit(MonetaryAmount totalProfit) {
        this.totalProfit = totalProfit;
    }

    public List<ProfitSummaryComponentDto> getProfitSummaryComponents() {
        return profitSummaryComponents;
    }

    public void setProfitSummaryComponents(List<ProfitSummaryComponentDto> profitSummaryComponents) {
        this.profitSummaryComponents = profitSummaryComponents;
    }

    public MonetaryAmount getTotalSpent() {
        return totalSpent;
    }

    public void setTotalSpent(MonetaryAmount totalSpent) {
        this.totalSpent = totalSpent;
    }

    public MonetaryAmount getCurrentWorth() {
        return currentWorth;
    }

    public void setCurrentWorth(MonetaryAmount currentWorth) {
        this.currentWorth = currentWorth;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProfitSummaryDto that = (ProfitSummaryDto) o;
        return totalProfit.equals(that.totalProfit) && profitSummaryComponents.equals(that.profitSummaryComponents);
    }

    @Override
    public int hashCode() {
        return Objects.hash(totalProfit, profitSummaryComponents);
    }

    @Override
    public String toString() {
        return "ProfitSummaryDto{" +
                "totalProfit=" + totalProfit +
                ", profitSummaryComponents=" + profitSummaryComponents +
                ", totalSpent=" + totalSpent +
                ", currentWorth=" + currentWorth +
                '}';
    }

    public static ProfitSummaryDto from(ProfitSummary profitSummary) {
        List<ProfitSummaryComponentDto> summaryComponents = profitSummary.getProfitComponents()
                .stream()
                .map(ProfitSummaryComponentDto::from)
                .collect(Collectors.toList());
        return ProfitSummaryDto.builder()
                .totalProfit(profitSummary.getTotalProfit())
                .profitSummaryComponents(summaryComponents)
                .totalSpent(profitSummary.getTotalSpent())
                .currentWorth(profitSummary.getCurrentWorth())
                .build();
    }

    public static ProfitSummaryDtoBuilder builder() {
        return new ProfitSummaryDtoBuilder();
    }

    public static class ProfitSummaryDtoBuilder {
        private MonetaryAmount totalProfit;
        private List<ProfitSummaryComponentDto> profitSummaryComponents;
        private MonetaryAmount totalSpent;
        private MonetaryAmount currentWorth;

        public ProfitSummaryDtoBuilder totalProfit(MonetaryAmount totalProfit) {
            this.totalProfit = totalProfit;
            return this;
        }

        public ProfitSummaryDtoBuilder profitSummaryComponents(List<ProfitSummaryComponentDto> profitSummaryComponents) {
            this.profitSummaryComponents = profitSummaryComponents;
            return this;
        }

        public ProfitSummaryDtoBuilder totalSpent(MonetaryAmount totalSpent) {
            this.totalSpent = totalSpent;
            return this;
        }

        public ProfitSummaryDtoBuilder currentWorth(MonetaryAmount currentWorth) {
            this.currentWorth = currentWorth;
            return this;
        }

        public ProfitSummaryDto build() {
            return new ProfitSummaryDto(this);
        }
    }
}
