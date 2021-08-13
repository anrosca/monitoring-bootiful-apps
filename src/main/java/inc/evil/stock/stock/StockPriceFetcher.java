package inc.evil.stock.stock;

import com.fasterxml.jackson.databind.node.ObjectNode;
import inc.evil.stock.config.iex.IexRestTemplate;
import inc.evil.stock.config.metrics.TimeMetrics;
import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
public class StockPriceFetcher {
    private final RestTemplate restTemplate;
    private final StockMetaDataMapper stockMetaDataMapper;

    public StockPriceFetcher(@IexRestTemplate RestTemplate restTemplate, StockMetaDataMapper stockMetaDataMapper) {
        this.restTemplate = restTemplate;
        this.stockMetaDataMapper = stockMetaDataMapper;
    }

    @TimeMetrics(value = "stock.price.fetch", extraTags = {"stockSymbol", "#args[0]"})
    public StockMetaData fetchStock(String stockSymbol) {
        ObjectNode jsonResponse = restTemplate.getForObject("stock/{stockSymbol}/quote", ObjectNode.class, stockSymbol);
        if (jsonResponse == null) {
            throw new StockMetaDataNotAvailableException("Cannot get metadata for stock '" + stockSymbol + "'.");
        }
        log.debug("{} stock json metadata: {}", stockSymbol, jsonResponse);
        return stockMetaDataMapper.map(jsonResponse);
    }
}
