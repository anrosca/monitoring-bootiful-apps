package inc.evil.stock.profit;

import inc.evil.stock.stock.StockPriceFetcher;
import inc.evil.stock.currency.CurrencyService;
import inc.evil.stock.investment.*;
import io.micrometer.core.annotation.Counted;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Component
public class ProfitCalculator {
    private final InvestmentRecordService investmentRecordService;
    private final InvestmentService investmentService;
    private final StockPriceFetcher stockPriceFetcher;
    private final CurrencyService currencyService;

    public ProfitCalculator(InvestmentRecordService investmentRecordRepository, InvestmentService investmentService,
                            StockPriceFetcher stockPriceFetcher, CurrencyService currencyService) {
        this.investmentRecordService = investmentRecordRepository;
        this.investmentService = investmentService;
        this.stockPriceFetcher = stockPriceFetcher;
        this.currencyService = currencyService;
    }

    @Transactional(readOnly = true)
    public ProfitSummary calculateForUser(String userId) {
        Map<Investment, List<InvestmentRecord>> investments = investmentRecordService.findInvestmentRecordsByUserId(userId)
                .stream()
                .collect(Collectors.groupingBy(InvestmentRecord::getInvestment, TreeMap::new, Collectors.toList()));
        return new ProfitSummary(calculateProfits(investments));
    }

    @Transactional(readOnly = true)
    @Counted("stock.investment.price")
    public InvestmentProfit calculateForInvestment(String investmentId) {
        List<InvestmentRecord> investmentRecords = investmentRecordService.findInvestmentRecordsForInvestment(investmentId);
        if (investmentRecords.isEmpty()) {
            return makeNullInvestmentProfit(investmentId);
        }
        String stockSymbol = getStockSymbol(investmentRecords);
        return calculateInvestmentProfitFor(investmentRecords, stockSymbol);
    }

    private InvestmentProfit makeNullInvestmentProfit(String investmentId) {
        Investment investment = investmentService.findById(investmentId);
        return InvestmentProfit.builder()
                .investmentId(investmentId)
                .stockSymbol(investment.getSymbol())
                .currency(currencyService.getConfiguredCurrency())
                .build();
    }

    private Map<Investment, InvestmentProfit> calculateProfits(Map<Investment, List<InvestmentRecord>> investments) {
        Map<Investment, InvestmentProfit> profits = new HashMap<>();
        for (Map.Entry<Investment, List<InvestmentRecord>> investmentEntry : investments.entrySet()) {
            Investment investment = investmentEntry.getKey();
            String stockSymbol = getStockSymbol(investments.get(investment));
            InvestmentProfit investmentProfit = calculateInvestmentProfitFor(investmentEntry.getValue(), stockSymbol);
            profits.put(investment, investmentProfit);
        }
        return profits;
    }

    private InvestmentProfit calculateInvestmentProfitFor(List<InvestmentRecord> investmentRecords, String stockSymbol) {
        BigDecimal totalAmount = BigDecimal.ZERO;
        BigDecimal totalSpent = BigDecimal.ZERO;
        for (InvestmentRecord record : investmentRecords) {
            totalAmount = totalAmount.add(record.getAmountBought());
            totalSpent = totalSpent.add(record.getSpent());
        }
        return InvestmentProfit.builder()
                .investmentId(getInvestmentIdFrom(investmentRecords))
                .stockSymbol(stockSymbol)
                .totalAmount(totalAmount)
                .totalSpent(totalSpent)
                .stockMetaData(stockPriceFetcher.fetchStock(stockSymbol))
                .build();
    }

    private String getInvestmentIdFrom(List<InvestmentRecord> investmentRecords) {
        return investmentRecords.stream()
                .findAny()
                .map(InvestmentRecord::getInvestment)
                .map(Investment::getId)
                .orElseThrow(() -> new InvestmentNotFoundException("No investments were found"));
    }

    private String getStockSymbol(List<InvestmentRecord> investmentRecords) {
        return investmentRecords.stream()
                .findAny()
                .map(InvestmentRecord::getSymbol)
                .orElse("");
    }
}
