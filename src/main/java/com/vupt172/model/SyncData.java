package com.vupt172.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.*;
@Data
public class SyncData {
 List<JsonEmployee> employeeList;
 String targetUrl;
 boolean success;
 String error;
 boolean unAuthorizedRequest;
 boolean __abp;
 public SyncData(){}
 @JsonCreator
    public SyncData(
@JsonProperty("result") List<JsonEmployee> employeeList,
@JsonProperty("targetUrl")String targetUrl,
@JsonProperty("success")Boolean success,
@JsonProperty("error")String error,
@JsonProperty("unAuthorizedRequest")boolean unAuthorizedRequest,
@JsonProperty("__abp")boolean __abp
 ){
this.employeeList=employeeList;
this.targetUrl=targetUrl;
this.success=success;
this.error=error;
this.unAuthorizedRequest=unAuthorizedRequest;
this.__abp=__abp;
 }
}
