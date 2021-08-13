package inc.evil.stock.profit;

import org.springframework.stereotype.Component;

@Component
public class ProfitSummaryMapper {
    public ProfitSummaryDto fromEntity(ProfitSummary profitSummary) {
        return ProfitSummaryDto.from(profitSummary);
    }
}
