package pl.qbsapps.yourHousingAssociation.exception;

import lombok.Getter;
import pl.qbsapps.yourHousingAssociation.model.exception.ExceptionCode;

@Getter
public class AppException extends RuntimeException {

    private final ExceptionCode code;

    public AppException(String message, ExceptionCode code) {
        super(message);
        this.code = code;
    }

    public AppException(String message, Throwable cause, ExceptionCode code) {
        super(message, cause);
        this.code = code;
    }
}
