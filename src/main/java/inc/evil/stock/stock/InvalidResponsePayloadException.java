package inc.evil.stock.stock;

public class InvalidResponsePayloadException extends RuntimeException {
    public InvalidResponsePayloadException(String message) {
        super(message);
    }
}
