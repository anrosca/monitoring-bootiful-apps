package inc.evil.stock.investment;

import inc.evil.stock.profit.ProfitCalculator;
import inc.evil.stock.user.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class InvestmentFacadeTest {
    @Mock
    private InvestmentService investmentService;

    @Mock
    private ProfitCalculator profitCalculator;

    @Spy
    private InvestmentMapper investmentMapper;

    @InjectMocks
    private InvestmentFacade investmentFacade;

    @Test
    public void shouldBeAbleToFindInvestmentById() {
        String investmentId = "5284f3d2-92d6-438a-8e24-949bd074662a";
        when(investmentService.findById(investmentId)).thenReturn(Investment.builder()
                .id(investmentId)
                .name("Apple investment")
                .user(User.builder()
                        .firstName("Mike")
                        .lastName("Smith")
                        .build())
                .build());

        InvestmentDto actualInvestment = investmentFacade.findById(investmentId);

        InvestmentDto expectedInvestment = InvestmentDto.builder()
                .id(investmentId)
                .name("Apple investment")
                .symbol("ETH")
                .build();
        assertEquals(expectedInvestment, actualInvestment);
    }
}
