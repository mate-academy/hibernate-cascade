package core.basesyntax.lib;

public class DataProcessingException extends RuntimeException {
    public DataProcessingException(String message) {
        super(message);
    }

    public DataProcessingException(String message, Throwable e) {
        super(message);
    }
}
