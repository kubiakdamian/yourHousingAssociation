package pl.qbsapps.yourHousingAssociation.exception;

public class UserNotFoundException extends AbstractNotFoundException {
    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserNotFoundException() {
        super("User not found");
    }
}
