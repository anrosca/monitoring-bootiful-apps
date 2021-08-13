package inc.evil.stock.investment;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class InvestmentRecordService {
    private final InvestmentRecordRepository investmentRecordRepository;
    private final InvestmentService investmentService;

    public InvestmentRecordService(InvestmentRecordRepository investmentRecordRepository, InvestmentService investmentService) {
        this.investmentRecordRepository = investmentRecordRepository;
        this.investmentService = investmentService;
    }

    @Transactional(readOnly = true)
    public Page<InvestmentRecord> findAllInvestmentRecordsForInvestment(String investmentId, Pageable pageable) {
        return investmentRecordRepository.findInvestmentRecordsForInvestment(investmentId, pageable);
    }

    @Transactional(readOnly = true)
    public List<InvestmentRecord> findInvestmentRecordsByUserId(String userId) {
        return investmentRecordRepository.findInvestmentRecordsByUserId(userId);
    }

    @Transactional(readOnly = true)
    public List<InvestmentRecord> findInvestmentRecordsForInvestment(String investmentId) {
        return investmentRecordRepository.findInvestmentRecordsForInvestment(investmentId);
    }

    @Transactional(readOnly = true)
    public InvestmentRecord findInvestmentRecordById(String investmentRecordId) {
        return investmentRecordRepository.findById(investmentRecordId)
                .orElseThrow(() -> new InvestmentRecordNotFoundException("No investment record with id: '" + investmentRecordId + "' was found."));
    }

    @Transactional
    public InvestmentRecord createInvestmentRecordForInvestment(InvestmentRecord investmentRecord, String investmentId) {
        investmentRecord.setInvestment(investmentService.findById(investmentId));
        return investmentRecordRepository.save(investmentRecord);
    }

    @Transactional
    public void deleteInvestmentRecordById(String investmentRecordId) {
        log.debug("Deleting investment record with id: {}", investmentRecordId);
        investmentRecordRepository.deleteById(investmentRecordId);
    }
}
