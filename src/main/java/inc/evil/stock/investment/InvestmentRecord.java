package inc.evil.stock.investment;

import inc.evil.stock.entity.AbstractEntity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "investment_records")
public class InvestmentRecord extends AbstractEntity {
    @Column(name = "investment_date", nullable = false)
    private LocalDateTime investmentDate;

    @Column(name = "unit_price", nullable = false, columnDefinition = "numeric(19, 8)")
    private BigDecimal unitPrice;

    @Column(name = "amount_bought", nullable = false, columnDefinition = "numeric(19, 8)")
    private BigDecimal amountBought;

    @Column(name = "spent", nullable = false, columnDefinition = "numeric(19, 2)")
    private BigDecimal spent;

    @Column(name = "symbol", nullable = false)
    private String symbol;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Investment investment;

    protected InvestmentRecord() {
    }

    private InvestmentRecord(InvestmentRecordBuilder builder) {
        this.investmentDate = builder.investmentDate;
        this.unitPrice = builder.unitPrice;
        this.amountBought = builder.amountBought;
        this.spent = builder.spent;
        this.symbol = builder.symbol;
        this.investment = builder.investment;
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

    public Investment getInvestment() {
        return investment;
    }

    public void setInvestment(Investment investment) {
        this.investment = investment;
    }

    public static InvestmentRecordBuilder builder() {
        return new InvestmentRecordBuilder();
    }

    public static class InvestmentRecordBuilder {
        private LocalDateTime investmentDate;
        private BigDecimal unitPrice;
        private BigDecimal amountBought;
        private BigDecimal spent;
        private String symbol;
        private Investment investment;

        public InvestmentRecordBuilder investmentDate(LocalDateTime investmentDate) {
            this.investmentDate = investmentDate;
            return this;
        }

        public InvestmentRecordBuilder unitPrice(BigDecimal unitPrice) {
            this.unitPrice = unitPrice;
            return this;
        }

        public InvestmentRecordBuilder amountBought(BigDecimal amountBought) {
            this.amountBought = amountBought;
            return this;
        }

        public InvestmentRecordBuilder spent(BigDecimal spent) {
            this.spent = spent;
            return this;
        }

        public InvestmentRecordBuilder symbol(String symbol) {
            this.symbol = symbol;
            return this;
        }

        public InvestmentRecordBuilder investment(Investment investment) {
            this.investment = investment;
            return this;
        }

        public InvestmentRecord build() {
            return new InvestmentRecord(this);
        }
    }

    @Override
    public String toString() {
        return "InvestmentRecord{" +
                "investmentDate=" + investmentDate +
                ", unitPrice=" + unitPrice +
                ", amountBought=" + amountBought +
                ", spent=" + spent +
                ", symbol='" + symbol + '\'' +
                '}';
    }
}
