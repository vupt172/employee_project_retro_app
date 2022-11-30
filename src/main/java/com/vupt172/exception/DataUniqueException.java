package com.vupt172.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.RestClientException;

@ResponseStatus(HttpStatus.CONFLICT)
public class DataUniqueException extends RestClientException {
    public DataUniqueException(String message){
        super(message);
    }
}
