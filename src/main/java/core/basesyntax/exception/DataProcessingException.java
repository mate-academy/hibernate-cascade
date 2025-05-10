package core.basesyntax.exception;

public class DataProcessingException extends RuntimeException {
    public DataProcessingException(String message, Exception cause) {
        super(message, cause);
    }
}
