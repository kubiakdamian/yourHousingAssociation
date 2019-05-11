package pl.qbsapps.yourHousingAssociation.exception;

import pl.qbsapps.yourHousingAssociation.model.exception.ExceptionCode;

public class FeeAlreadyPaidException extends AbstractBadRequestException {
    public FeeAlreadyPaidException(String message, ExceptionCode code) {
        super(message, code);
    }

    public FeeAlreadyPaidException(String message, Throwable cause, ExceptionCode code) {
        super(message, cause, code);
    }

    public FeeAlreadyPaidException() {
        super("Fee already paid", ExceptionCode.FEE_ALREADY_PAID);
    }
}
