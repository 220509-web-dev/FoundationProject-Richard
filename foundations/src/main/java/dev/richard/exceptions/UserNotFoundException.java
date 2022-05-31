package dev.richard.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
        super("The specified user does not exist.");
    }
    public UserNotFoundException(String message) {
        super(String.format("The specified user does not exist. Additional information: %s", message));
    }

    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserNotFoundException(Throwable cause) {
        super(cause);
    }

    public UserNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
