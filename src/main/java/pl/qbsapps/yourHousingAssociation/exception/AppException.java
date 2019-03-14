package pl.qbsapps.yourHousingAssociation.exception;

import lombok.Getter;

@Getter
public class AppException extends RuntimeException {

    public AppException(String message) {
        super(message);
    }

    public AppException(String message, Throwable cause) {
        super(message, cause);
    }
}
