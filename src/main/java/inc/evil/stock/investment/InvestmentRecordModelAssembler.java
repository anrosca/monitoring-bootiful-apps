package inc.evil.stock.investment;

import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class InvestmentRecordModelAssembler implements RepresentationModelAssembler<InvestmentRecordDto, EntityModel<InvestmentRecordDto>> {
    private static final String INVESTMENT_REL = "investment";

    private final PagedResourcesAssembler<InvestmentRecordDto> pagedResourcesAssembler;

    public InvestmentRecordModelAssembler(PagedResourcesAssembler<InvestmentRecordDto> pagedResourcesAssembler) {
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }

    public PagedModel<EntityModel<InvestmentRecordDto>> toPagedModel(Page<InvestmentRecordDto> investmentRecords) {
        return pagedResourcesAssembler.toModel(investmentRecords, this);
    }

    @Override
    @NonNull
    public EntityModel<InvestmentRecordDto> toModel(@NonNull InvestmentRecordDto investmentRecord) {
        return EntityModel.of(
                investmentRecord,
                linkTo(methodOn(InvestmentRecordController.class, investmentRecord.getInvestmentId()).findInvestmentRecordById(investmentRecord.getId())).withSelfRel(),
                linkTo(methodOn(InvestmentController.class).findById(investmentRecord.getInvestmentId())).withRel(INVESTMENT_REL)
        );
    }
}
