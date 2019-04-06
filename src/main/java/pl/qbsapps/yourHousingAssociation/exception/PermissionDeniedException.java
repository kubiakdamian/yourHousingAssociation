package pl.qbsapps.yourHousingAssociation.exception;

import pl.qbsapps.yourHousingAssociation.model.exception.ExceptionCode;

public class PermissionDeniedException extends AbstractUnauthorizedException {
    public PermissionDeniedException(String message, ExceptionCode code) {
        super(message, code);
    }

    public PermissionDeniedException(String message, Throwable cause, ExceptionCode code) {
        super(message, cause, code);
    }

    public PermissionDeniedException() {
        super("Permission denied", ExceptionCode.NO_REQUIRED_PERMISSIONS);
    }
}
