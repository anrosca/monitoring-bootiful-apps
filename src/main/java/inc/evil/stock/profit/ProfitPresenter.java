package inc.evil.stock.profit;

import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;

@Component
public class ProfitPresenter {
    private final ProfitFacade profitFacade;
    private final ProfitSummaryModelAssembler profitSummaryModelAssembler;

    public ProfitPresenter(ProfitFacade profitFacade, ProfitSummaryModelAssembler profitSummaryModelAssembler) {
        this.profitFacade = profitFacade;
        this.profitSummaryModelAssembler = profitSummaryModelAssembler;
    }

    public EntityModel<ProfitSummaryDto> getTotalProfitsFor(String userId) {
        ProfitSummaryDto profitSummary = profitFacade.getTotalProfitsFor(userId);
        return profitSummaryModelAssembler.toModel(profitSummary);
    }
}
