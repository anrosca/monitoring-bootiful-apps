package inc.evil.stock.profit;

import inc.evil.stock.config.security.SecurityUtil;
import inc.evil.stock.investment.InvestmentController;
import inc.evil.stock.user.UserDto;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ProfitSummaryModelAssembler implements RepresentationModelAssembler<ProfitSummaryDto, EntityModel<ProfitSummaryDto>> {
    private static final String INVESTMENTS_REL = "investments";

    private final SecurityUtil securityUtil;

    public ProfitSummaryModelAssembler(SecurityUtil securityUtil) {
        this.securityUtil = securityUtil;
    }

    @Override
    @NonNull
    public EntityModel<ProfitSummaryDto> toModel(@NonNull ProfitSummaryDto profit) {
        UserDto authenticatedUser = securityUtil.getAuthenticatedUser();
        return EntityModel.of(
                profit,
                linkTo(methodOn(ProfitController.class).getTotalProfits(authenticatedUser)).withSelfRel(),
                linkTo(methodOn(InvestmentController.class).findAll(authenticatedUser, Pageable.unpaged())).withRel(INVESTMENTS_REL)
        );
    }
}
