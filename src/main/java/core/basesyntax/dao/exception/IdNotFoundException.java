package core.basesyntax.dao.exception;

public class IdNotFoundException extends RuntimeException {
    public IdNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
