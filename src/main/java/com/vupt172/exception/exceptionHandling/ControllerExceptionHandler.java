package com.vupt172.exception.exceptionHandling;

import com.vupt172.exception.ElementNotExistException;
import com.vupt172.exception.DataUniqueException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Date;

@RestControllerAdvice
public class ControllerExceptionHandler {
    /*@ExceptionHandler(value={ElementNotExistException.class})
    @ResponseStatus(value= HttpStatus.BAD_REQUEST)
    public ErrorMessage elementNotExistException(ElementNotExistException ex, WebRequest request){
        ErrorMessage errorMessage=new ErrorMessage(HttpStatus.NOT_FOUND.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false)
                );
        return errorMessage;
    }*/
    @ExceptionHandler(value={MethodArgumentTypeMismatchException.class})
    @ResponseStatus(value=HttpStatus.BAD_REQUEST)
    public ErrorMessage methodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex,WebRequest request){
        ErrorMessage errorMessage=new ErrorMessage(HttpStatus.BAD_REQUEST.value(),
                new Date(),
                "Path variable or param in request is invalid type :"+ex.getName()+"="+ex.getValue(),
                request.getDescription(false)
                );
        return errorMessage;
    }
/*    @ExceptionHandler(value={DataUniqueException.class})
    @ResponseStatus(value= HttpStatus.BAD_REQUEST)
    public ErrorMessage dataUniqueException(DataUniqueException ex, WebRequest request){
        ErrorMessage errorMessage=new ErrorMessage(HttpStatus.BAD_REQUEST.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false)
        );
        return errorMessage;
    }*/
}
