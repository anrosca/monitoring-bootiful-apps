package inc.evil.stock.investment;

import org.springframework.stereotype.Component;

@Component
public class InvestmentMapper {
    public InvestmentDto fromEntity(Investment investment) {
        return InvestmentDto.from(investment);
    }
}
