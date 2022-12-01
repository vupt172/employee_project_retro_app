package com.vupt172.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.RestClientException;
@ResponseStatus(code= HttpStatus.BAD_REQUEST)
public class OverPermissionException extends RestClientException {

    public OverPermissionException(String msg) {
        super(msg);
    }
}
