package pl.qbsapps.yourHousingAssociation.exception;

import pl.qbsapps.yourHousingAssociation.model.exception.ExceptionCode;

public class InvalidCardNumberException extends AbstractBadRequestException {
    public InvalidCardNumberException(String message, ExceptionCode code) {
        super(message, code);
    }

    public InvalidCardNumberException(String message, Throwable cause, ExceptionCode code) {
        super(message, cause, code);
    }

    public InvalidCardNumberException() {
        super("Card number is not valid", ExceptionCode.INVALID_CARD_NUMBER);
    }
}
