package pl.qbsapps.yourHousingAssociation.exception;

import pl.qbsapps.yourHousingAssociation.model.exception.ExceptionCode;

public class VerificationKeyNotFoundException extends AbstractNotFoundException {
    public VerificationKeyNotFoundException(String message, ExceptionCode code) {
        super(message, code);
    }

    public VerificationKeyNotFoundException(String message, Throwable cause, ExceptionCode code) {
        super(message, cause, code);
    }

    public VerificationKeyNotFoundException(){
        super("Verification key not found", ExceptionCode.VERIFICATION_KEY_NOT_FOUND);
    }
}
