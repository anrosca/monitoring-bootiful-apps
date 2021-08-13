package inc.evil.stock.investment;

import inc.evil.stock.config.caching.CacheNames;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@CacheConfig(cacheNames = CacheNames.INVESTMENT_RECORDS_CACHE)
public class InvestmentRecordFacade {
    private final InvestmentRecordService investmentRecordService;
    private final InvestmentRecordMapper investmentRecordMapper;

    public InvestmentRecordFacade(InvestmentRecordService investmentRecordService, InvestmentRecordMapper investmentRecordMapper) {
        this.investmentRecordService = investmentRecordService;
        this.investmentRecordMapper = investmentRecordMapper;
    }

    public Page<InvestmentRecordDto> findAllInvestmentRecordsForInvestment(String investmentId, Pageable pageable) {
        Page<InvestmentRecord> investmentRecords = investmentRecordService.findAllInvestmentRecordsForInvestment(investmentId, pageable);
        return investmentRecordMapper.fromEntities(investmentRecords);
    }

    public List<InvestmentRecordDto> findInvestmentRecordsByUserId(String userId) {
        List<InvestmentRecord> investmentRecords = investmentRecordService.findInvestmentRecordsByUserId(userId);
        return investmentRecordMapper.fromEntities(investmentRecords);
    }

    public List<InvestmentRecordDto> findInvestmentRecordsForInvestment(String investmentId) {
        List<InvestmentRecord> investmentRecords = investmentRecordService.findInvestmentRecordsForInvestment(investmentId);
        return investmentRecordMapper.fromEntities(investmentRecords);
    }

    @Cacheable
    public InvestmentRecordDto findInvestmentRecordById(String investmentRecordId) {
        InvestmentRecord investmentRecord = investmentRecordService.findInvestmentRecordById(investmentRecordId);
        return investmentRecordMapper.fromEntity(investmentRecord);
    }

    @CachePut(key = "#result.id")
    public InvestmentRecordDto createInvestmentRecordForInvestment(CreateInvestmentRecordDto createInvestmentRecordDto, String investmentId) {
        InvestmentRecord createdRecord = investmentRecordService.createInvestmentRecordForInvestment(createInvestmentRecordDto.toInvestmentRecord(), investmentId);
        return investmentRecordMapper.fromEntity(createdRecord);
    }

    @CacheEvict
    public void deleteInvestmentRecordById(String investmentRecordId) {
        investmentRecordService.deleteInvestmentRecordById(investmentRecordId);
    }
}
