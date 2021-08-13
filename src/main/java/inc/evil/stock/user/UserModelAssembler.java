package inc.evil.stock.user;

import inc.evil.stock.config.security.SecurityUtil;
import inc.evil.stock.investment.InvestmentController;
import inc.evil.stock.profit.ProfitController;
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
public class UserModelAssembler implements RepresentationModelAssembler<UserDto, EntityModel<UserDto>> {
    private static final String INVESTMENTS_REL = "investments";
    private static final String PROFIT_REL = "profit";

    private final PagedResourcesAssembler<UserDto> pagedResourcesAssembler;
    private final SecurityUtil securityUtil;

    public UserModelAssembler(PagedResourcesAssembler<UserDto> pagedResourcesAssembler, SecurityUtil securityUtil) {
        this.pagedResourcesAssembler = pagedResourcesAssembler;
        this.securityUtil = securityUtil;
    }

    public PagedModel<EntityModel<UserDto>> toPagedModel(Page<UserDto> users) {
        return pagedResourcesAssembler.toModel(users, this);
    }

    @Override
    @NonNull
    public EntityModel<UserDto> toModel(@NonNull UserDto user) {
        UserDto authenticatedUser = securityUtil.getAuthenticatedUser();
        return EntityModel.of(
                user,
                linkTo(methodOn(UserController.class).findById(user.getId())).withSelfRel(),
                linkTo(methodOn(InvestmentController.class).findAll(authenticatedUser, Pageable.unpaged())).withRel(INVESTMENTS_REL),
                linkTo(methodOn(ProfitController.class).getTotalProfits(authenticatedUser)).withRel(PROFIT_REL)
        );
    }
}
