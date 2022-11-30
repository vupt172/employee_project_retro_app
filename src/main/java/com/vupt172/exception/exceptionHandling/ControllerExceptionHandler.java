package com.vupt172.exception.exceptionHandling;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
    public ApiError methodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex, WebRequest request){
        ApiError errorMessage=new ApiError(HttpStatus.BAD_REQUEST.value(),
                new Date(),
                "MethodArgumentTypeMismatchException",
                "Path variable or param in request is invalid type :"+ex.getName()+"="+ex.getValue(),
              //  request.getDescription(false)
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
@ResponseStatus(HttpStatus.BAD_REQUEST)
@ExceptionHandler(MethodArgumentNotValidException.class)
public ApiError handleValidationExceptions(
        MethodArgumentNotValidException ex,WebRequest request) {
    Map<String, String> detail = new HashMap<>();
    ex.getBindingResult().getAllErrors().forEach((error) -> {
        String fieldName = ((FieldError) error).getField();
        String errorMessage = error.getDefaultMessage();
        detail.put(fieldName, errorMessage);
    });
    ApiError apiError=new ApiError(HttpStatus.BAD_REQUEST.value(),
            new Date(),
            "MethodArgumentNotValidException",
            detail,
            request.getDescription(false)
            );
    return apiError;
}
}
