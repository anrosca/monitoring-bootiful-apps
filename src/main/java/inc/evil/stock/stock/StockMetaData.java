package inc.evil.stock.stock;

import java.util.Objects;

public class StockMetaData {
    private final String stockSymbol;
    private final String companyName;
    private final Price price;

    private StockMetaData(StockMetaDataBuilder builder) {
        this.stockSymbol = builder.stockSymbol;
        this.companyName = builder.companyName;
        this.price = builder.price;
    }

    public String getStockSymbol() {
        return stockSymbol;
    }

    public String getCompanyName() {
        return companyName;
    }

    public Price getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StockMetaData that = (StockMetaData) o;
        return stockSymbol.equals(that.stockSymbol) && companyName.equals(that.companyName) && price.equals(that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stockSymbol, companyName, price);
    }

    @Override
    public String toString() {
        return "StockMetaData{" +
                "stockSymbol='" + stockSymbol + '\'' +
                ", companyName='" + companyName + '\'' +
                ", price=" + price +
                '}';
    }

    public static StockMetaDataBuilder builder() {
        return new StockMetaDataBuilder();
    }

    public static class StockMetaDataBuilder {
        private String stockSymbol;
        private String companyName;
        private Price price;

        public StockMetaDataBuilder stockSymbol(String stockSymbol) {
            this.stockSymbol = stockSymbol;
            return this;
        }

        public StockMetaDataBuilder companyName(String companyName) {
            this.companyName = companyName;
            return this;
        }

        public StockMetaDataBuilder price(Price price) {
            this.price = price;
            return this;
        }

        public StockMetaData build() {
            return new StockMetaData(this);
        }
    }
}
