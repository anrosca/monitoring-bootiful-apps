package inc.evil.stock.profit;

import inc.evil.stock.investment.MonetaryAmount;
import inc.evil.stock.util.web.ResponseBodyMatchers;
import inc.evil.stock.util.web.security.TestSecurityConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.EntityModel;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.when;

@WebMvcTest(controllers = ProfitController.class)
@ContextConfiguration(classes = TestSecurityConfiguration.class)
public class ProfitControllerTest {

    @MockBean
    private ProfitPresenter profitPresenter;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(TestSecurityConfiguration.USER_NAME)
    public void shouldBeAbleToGetGlobalProfit() throws Exception {
        ProfitSummaryDto profitSummary = ProfitSummaryDto.builder()
                .totalProfit(new MonetaryAmount(new BigDecimal("20.5"), "USD"))
                .currentWorth(new MonetaryAmount(new BigDecimal("21.5"), "USD"))
                .profitSummaryComponents(
                        List.of(
                                new ProfitSummaryComponentDto("ETH", new MonetaryAmount(new BigDecimal("10.5"), "USD")),
                                new ProfitSummaryComponentDto("ADA", new MonetaryAmount(new BigDecimal("10"), "USD"))
                        )
                ).totalSpent(new MonetaryAmount(new BigDecimal("1.5"), "USD"))
                .build();
        when(profitPresenter.getTotalProfitsFor(TestSecurityConfiguration.USER_ID)).thenReturn(EntityModel.of(profitSummary));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/profits"))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(ResponseBodyMatchers.responseBody().containsObjectAsJson(profitSummary, ProfitSummaryDto.class));
    }
}
