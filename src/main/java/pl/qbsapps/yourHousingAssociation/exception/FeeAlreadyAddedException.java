package pl.qbsapps.yourHousingAssociation.exception;

import pl.qbsapps.yourHousingAssociation.model.exception.ExceptionCode;

public class FeeAlreadyAddedException extends AbstractBadRequestException {
    public FeeAlreadyAddedException(String message, ExceptionCode code) {
        super(message, code);
    }

    public FeeAlreadyAddedException(String message, Throwable cause, ExceptionCode code) {
        super(message, cause, code);
    }

    public FeeAlreadyAddedException(){
        super("Fee already added", ExceptionCode.FEE_ALREADY_ADDED);
    }
}
