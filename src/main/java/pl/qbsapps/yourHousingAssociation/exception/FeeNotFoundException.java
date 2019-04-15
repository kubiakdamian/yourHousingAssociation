package pl.qbsapps.yourHousingAssociation.exception;

import pl.qbsapps.yourHousingAssociation.model.exception.ExceptionCode;

public class FeeNotFoundException extends AbstractNotFoundException {
    public FeeNotFoundException(String message, ExceptionCode code) {
        super(message, code);
    }

    public FeeNotFoundException(String message, Throwable cause, ExceptionCode code) {
        super(message, cause, code);
    }

    public FeeNotFoundException() {
        super("Fee not found", ExceptionCode.FEE_NOT_FOUND);
    }
}
