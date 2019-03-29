package pl.qbsapps.yourHousingAssociation.model.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ExceptionCode {
    USER_NOT_FOUND("UNF"),
    USER_NOT_ACTIVATED("UNA"),
    USER_ALREADY_EXISTS("UAE"),
    FEE_ALREADY_ADDED("FAA");

    private String code;
}
