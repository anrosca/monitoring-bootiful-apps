package inc.evil.stock.investment;

import inc.evil.stock.util.web.ResponseBodyMatchers;
import inc.evil.stock.util.web.security.TestSecurityConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

@WebMvcTest(controllers = InvestmentRecordController.class)
@ContextConfiguration(classes = TestSecurityConfiguration.class)
public class InvestmentRecordControllerTest {
    private static final String INVESTMENT_ID = "1da1f05d-344e-477c-b095-6529f0d756c1";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InvestmentRecordPresenter presenter;

    @Test
    @WithMockUser(TestSecurityConfiguration.USER_NAME)
    public void shouldReturnEmptyListWhenThereAreNoInvestmentRecords() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/investments/{investmentId}/investment-records", INVESTMENT_ID))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(ResponseBodyMatchers.responseBody().containsListAsJson(List.of()));
    }
}
