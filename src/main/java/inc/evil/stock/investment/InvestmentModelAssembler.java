package inc.evil.stock.investment;

import inc.evil.stock.config.security.SecurityUtil;
import inc.evil.stock.user.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class InvestmentModelAssembler implements RepresentationModelAssembler<InvestmentDto, EntityModel<InvestmentDto>> {
    private static final String PROFIT_REL = "profit";
    private static final String INVESTMENT_RECORDS_REL = "investment-records";
    private static final String INVESTMENTS_REL = "investments";

    private final PagedResourcesAssembler<InvestmentDto> pagedResourcesAssembler;
    private final SecurityUtil securityUtil;

    public InvestmentModelAssembler(PagedResourcesAssembler<InvestmentDto> pagedResourcesAssembler, SecurityUtil securityUtil) {
        this.pagedResourcesAssembler = pagedResourcesAssembler;
        this.securityUtil = securityUtil;
    }

    public PagedModel<EntityModel<InvestmentDto>> toPagedModel(Page<InvestmentDto> investments) {
        return pagedResourcesAssembler.toModel(investments, this);
    }

    @Override
    @NonNull
    public EntityModel<InvestmentDto> toModel(@NonNull InvestmentDto investment) {
        UserDto user = securityUtil.getAuthenticatedUser();
        return EntityModel.of(
                investment,
                linkTo(methodOn(InvestmentController.class).findById(investment.getId())).withSelfRel(),
                linkTo(methodOn(InvestmentController.class).getProfitForInvestment(investment.getId())).withRel(PROFIT_REL),
                linkTo(methodOn(InvestmentRecordController.class).findAllInvestmentRecordsForInvestment(investment.getId(), Pageable.unpaged())).withRel(INVESTMENT_RECORDS_REL),
                linkTo(methodOn(InvestmentController.class).findAll(user, Pageable.unpaged())).withRel(INVESTMENTS_REL)
        );
    }
}
