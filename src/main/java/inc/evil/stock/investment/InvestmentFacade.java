package inc.evil.stock.investment;

import inc.evil.stock.config.caching.CacheNames;
import inc.evil.stock.profit.ProfitCalculator;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@CacheConfig(cacheNames = CacheNames.INVESTMENT_CACHE)
public class InvestmentFacade {
    private final InvestmentService investmentService;
    private final ProfitCalculator profitCalculator;
    private final InvestmentMapper investmentMapper;

    public InvestmentFacade(InvestmentService investmentService, ProfitCalculator profitCalculator, InvestmentMapper investmentMapper) {
        this.investmentService = investmentService;
        this.profitCalculator = profitCalculator;
        this.investmentMapper = investmentMapper;
    }

    public Page<InvestmentDto> findAllInvestmentsForUser(String userId, Pageable pageable) {
        return investmentService.findAllInvestmentsForUser(userId, pageable)
                .map(investmentMapper::fromEntity);
    }

    @Cacheable
    public InvestmentDto findById(String investmentId) {
        return investmentMapper.fromEntity(investmentService.findById(investmentId));
    }

    @CachePut(key = "#result.id")
    public InvestmentDto create(String userId, CreateInvestmentDto createInvestmentDto) {
        return investmentMapper.fromEntity(investmentService.create(userId, createInvestmentDto.toInvestment()));
    }

    public InvestmentProfitDto getProfitForInvestment(String investmentId) {
        InvestmentProfit investmentProfit = profitCalculator.calculateForInvestment(investmentId);
        return InvestmentProfitDto.from(investmentProfit);
    }
}
