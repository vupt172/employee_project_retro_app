package com.vupt172.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.RestClientException;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class TokenRefreshException extends RestClientException {
    public TokenRefreshException(String msg) {
        super(msg);
    }
}
