package inc.evil.stock.investment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;

@Component
public class InvestmentPresenter {
    private final InvestmentFacade investmentFacade;
    private final InvestmentModelAssembler investmentModelAssembler;
    private final InvestmentProfitModelAssembler investmentProfitModelAssembler;

    public InvestmentPresenter(InvestmentFacade investmentFacade,
                               InvestmentModelAssembler investmentModelAssembler,
                               InvestmentProfitModelAssembler investmentProfitModelAssembler) {
        this.investmentFacade = investmentFacade;
        this.investmentModelAssembler = investmentModelAssembler;
        this.investmentProfitModelAssembler = investmentProfitModelAssembler;
    }

    public CollectionModel<EntityModel<InvestmentDto>> findAllInvestmentsForUser(String userId, Pageable pageable) {
        Page<InvestmentDto> investments = investmentFacade.findAllInvestmentsForUser(userId, pageable);
        return investmentModelAssembler.toPagedModel(investments);
    }

    public EntityModel<InvestmentDto> findById(String investmentId) {
        InvestmentDto investment = investmentFacade.findById(investmentId);
        return investmentModelAssembler.toModel(investment);
    }

    public InvestmentDto create(String userId, CreateInvestmentDto createInvestmentDto) {
        return investmentFacade.create(userId, createInvestmentDto);
    }

    public EntityModel<InvestmentProfitDto> getProfitForInvestment(String investmentId) {
        InvestmentProfitDto investmentProfit = investmentFacade.getProfitForInvestment(investmentId);
        return investmentProfitModelAssembler.toModel(investmentProfit);
    }
}
