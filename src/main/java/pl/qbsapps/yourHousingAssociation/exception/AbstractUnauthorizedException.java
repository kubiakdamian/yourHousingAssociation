package pl.qbsapps.yourHousingAssociation.exception;

import pl.qbsapps.yourHousingAssociation.model.exception.ExceptionCode;

public class AbstractUnauthorizedException extends AppException {
    AbstractUnauthorizedException(String message, ExceptionCode code) {
        super(message, code);
    }

    AbstractUnauthorizedException(String message, Throwable cause, ExceptionCode code) {
        super(message, cause, code);
    }
}
