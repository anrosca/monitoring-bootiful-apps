package inc.evil.stock.investment;

import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;

@Relation(itemRelation = "investmentRecord", collectionRelation = "investmentRecords")
public class InvestmentRecordDto {
    private String id;
    private String investmentDate;
    private BigDecimal unitPrice;
    private BigDecimal amountBought;
    private BigDecimal spent;
    private String symbol;
    private String investmentId;

    public InvestmentRecordDto() {
    }

    private InvestmentRecordDto(InvestmentRecordDtoBuilder builder) {
        this.id = builder.id;
        this.investmentDate = builder.investmentDate;
        this.unitPrice = builder.unitPrice;
        this.amountBought = builder.amountBought;
        this.spent = builder.spent;
        this.symbol = builder.symbol;
        this.investmentId = builder.investmentId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInvestmentDate() {
        return investmentDate;
    }

    public void setInvestmentDate(String investmentDate) {
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

    public String getInvestmentId() {
        return investmentId;
    }

    public void setInvestmentId(String investmentId) {
        this.investmentId = investmentId;
    }

    public static InvestmentRecordDto from(InvestmentRecord investmentRecord) {
        return InvestmentRecordDto.builder()
                .id(investmentRecord.getId())
                .investmentDate(investmentRecord.getInvestmentDate().toString())
                .symbol(investmentRecord.getSymbol())
                .amountBought(investmentRecord.getAmountBought())
                .unitPrice(investmentRecord.getUnitPrice())
                .spent(investmentRecord.getSpent())
                .investmentId(investmentRecord.getInvestment().getId())
                .build();
    }

    public static InvestmentRecordDtoBuilder builder() {
        return new InvestmentRecordDtoBuilder();
    }

    public static class InvestmentRecordDtoBuilder {
        private String id;
        private String investmentDate;
        private BigDecimal unitPrice;
        private BigDecimal amountBought;
        private BigDecimal spent;
        private String symbol;
        private String investmentId;

        public InvestmentRecordDtoBuilder id(String id) {
            this.id = id;
            return this;
        }

        public InvestmentRecordDtoBuilder investmentDate(String investmentDate) {
            this.investmentDate = investmentDate;
            return this;
        }

        public InvestmentRecordDtoBuilder unitPrice(BigDecimal unitPrice) {
            this.unitPrice = unitPrice;
            return this;
        }

        public InvestmentRecordDtoBuilder amountBought(BigDecimal amountBought) {
            this.amountBought = amountBought;
            return this;
        }

        public InvestmentRecordDtoBuilder spent(BigDecimal spent) {
            this.spent = spent;
            return this;
        }

        public InvestmentRecordDtoBuilder symbol(String symbol) {
            this.symbol = symbol;
            return this;
        }

        public InvestmentRecordDtoBuilder investmentId(String investmentId) {
            this.investmentId = investmentId;
            return this;
        }

        public InvestmentRecordDto build() {
            return new InvestmentRecordDto(this);
        }
    }
}
