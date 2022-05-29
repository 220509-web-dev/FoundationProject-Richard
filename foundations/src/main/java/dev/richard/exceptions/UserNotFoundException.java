package dev.richard.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
        super("The specified user does not exist.");
    }
    public UserNotFoundException(String message) {
        super(String.format("The specified user does not exist. Additional information: %s", message));
    }
}
