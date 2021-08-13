package inc.evil.stock.stock;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class StockMetaDataMapper {
    public StockMetaData map(ObjectNode node) {
        return StockMetaData.builder()
                .stockSymbol(node.get("symbol").asText())
                .companyName(node.get("companyName").asText())
                .price(new Price(new BigDecimal(node.get("latestPrice").asText()), "USD"))
                .build();
    }
}
