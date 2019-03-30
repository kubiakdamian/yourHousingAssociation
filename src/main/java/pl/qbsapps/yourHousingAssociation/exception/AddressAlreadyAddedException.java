package pl.qbsapps.yourHousingAssociation.exception;

import pl.qbsapps.yourHousingAssociation.model.exception.ExceptionCode;

public class AddressAlreadyAddedException extends AbstractBadRequestException {
    public AddressAlreadyAddedException(String message, ExceptionCode code) {
        super(message, code);
    }

    public AddressAlreadyAddedException(String message, Throwable cause, ExceptionCode code) {
        super(message, cause, code);
    }

    public AddressAlreadyAddedException() {
        super("Address already added", ExceptionCode.ADDRESS_ALREADY_ADDED);
    }
}
