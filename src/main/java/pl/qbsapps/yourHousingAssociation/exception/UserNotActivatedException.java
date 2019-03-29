package pl.qbsapps.yourHousingAssociation.exception;

import pl.qbsapps.yourHousingAssociation.model.exception.ExceptionCode;

public class UserNotActivatedException extends AbstractBadRequestException {


    public UserNotActivatedException(String message, ExceptionCode code) {
        super(message, code);
    }

    public UserNotActivatedException(String message, Throwable cause, ExceptionCode code) {
        super(message, cause, code);
    }

    public UserNotActivatedException() {
        super("User is not activated", ExceptionCode.USER_NOT_ACTIVATED);
    }
}
