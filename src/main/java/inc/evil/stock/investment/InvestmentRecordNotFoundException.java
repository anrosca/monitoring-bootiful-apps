package inc.evil.stock.investment;

public class InvestmentRecordNotFoundException extends RuntimeException {
    public InvestmentRecordNotFoundException(String message) {
        super(message);
    }
}
