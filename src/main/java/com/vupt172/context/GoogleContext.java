package com.vupt172.context;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;
@Component
public class GoogleContext {
    @Value("${com.vupt172.google.clientId}")
    public  String clientId;
    @Value("${com.vupt172.google.clientSecret}")
    public String clientSecret;
    @Value("${com.vupt172.google.redirectURI}")
    public  String redirectURI;
    @Value("${com.vupt172.google.getTokenURL}")
    public String getTokenURL;
    @Value("${com.vupt172.google.scopes}")
    public  List<String> listScope;
}
