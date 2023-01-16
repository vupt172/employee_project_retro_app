package com.vupt172.security.oauth2.google;

import com.google.api.client.auth.oauth2.AuthorizationCodeResponseUrl;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.auth.oauth2.TokenResponseException;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.vupt172.context.GoogleContext;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
@Data
@Component
public class GoogleOAuth2Util {
    private GoogleContext googleContext;
    private String responseCode;
    private Credential credential;
    private TokenResponse tokenResponse;
    public static Logger logger= LoggerFactory.getLogger(GoogleOAuth2Util.class) ;
    @Autowired
    private RestTemplate restTemplate;

    public GoogleOAuth2Util(GoogleContext googleContext){
        System.out.println("GoogleeOAuth2Util-googleContext is autowiring");
        this.googleContext=googleContext;
    }
    public void extractResponseCodeURL(HttpServletRequest request){
        StringBuffer fullUrlBuf = request.getRequestURL();
        if (request.getQueryString() != null) {
            fullUrlBuf.append('?').append(request.getQueryString());
        }
        System.out.println("Url request :"+fullUrlBuf);
        AuthorizationCodeResponseUrl authResponse =
                new AuthorizationCodeResponseUrl(fullUrlBuf.toString());
        // check for user-denied error
        if (authResponse.getError() != null) {
            // authorization denied...
            logger.error("Authorization with google failed");
        } else {
            System.out.println("ResponseCode ="+authResponse.getCode());
            this.responseCode=authResponse.getCode();
            // request access token using authResponse.getCode()...
        }
    }
    public void getTokenFromResponseCode(){
        try {
            TokenResponse response=new GoogleAuthorizationCodeTokenRequest(new NetHttpTransport(),
                    new GsonFactory(),
                    googleContext.getTokenURL,
                    googleContext.clientId,
                    googleContext.clientSecret,
                    this.responseCode,
                    googleContext.redirectURI
            ).execute();
            System.out.println("Access token: " + response.getAccessToken());
            this.tokenResponse=response;
        } catch (TokenResponseException e) {
            System.out.println("TokenResponseException"+e.getMessage());
            if (e.getDetails() != null) {
                System.err.println("Error: " + e.getDetails().getError());
                if (e.getDetails().getErrorDescription() != null) {
                    System.err.println(e.getDetails().getErrorDescription());
                }
                if (e.getDetails().getErrorUri() != null) {
                    System.err.println(e.getDetails().getErrorUri());
                }
            } else {
                System.err.println(e.getMessage());
            }
        }
        catch( Exception e){
            System.out.println(e.getMessage());
        }
    }
    public Credential buildCredential(){
        this.credential=new GoogleCredential.Builder()
                .setClientSecrets(googleContext.clientId,googleContext.clientSecret)
                .setJsonFactory(new GsonFactory()).setTransport(new NetHttpTransport())
                .build()
                .setAccessToken(this.tokenResponse.getAccessToken())
                .setRefreshToken(tokenResponse.getAccessToken());
        return this.credential;
    }
    public GoogleMailInfo getGoogleMailInfo(){
  GoogleMailInfo mailInfo=restTemplate.getForObject("?alt=json&access_token="+this.credential.getAccessToken(), GoogleMailInfo.class);
  return mailInfo;
    }
}
