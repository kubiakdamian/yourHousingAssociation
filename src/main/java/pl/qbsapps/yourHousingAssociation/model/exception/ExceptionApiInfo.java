package pl.qbsapps.yourHousingAssociation.model.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExceptionApiInfo {
    private String message;
    private String code;

    public ExceptionApiInfo(String message, String code){
        this.message = message;
        this.code = code;
    }
}
