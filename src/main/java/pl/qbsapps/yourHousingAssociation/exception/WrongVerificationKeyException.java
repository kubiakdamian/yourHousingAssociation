package pl.qbsapps.yourHousingAssociation.exception;

import pl.qbsapps.yourHousingAssociation.model.exception.ExceptionCode;

public class WrongVerificationKeyException extends AbstractBadRequestException {
    public WrongVerificationKeyException(String message, ExceptionCode code) {
        super(message, code);
    }

    public WrongVerificationKeyException(String message, Throwable cause, ExceptionCode code) {
        super(message, cause, code);
    }

    public WrongVerificationKeyException(){
        super("Wrong verification key", ExceptionCode.WRONG_VERIFICATION_KEY);
    }
}
