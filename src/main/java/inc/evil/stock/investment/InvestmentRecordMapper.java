package inc.evil.stock.investment;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class InvestmentRecordMapper {
    public InvestmentRecordDto fromEntity(InvestmentRecord investmentRecord) {
        return InvestmentRecordDto.from(investmentRecord);
    }

    public List<InvestmentRecordDto> fromEntities(List<InvestmentRecord> investmentRecords) {
        return investmentRecords.stream()
                .map(this::fromEntity)
                .collect(Collectors.toList());
    }

    public Page<InvestmentRecordDto> fromEntities(Page<InvestmentRecord> investmentRecords) {
        return investmentRecords.map(this::fromEntity);
    }
}
