package inc.evil.stock.investment;

import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class InvestmentProfitModelAssembler implements RepresentationModelAssembler<InvestmentProfitDto, EntityModel<InvestmentProfitDto>> {
    private static final String INVESTMENT_RECORDS_REL = "investment-records";
    private static final String INVESTMENT_REL = "investment";

    @Override
    @NonNull
    public EntityModel<InvestmentProfitDto> toModel(@NonNull InvestmentProfitDto profit) {
        return EntityModel.of(
                profit,
                linkTo(methodOn(InvestmentController.class).getProfitForInvestment(profit.getInvestmentId())).withSelfRel(),
                linkTo(methodOn(InvestmentRecordController.class).findAllInvestmentRecordsForInvestment(profit.getInvestmentId(), Pageable.unpaged())).withRel(INVESTMENT_RECORDS_REL),
                linkTo(methodOn(InvestmentController.class).findById(profit.getInvestmentId())).withRel(INVESTMENT_REL)
        );
    }
}
