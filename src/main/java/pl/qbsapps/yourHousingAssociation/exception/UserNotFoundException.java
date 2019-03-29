package pl.qbsapps.yourHousingAssociation.exception;

import pl.qbsapps.yourHousingAssociation.model.exception.ExceptionCode;

public class UserNotFoundException extends AbstractNotFoundException {


    public UserNotFoundException(String message, ExceptionCode code) {
        super(message, code);
    }

    public UserNotFoundException(String message, Throwable cause, ExceptionCode code) {
        super(message, cause, code);
    }

    public UserNotFoundException() {
        super("User not found", ExceptionCode.USER_NOT_FOUND);
    }
}
