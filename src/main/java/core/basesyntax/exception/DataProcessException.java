package core.basesyntax.exception;

public class DataProcessException extends RuntimeException {
    public DataProcessException(String message, Throwable e) {
        super(message,e);
    }
}
