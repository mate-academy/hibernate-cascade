package core.basesyntax.exception;

public class DataAccessException extends RuntimeException {
    public DataAccessException() {
        super();
    }

    public DataAccessException(String message) {
        super(message);
    }
}
