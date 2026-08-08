package core.basesyntax.exception;

public class DataProcessingException extends RuntimeException {
    public DataProcessingException(String message) {
        super(message);
    }

    public DataProcessingException(String message, Throwable couse) {
        super(message, couse);
    }
}
