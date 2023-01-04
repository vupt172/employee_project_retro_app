package com.vupt172.security.jwt;

import com.vupt172.exception.JwtValidateException;
import com.vupt172.security.service.UserDetailsImpl;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;

@Component
public class JwtUtils {
    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);
    @Value("${com.vupt172.jwtSecret}")
    private String jwtSecret;

    @Value("${com.vupt172.jwtExpirationMs}")
    private long jwtExpirationMs;
    @Value("${com.vupt172.jwtRefreshExpirationMs}")
    private long jwtRefreshExpirationMs;

    public String generateJwtToken(Authentication authentication) {

        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject((userPrincipal.getUsername()))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }
    public String generateRefreshToken(Authentication authentication){
        UserDetailsImpl userPrincipal= (UserDetailsImpl) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject(userPrincipal.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime()+jwtRefreshExpirationMs))
                .signWith(SignatureAlgorithm.HS512,jwtSecret)
                .compact();
    }

    public String generateJwtTokenFromUsername(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String getUsernameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateJwtToken(String authToken) throws JwtValidateException {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature: {}", e.getMessage());
            throw new JwtValidateException("Invalid Jwt Signature");
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
            throw new JwtValidateException("Invalid Jwt Token");
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
            throw new JwtValidateException("JWT token has been expired");
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
            throw new JwtValidateException("JWT Token is unsupported");
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
           throw new JwtValidateException("JWT claims string is empty");
        }
    //return false;
    }
    public HashMap<String,Object> validateJwtToken1(String authToken) {
        HashMap<String,Object> results=new HashMap<>();
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            results.put("isValidate",true);
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature: {}", e.getMessage());
            results.put("isValidate",false);
            results.put("message","Invalid JWT signature");
            // throw new JwtValidateException("Invalid Jwt Signature");
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
            results.put("isValidate",false);
            results.put("message","Invalid JWT token");
            //   throw new JwtValidateException("Invalid Jwt Token");
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
            results.put("isValidate",false);
            results.put("message","Jwt Token has been expired");
            //   throw new JwtValidateException("JWT token has been expired");
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
            results.put("isValidate",false);
            results.put("message","Jwt Token is unsupported");
            //  throw new JwtValidateException("JWT Token is unsupported");
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
            results.put("isValidate",false);
            results.put("message","Jwt Claims string is empty");
            // throw new JwtValidateException("JWT Claims string is empty");
        }
        return results;
    }
}
