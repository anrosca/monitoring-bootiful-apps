package inc.evil.stock.investment;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CreateInvestmentDto {
    @NotNull(message = "{create.investment.investment-name.null}")
    @Size(min = 1, max = 200, message = "{create.investment.investment-name.size}")
    private String name;

    @NotNull(message = "{create.investment.symbol.null}")
    @Size(min = 1, max = 10, message = "{create.investment.symbol.size}")
    private String symbol;

    public CreateInvestmentDto() {
    }

    public CreateInvestmentDto(String name, String symbol) {
        this.name = name;
        this.symbol = symbol;
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

    public Investment toInvestment() {
        return Investment.builder()
                .name(name)
                .symbol(symbol)
                .build();
    }
}
