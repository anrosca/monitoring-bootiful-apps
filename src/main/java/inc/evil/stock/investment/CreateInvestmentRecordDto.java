package inc.evil.stock.investment;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CreateInvestmentRecordDto {
    private LocalDateTime investmentDate;

    @NotNull(message = "{create.investment-record.unitPrice.null}")
    @Positive(message = "{create.investment-record.unitPrice.negative}")
    private BigDecimal unitPrice;

    @NotNull(message = "{create.investment-record.amountBought.null}")
    @Positive(message = "{create.investment-record.amountBought.negative}")
    private BigDecimal amountBought;

    @NotNull(message = "{create.investment-record.spent.null}")
    @Positive(message = "{create.investment-record.spent.negative}")
    private BigDecimal spent;

    @NotNull(message = "{create.investment-record.symbol.null}")
    @Size(min = 1, max = 200, message = "{create.investment-record.symbol.size}")
    private String symbol;

    public CreateInvestmentRecordDto() {
    }

    public LocalDateTime getInvestmentDate() {
        return investmentDate;
    }

    public void setInvestmentDate(LocalDateTime investmentDate) {
        this.investmentDate = investmentDate;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getAmountBought() {
        return amountBought;
    }

    public void setAmountBought(BigDecimal amountBought) {
        this.amountBought = amountBought;
    }

    public BigDecimal getSpent() {
        return spent;
    }

    public void setSpent(BigDecimal spent) {
        this.spent = spent;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public InvestmentRecord toInvestmentRecord() {
        return InvestmentRecord.builder()
                .investmentDate(investmentDate)
                .spent(spent)
                .symbol(symbol)
                .unitPrice(unitPrice)
                .amountBought(amountBought)
                .build();
    }
}
