package inc.evil.stock.investment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Component;

@Component
public class InvestmentRecordPresenter {
    private final InvestmentRecordFacade investmentRecordFacade;
    private final InvestmentRecordModelAssembler investmentRecordModelAssembler;

    public InvestmentRecordPresenter(InvestmentRecordFacade investmentRecordFacade, InvestmentRecordModelAssembler investmentRecordModelAssembler) {
        this.investmentRecordFacade = investmentRecordFacade;
        this.investmentRecordModelAssembler = investmentRecordModelAssembler;
    }

    public PagedModel<EntityModel<InvestmentRecordDto>> findAllInvestmentRecordsForInvestment(String investmentId, Pageable pageable) {
        Page<InvestmentRecordDto> investmentRecords = investmentRecordFacade.findAllInvestmentRecordsForInvestment(investmentId, pageable);
        return investmentRecordModelAssembler.toPagedModel(investmentRecords);
    }

    public EntityModel<InvestmentRecordDto> findInvestmentRecordById(String investmentRecordId) {
        InvestmentRecordDto investmentRecord = investmentRecordFacade.findInvestmentRecordById(investmentRecordId);
        return investmentRecordModelAssembler.toModel(investmentRecord);
    }

    public InvestmentRecordDto createInvestmentRecord(CreateInvestmentRecordDto createInvestmentRecordDto, String investmentId) {
        return investmentRecordFacade.createInvestmentRecordForInvestment(createInvestmentRecordDto, investmentId);
    }

    public void deleteInvestmentRecordById(String investmentRecordId) {
        investmentRecordFacade.deleteInvestmentRecordById(investmentRecordId);
    }
}
