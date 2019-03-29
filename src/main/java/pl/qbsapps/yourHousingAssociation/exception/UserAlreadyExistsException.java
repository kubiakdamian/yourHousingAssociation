package pl.qbsapps.yourHousingAssociation.exception;

import pl.qbsapps.yourHousingAssociation.model.exception.ExceptionCode;

public class UserAlreadyExistsException extends AbstractBadRequestException {

    public UserAlreadyExistsException(String message, ExceptionCode code) {
        super(message, code);
    }

    public UserAlreadyExistsException(String message, Throwable cause, ExceptionCode code) {
        super(message, cause, code);
    }

    public UserAlreadyExistsException() {
        super("User already exists", ExceptionCode.USER_ALREADY_EXISTS);
    }
}
