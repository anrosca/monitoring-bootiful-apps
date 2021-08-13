package inc.evil.stock.profit;

import org.springframework.stereotype.Component;

@Component
public class ProfitFacade {
    private final ProfitCalculator profitCalculator;
    private final ProfitSummaryMapper profitSummaryMapper;

    public ProfitFacade(ProfitCalculator profitCalculator, ProfitSummaryMapper profitSummaryMapper) {
        this.profitCalculator = profitCalculator;
        this.profitSummaryMapper = profitSummaryMapper;
    }

    public ProfitSummaryDto getTotalProfitsFor(String userId) {
        ProfitSummary profitSummary = profitCalculator.calculateForUser(userId);
        return profitSummaryMapper.fromEntity(profitSummary);
    }
}
