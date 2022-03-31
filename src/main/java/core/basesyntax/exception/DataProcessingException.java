package core.basesyntax.exception;

public class DataProcessingException extends RuntimeException {
    public DataProcessingException(String massage, Throwable ex) {
        super(massage, ex);
    }
}
