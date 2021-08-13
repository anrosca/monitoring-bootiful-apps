package inc.evil.stock.investment;

import inc.evil.stock.config.security.AuthenticatedUser;
import inc.evil.stock.user.UserDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/v1/investments")
public class InvestmentController {
    private final InvestmentPresenter investmentPresenter;

    public InvestmentController(InvestmentPresenter investmentPresenter) {
        this.investmentPresenter = investmentPresenter;
    }

    @GetMapping
    public CollectionModel<EntityModel<InvestmentDto>> findAll(@AuthenticatedUser UserDto user, @PageableDefault Pageable pageable) {
        return investmentPresenter.findAllInvestmentsForUser(user.getId(), pageable);
    }

    @GetMapping("{investmentId}")
    public EntityModel<InvestmentDto> findById(@PathVariable("investmentId") String investmentId) {
        return investmentPresenter.findById(investmentId);
    }

    @GetMapping("{investmentId}/profits")
    public EntityModel<InvestmentProfitDto> getProfitForInvestment(@PathVariable("investmentId") String investmentId) {
        return investmentPresenter.getProfitForInvestment(investmentId);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid CreateInvestmentDto createInvestmentDto, @AuthenticatedUser UserDto user) {
        InvestmentDto createdInvestment = investmentPresenter.create(user.getId(), createInvestmentDto);
        URI location = MvcUriComponentsBuilder.fromMethodCall(MvcUriComponentsBuilder.on(getClass())
                        .findById(createdInvestment.getId()))
                .build()
                .toUri();
        return ResponseEntity.created(location).build();
    }
}
