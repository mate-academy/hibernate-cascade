package core.basesyntax.dao.exception;

public class EmptyTableException extends RuntimeException {
    public EmptyTableException(String message, Throwable cause) {
        super(message, cause);
    }
}
