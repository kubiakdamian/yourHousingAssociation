package pl.qbsapps.yourHousingAssociation.exception;

public class AbstractBadRequestException extends AppException {
    public AbstractBadRequestException(String message) {
        super(message);
    }

    public AbstractBadRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
