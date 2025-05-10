package core.basesyntax.exception;

public class DataProcessingException extends RuntimeException {
    public DataProcessingException(String message, Throwable ex) {
        super(message, ex);
    }
}
