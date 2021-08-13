package inc.evil.stock.investment;

public class InvestmentNotFoundException extends RuntimeException {
    public InvestmentNotFoundException(String message) {
        super(message);
    }
}
