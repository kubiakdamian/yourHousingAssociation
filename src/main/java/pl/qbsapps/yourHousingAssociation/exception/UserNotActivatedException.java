package pl.qbsapps.yourHousingAssociation.exception;

public class UserNotActivatedException extends AbstractBadRequestException {
    public UserNotActivatedException(String message) {
        super(message);
    }

    public UserNotActivatedException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserNotActivatedException() {
        super("User is not activated");
    }
}
