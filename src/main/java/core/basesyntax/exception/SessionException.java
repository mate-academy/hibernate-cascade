package core.basesyntax.exception;

public class SessionException extends RuntimeException {
    SessionException(String message, Throwable ex) {
        super(message, ex);
    }
}
