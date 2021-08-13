package inc.evil.stock.investment;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/v1/investments/{investmentId}/investment-records")
public class InvestmentRecordController {
    private final InvestmentRecordPresenter investmentRecordPresenter;

    public InvestmentRecordController(InvestmentRecordPresenter investmentRecordPresenter) {
        this.investmentRecordPresenter = investmentRecordPresenter;
    }

    @GetMapping
    public PagedModel<EntityModel<InvestmentRecordDto>> findAllInvestmentRecordsForInvestment(@PathVariable("investmentId") String investmentId, @PageableDefault Pageable pageable) {
        return investmentRecordPresenter.findAllInvestmentRecordsForInvestment(investmentId, pageable);
    }

    @GetMapping("{investmentRecordId}")
    public EntityModel<InvestmentRecordDto> findInvestmentRecordById(@PathVariable("investmentRecordId") String investmentRecordId) {
        return investmentRecordPresenter.findInvestmentRecordById(investmentRecordId);
    }

    @PostMapping
    public ResponseEntity<?> createInvestmentRecord(@RequestBody @Valid CreateInvestmentRecordDto createInvestmentRecordDto, @PathVariable("investmentId") String investmentId) {
        InvestmentRecordDto createdRecord = investmentRecordPresenter.createInvestmentRecord(createInvestmentRecordDto, investmentId);
        URI location = MvcUriComponentsBuilder.fromMethodCall(MvcUriComponentsBuilder.on(getClass())
                        .findInvestmentRecordById(createdRecord.getId()))
                .buildAndExpand("investmentId", createdRecord.getInvestmentId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("{investmentRecordId}")
    public ResponseEntity<?> deleteInvestmentRecord(@PathVariable("investmentRecordId") String investmentRecordId) {
        investmentRecordPresenter.deleteInvestmentRecordById(investmentRecordId);
        return ResponseEntity.noContent()
                .build();
    }
}
