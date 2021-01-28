package core.basesyntax.exception;

public class DataProcessException extends RuntimeException {
    public DataProcessException(String msg, Throwable throwable) {
        super(msg,throwable);
    }
}
