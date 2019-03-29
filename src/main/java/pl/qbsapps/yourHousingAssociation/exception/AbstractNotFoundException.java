package pl.qbsapps.yourHousingAssociation.exception;

import pl.qbsapps.yourHousingAssociation.model.exception.ExceptionCode;

public class AbstractNotFoundException extends AppException {
    public AbstractNotFoundException(String message, ExceptionCode code) {
        super(message, code);
    }

    public AbstractNotFoundException(String message, Throwable cause, ExceptionCode code) {
        super(message, cause, code);
    }
}
