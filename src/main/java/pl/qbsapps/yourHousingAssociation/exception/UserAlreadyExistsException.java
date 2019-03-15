package pl.qbsapps.yourHousingAssociation.exception;

public class UserAlreadyExistsException extends AbstractBadRequestException {
    public UserAlreadyExistsException(String message) {
        super(message);
    }

    public UserAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserAlreadyExistsException() {
        super("User already exists");
    }
}
