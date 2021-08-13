package inc.evil.stock.investment;

import inc.evil.stock.stock.StockMetaData;
import inc.evil.stock.stock.Price;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class InvestmentProfitTest {

    @Test
    public void shouldBeAbleToCalculateTheCurrentPrice() {
        InvestmentProfit investmentProfit = InvestmentProfit.builder()
                .stockSymbol("ETH")
                .totalAmount(new BigDecimal("0.01014"))
                .totalSpent(new BigDecimal("29.58"))
                .stockMetaData(StockMetaData.builder()
                        .companyName("Apple")
                        .stockSymbol("ETH")
                        .price(new Price(new BigDecimal("4309.98"), "USD"))
                        .build())
                .build();

        Price actualCurrentPrice = investmentProfit.getCurrentPrice();

        assertEquals(new Price(new BigDecimal("43.7031972"), "USD"), actualCurrentPrice);
    }

    @Test
    public void shouldBeAbleToCalculateThePureProfit() {
        InvestmentProfit investmentProfit = InvestmentProfit.builder()
                .stockSymbol("ETH")
                .totalAmount(new BigDecimal("0.01014"))
                .totalSpent(new BigDecimal("29.58"))
                .stockMetaData(StockMetaData.builder()
                        .companyName("Apple")
                        .stockSymbol("ETH")
                        .price(new Price(new BigDecimal("4309.98"), "USD"))
                        .build())
                .build();

        MonetaryAmount actualPureProfit = investmentProfit.getPureProfit();

        assertEquals(new MonetaryAmount(new BigDecimal("14.1231972"), "USD"), actualPureProfit);
    }
}
