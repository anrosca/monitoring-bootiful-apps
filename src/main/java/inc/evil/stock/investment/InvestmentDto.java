package inc.evil.stock.investment;

import org.springframework.hateoas.server.core.Relation;

import java.util.Objects;

@Relation(itemRelation = "investment", collectionRelation = "investments")
public class InvestmentDto {
    private String id;
    private String name;
    private String symbol;

    public InvestmentDto() {
    }

    private InvestmentDto(InvestmentDtoBuilder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.symbol = builder.symbol;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InvestmentDto that = (InvestmentDto) o;
        return id.equals(that.id) && name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    public static InvestmentDtoBuilder builder() {
        return new InvestmentDtoBuilder();
    }

    public static class InvestmentDtoBuilder {
        private String id;
        private String name;
        private String symbol;

        public InvestmentDtoBuilder id(String id) {
            this.id = id;
            return this;
        }

        public InvestmentDtoBuilder name(String name) {
            this.name = name;
            return this;
        }

        public InvestmentDtoBuilder symbol(String symbol) {
            this.symbol = symbol;
            return this;
        }

        public InvestmentDto build() {
            return new InvestmentDto(this);
        }
    }

    public static InvestmentDto from(Investment investment) {
        return InvestmentDto.builder()
                .id(investment.getId())
                .name(investment.getName())
                .symbol(investment.getSymbol())
                .build();
    }
}
