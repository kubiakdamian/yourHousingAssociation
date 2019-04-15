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
    FEE_ALREADY_ADDED("FAA"),
    ADDRESS_ALREADY_ADDED("AAA"),
    VERIFICATION_KEY_NOT_FOUND("KNF"),
    WRONG_VERIFICATION_KEY("WVK"),
    MANAGERS_NOT_FOUND("MNF"),
    NO_REQUIRED_PERMISSIONS("NRP"),
    FEE_NOT_FOUND("FNF");

    private String code;
}
