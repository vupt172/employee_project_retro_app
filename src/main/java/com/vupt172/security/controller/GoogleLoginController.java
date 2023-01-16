package com.vupt172.security.controller;

import com.google.api.client.auth.oauth2.AuthorizationCodeRequestUrl;
import com.vupt172.context.GoogleContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

@RestController
public class GoogleLoginController {
    GoogleContext googleContext;
    @GetMapping("/google-login")
    public void loginGoogle(HttpServletRequest request, HttpServletResponse response) throws Exception{
        System.out.println("clientId :"+googleContext.clientId);
        String url =
                new AuthorizationCodeRequestUrl("https://accounts.google.com/o/oauth2/v2/auth", googleContext.clientId)
                        .setResponseTypes(Arrays.asList("code"))
                        .setScopes(googleContext.listScope)
                        //.setClientId(googleContext.clientId)
                        .setRedirectUri(googleContext.redirectURI).build();

        response.sendRedirect(url);
    }
   @Autowired
    public void setGoogleContext(GoogleContext googleContext) {
       System.out.println("GoogleLoginController-GoogleContext is autowiring");
        this.googleContext = googleContext;
    }

    public GoogleContext getGoogleContext() {
        return googleContext;
    }
}
