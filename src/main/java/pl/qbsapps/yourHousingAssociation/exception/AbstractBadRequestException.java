package pl.qbsapps.yourHousingAssociation.exception;

import pl.qbsapps.yourHousingAssociation.model.exception.ExceptionCode;

public class AbstractBadRequestException extends AppException {
    public AbstractBadRequestException(String message, ExceptionCode code) {
        super(message, code);
    }

    public AbstractBadRequestException(String message, Throwable cause, ExceptionCode code) {
        super(message, cause, code);
    }
}
