package pl.qbsapps.yourHousingAssociation.model.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExceptionApiInfo {
    private String message;

    public ExceptionApiInfo(String message){
        this.message = message;
    }
}
