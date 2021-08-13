package inc.evil.stock.profit;

import inc.evil.stock.config.security.AuthenticatedUser;
import inc.evil.stock.user.UserDto;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/profits")
public class ProfitController {
    private final ProfitPresenter profitPresenter;

    public ProfitController(ProfitPresenter profitPresenter) {
        this.profitPresenter = profitPresenter;
    }

    @GetMapping
    public EntityModel<ProfitSummaryDto> getTotalProfits(@AuthenticatedUser UserDto user) {
        return profitPresenter.getTotalProfitsFor(user.getId());
    }
}
