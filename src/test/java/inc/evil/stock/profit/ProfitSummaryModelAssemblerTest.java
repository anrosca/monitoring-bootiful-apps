package inc.evil.stock.profit;

import inc.evil.stock.config.security.SecurityUtil;
import inc.evil.stock.investment.MonetaryAmount;
import inc.evil.stock.user.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class ProfitSummaryModelAssemblerTest {
    private final SecurityUtil securityUtil = Mockito.mock(SecurityUtil.class);
    private final ProfitSummaryModelAssembler assembler = new ProfitSummaryModelAssembler(securityUtil);

    @BeforeEach
    void setUp() {
        UserDto userDto = UserDto.builder()
                .id("1111-2222-3333-4444")
                .firstName("Mike")
                .lastName("Smith")
                .userName("msmith")
                .build();
        when(securityUtil.getAuthenticatedUser()).thenReturn(userDto);
    }

    @Test
    public void shouldBeAbleToAssembleProfitToHateoasEntityModel() {
        ProfitSummaryDto profit = ProfitSummaryDto.builder()
                .currentWorth(new MonetaryAmount(new BigDecimal("100.0"), "EUR"))
                .totalSpent(new MonetaryAmount(new BigDecimal("50.0"), "EUR"))
                .totalProfit(new MonetaryAmount(new BigDecimal("50.0"), "EUR"))
                .profitSummaryComponents(List.of(
                        new ProfitSummaryComponentDto("BTC", new MonetaryAmount(new BigDecimal("50.0"), "EUR"))
                ))
                .build();
        EntityModel<ProfitSummaryDto> actualModel = assembler.toModel(profit);

        EntityModel<ProfitSummaryDto> expectedModel = EntityModel.of(
                profit,
                Link.of("/api/v1/profits", "self"),
                Link.of("/api/v1/investments", "investments")
        );
        assertThat(actualModel.toString()).isEqualTo(expectedModel.toString());
    }
}
