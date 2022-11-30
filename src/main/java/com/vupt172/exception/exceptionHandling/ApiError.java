package com.vupt172.exception.exceptionHandling;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
@Data
@AllArgsConstructor
public class ApiError {
    private int statusCode;
    private Date timestamp;
    private String error;
    private  Object details;
    private  String path;
}
