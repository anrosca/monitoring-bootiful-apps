package inc.evil.stock.investment;

import inc.evil.stock.stock.StockMetaData;
import inc.evil.stock.stock.Price;
import inc.evil.stock.util.json.JsonSerde;
import inc.evil.stock.util.web.ResponseBodyMatchers;
import inc.evil.stock.util.web.security.TestSecurityConfiguration;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@WebMvcTest(controllers = InvestmentController.class)
@ContextConfiguration(classes = TestSecurityConfiguration.class)
public class InvestmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InvestmentPresenter investmentPresenter;

    @Test
    @WithMockUser(TestSecurityConfiguration.USER_NAME)
    public void shouldReturnEmptyListWhenThereAreNoInvestments() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/investments"))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(ResponseBodyMatchers.responseBody().containsListAsJson(List.of()));
    }

    @Test
    @WithMockUser(TestSecurityConfiguration.USER_NAME)
    public void shouldBeAbleToGetAllInvestments() throws Exception {
        InvestmentDto investment = InvestmentDto.builder()
                .id("5284f3d2-92d6-438a-8e24-949bd074662a")
                .name("Apple investment")
                .build();
        List<InvestmentDto> expectedInvestments = List.of(investment);
        when(investmentPresenter.findAllInvestmentsForUser(eq(TestSecurityConfiguration.USER_ID), any(Pageable.class)))
                .thenReturn(CollectionModel.wrap(expectedInvestments));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/investments"))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(ResponseBodyMatchers.responseBody().containsListAsJson(expectedInvestments, "investments"));
    }

    @Test
    @WithMockUser(TestSecurityConfiguration.USER_NAME)
    public void shouldBeAbleToFindInvestmentById() throws Exception {
        InvestmentDto expectedInvestment = InvestmentDto.builder()
                .id("5284f3d2-92d6-438a-8e24-949bd074662a")
                .name("Apple investment")
                .symbol("ETH")
                .build();
        when(investmentPresenter.findById(expectedInvestment.getId())).thenReturn(EntityModel.of(expectedInvestment));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/investments/{investmentId}", expectedInvestment.getId()))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(ResponseBodyMatchers.responseBody().containsObjectAsJson(expectedInvestment, InvestmentDto.class));
    }

    @Test
    @WithMockUser(TestSecurityConfiguration.USER_NAME)
    public void shouldBeAbleToCreateInvestments() throws Exception {
        InvestmentDto expectedInvestment = InvestmentDto.builder()
                .id("a284f3d2-b2d6-b38a-be24-b49bd074662b")
                .name("Apple investment")
                .symbol("ETH")
                .build();
        when(investmentPresenter.create(eq(TestSecurityConfiguration.USER_ID), any()))
                .thenReturn(expectedInvestment);

        CreateInvestmentDto requestPayload = new CreateInvestmentDto("Apple investment", "ETH");
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/investments").contentType(MediaType.APPLICATION_JSON).content(JsonSerde.toJson(requestPayload)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.header().string(HttpHeaders.LOCATION, Matchers.endsWith("/api/v1/investments/a284f3d2-b2d6-b38a-be24-b49bd074662b")));
    }

    @Test
    @WithMockUser(TestSecurityConfiguration.USER_NAME)
    public void shouldBeAbleToCalculateProfitForInvestment() throws Exception {
        String investmentId = "5284f3d2-92d6-438a-8e24-949bd074662a";
        InvestmentProfitDto expectedProfit = InvestmentProfitDto.from(InvestmentProfit.builder()
                .stockSymbol("ETH")
                .totalSpent(new BigDecimal("100"))
                .totalAmount(new BigDecimal("0.01"))
                .stockMetaData(StockMetaData.builder()
                        .stockSymbol("ETH")
                        .companyName("Apple")
                        .price(new Price(new BigDecimal("1250.55"), "USD"))
                        .build())
                .build());
        when(investmentPresenter.getProfitForInvestment(investmentId)).thenReturn(EntityModel.of(expectedProfit));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/investments/{investmentId}/profits", investmentId))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(ResponseBodyMatchers.responseBody().containsObjectAsJson(expectedProfit, InvestmentProfitDto.class));
    }
}
