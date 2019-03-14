package pl.qbsapps.yourHousingAssociation.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import pl.qbsapps.yourHousingAssociation.exception.AbstractBadRequestException;
import pl.qbsapps.yourHousingAssociation.exception.AbstractNotFoundException;
import pl.qbsapps.yourHousingAssociation.exception.AppException;
import pl.qbsapps.yourHousingAssociation.model.exception.ExceptionApiInfo;

@ControllerAdvice
public class ExceptionHandlerController {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(AbstractBadRequestException.class)
    public @ResponseBody
    ExceptionApiInfo handleBadRequestException(AppException ex) {
        return new ExceptionApiInfo(ex.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(AbstractNotFoundException.class)
    public @ResponseBody
    ExceptionApiInfo handleNotFoundException(AppException ex) {
        return new ExceptionApiInfo(ex.getMessage());
    }
}
