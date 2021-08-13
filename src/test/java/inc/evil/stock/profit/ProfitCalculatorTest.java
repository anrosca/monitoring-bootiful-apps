package inc.evil.stock.profit;

import inc.evil.stock.stock.StockMetaData;
import inc.evil.stock.stock.StockPriceFetcher;
import inc.evil.stock.stock.Price;
import inc.evil.stock.currency.CurrencyService;
import inc.evil.stock.investment.*;
import inc.evil.stock.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProfitCalculatorTest {
    private static final String USER_ID = "123e4567-e89b-12d3-a456-426614174000";

    @Mock
    private InvestmentRecordService investmentRecordService;

    @Mock
    private InvestmentService investmentService;

    @Mock
    private StockPriceFetcher stockPriceFetcher;

    @Mock
    private CurrencyService currencyService;

    @InjectMocks
    private ProfitCalculator profitCalculator;

    @BeforeEach
    public void setUp() {
//        when(currencyService.getConfiguredCurrency()).thenReturn(Currency.getInstance("EUR"));
    }

    @Test
    public void shouldBeAbleToCalculateProfitForHomogeneousInvestment() {
        Investment investment = Investment.builder()
                .id("1111-2222-3333-4444")
                .name("Apple")
                .user(User.builder()
                        .id(USER_ID)
                        .firstName("Mike")
                        .lastName("Smith")
                        .build())
                .build();
        when(investmentRecordService.findInvestmentRecordsByUserId(USER_ID)).thenReturn(List.of(
                InvestmentRecord.builder()
                        .symbol("ETH")
                        .investmentDate(LocalDateTime.of(2021, 4, 29, 11, 12))
                        .amountBought(new BigDecimal("0.01014"))
                        .unitPrice(new BigDecimal("2802.761341"))
                        .spent(new BigDecimal("29.58"))
                        .investment(investment)
                        .build(),
                InvestmentRecord.builder()
                        .symbol("ETH")
                        .investmentDate(LocalDateTime.of(2021, 5, 4, 11, 12))
                        .amountBought(new BigDecimal("0.009226"))
                        .unitPrice(new BigDecimal("2804.5356"))
                        .spent(new BigDecimal("27.3"))
                        .investment(investment)
                        .build()
        ));

        ProfitSummary actualProfit = profitCalculator.calculateForUser(USER_ID);

        var expectedProfit = new ProfitSummary(
                Map.of(
                        investment, InvestmentProfit.builder()
                                .stockSymbol("ETH")
                                .totalAmount(new BigDecimal("0.019366"))
                                .totalSpent(new BigDecimal("56.88"))
                                .build()
                )
        );
        assertEquals(expectedProfit, actualProfit);
    }

    @Test
    public void shouldBeAbleToCalculateProfitForSingleInvestment() {
        Investment investment = Investment.builder()
                .id("1111-2222-3333-4444")
                .name("Apple")
                .user(User.builder()
                        .id(USER_ID)
                        .firstName("Mike")
                        .lastName("Smith")
                        .build())
                .build();
        when(investmentRecordService.findInvestmentRecordsByUserId(USER_ID)).thenReturn(List.of(
                InvestmentRecord.builder()
                        .symbol("ETH")
                        .investmentDate(LocalDateTime.of(2021, 4, 29, 11, 12))
                        .amountBought(new BigDecimal("0.01014"))
                        .unitPrice(new BigDecimal("2802.761341"))
                        .spent(new BigDecimal("29.58"))
                        .investment(investment)
                        .build()
        ));

        ProfitSummary actualProfit = profitCalculator.calculateForUser(USER_ID);

        var expectedProfit = new ProfitSummary(
                Map.of(
                        investment, InvestmentProfit.builder()
                                .stockSymbol("ETH")
                                .totalAmount(new BigDecimal("0.01014"))
                                .totalSpent(new BigDecimal("29.58"))
                                .build()
                )
        );
        assertEquals(expectedProfit, actualProfit);
    }

    @Test
    public void whenThereAreNoInvestments_thereAreNoProfits() {
        when(investmentRecordService.findInvestmentRecordsByUserId(USER_ID)).thenReturn(List.of());

        ProfitSummary actualProfit = profitCalculator.calculateForUser(USER_ID);

        assertEquals(new ProfitSummary(Map.of()), actualProfit);
    }

    @Test
    public void shouldBeAbleToCalculateProfitForHeterogeneousInvestment() {
        User user = User.builder().id(USER_ID).firstName("Mike").lastName("Smith").build();
        Investment ethInvestment = Investment.builder().id("1111").user(user).name("Apple").build();
        Investment btcInvestment = Investment.builder().id("2222").user(user).name("Tesla").build();
        when(investmentRecordService.findInvestmentRecordsByUserId(USER_ID)).thenReturn(List.of(
                InvestmentRecord.builder()
                        .symbol("ETH")
                        .investmentDate(LocalDateTime.of(2021, 4, 29, 11, 12))
                        .amountBought(new BigDecimal("0.01014"))
                        .unitPrice(new BigDecimal("2802.761341"))
                        .spent(new BigDecimal("29.58"))
                        .investment(ethInvestment)
                        .build(),
                InvestmentRecord.builder()
                        .symbol("BTC")
                        .investmentDate(LocalDateTime.of(2021, 5, 4, 11, 12))
                        .amountBought(new BigDecimal("0.000512"))
                        .unitPrice(new BigDecimal("55507.8125"))
                        .spent(new BigDecimal("29.64"))
                        .investment(btcInvestment)
                        .build()
        ));

        ProfitSummary actualProfit = profitCalculator.calculateForUser(USER_ID);

        var expectedProfit = new ProfitSummary(
                Map.of(
                        ethInvestment, InvestmentProfit.builder()
                                .stockSymbol("ETH")
                                .totalAmount(new BigDecimal("0.01014"))
                                .totalSpent(new BigDecimal("29.58"))
                                .build(),
                        btcInvestment, InvestmentProfit.builder()
                                .stockSymbol("BTC")
                                .totalAmount(new BigDecimal("0.000512"))
                                .totalSpent(new BigDecimal("29.64"))
                                .build()
                )
        );
        assertEquals(expectedProfit, actualProfit);
    }

    @Test
    public void shouldBeAbleToCalculateProfitForAllInvestments() {
        User user = User.builder()
                .id(USER_ID)
                .firstName("Mike")
                .lastName("Smith")
                .build();
        Investment ethInvestment = Investment.builder()
                .id("1111")
                .symbol("ETH")
                .user(user)
                .name("Apple")
                .build();
        Investment btcInvestment = Investment.builder()
                .id("2222")
                .symbol("BTC")
                .user(user)
                .name("Tesla")
                .build();
        when(stockPriceFetcher.fetchStock("ETH"))
                .thenReturn(StockMetaData.builder()
                        .stockSymbol("ETH")
                        .companyName("Apple")
                        .price(new Price(new BigDecimal("3833.86"), "USD"))
                        .build());
        when(stockPriceFetcher.fetchStock("BTC"))
                .thenReturn(StockMetaData.builder()
                        .stockSymbol("BTC")
                        .companyName("Tesla")
                        .price(new Price(new BigDecimal("50304.5"), "USD"))
                        .build());
        when(investmentRecordService.findInvestmentRecordsByUserId(USER_ID)).thenReturn(List.of(
                InvestmentRecord.builder()
                        .symbol("ETH")
                        .investmentDate(LocalDateTime.of(2021, 4, 29, 11, 12))
                        .amountBought(new BigDecimal("0.01014"))
                        .unitPrice(new BigDecimal("2802.761341"))
                        .spent(new BigDecimal("29.58"))
                        .investment(ethInvestment)
                        .build(),
                InvestmentRecord.builder()
                        .symbol("BTC")
                        .investmentDate(LocalDateTime.of(2021, 5, 4, 11, 12))
                        .amountBought(new BigDecimal("0.000512"))
                        .unitPrice(new BigDecimal("55507.8125"))
                        .spent(new BigDecimal("29.64"))
                        .investment(btcInvestment)
                        .build()
        ));

        ProfitSummary actualProfit = profitCalculator.calculateForUser(USER_ID);

        var expectedProfit = new MonetaryAmount(new BigDecimal("5.4112444"), "USD");
        assertEquals(expectedProfit, actualProfit.getTotalProfit());
    }
}
