package com.example.newsBlock.security.jwt;


import com.example.newsBlock.security.AppUserDetails;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.time.Duration;
import java.util.Date;

@Component
@Slf4j
public class JwtUtils {
    @Value("${app.jwt.secret}")
    private String jwtSecret;
    @Value("${app.jwt.tokenExpiration}")
    private Duration tokenExpiration;
    public String generateJwtToken(AppUserDetails userDetails){
        var string = userDetails.getUsername();
        userDetails.getAuthorities().forEach(System.out::println);
        log.info(string);
     return generateTokenFromUserName(string);
    }

    public String generateTokenFromUserName(String username) {
        long expirationTimeInMillis = tokenExpiration.toMillis();
        long currentTimeInMillis = new Date().getTime();
        long expirationDateInMillis = currentTimeInMillis + expirationTimeInMillis;

        Date expirationDate = new Date(expirationDateInMillis);

        log.info("Current time in millis: " + currentTimeInMillis);
        log.info("Token expiration time in millis: " + expirationTimeInMillis);
        log.info("Expiration date: " + expirationDate);
        String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(currentTimeInMillis))
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
        log.info(MessageFormat.format("utils {0}", token));
        return token;
    }
    public String getUsername(String token){
        return Jwts.parser().setSigningKey(jwtSecret)
                .parseClaimsJws(token).getBody().getSubject();
    }
    public boolean validate(String authToken){
        try {
             Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
             return true;
        }catch (SignatureException ex){
            log.error("Invalid signature: {}", ex.getMessage());
        }catch (MalformedJwtException e){
            log.error("Invalid token: {}", e.getMessage());
        }catch (ExpiredJwtException e){
            log.error("Token is expired {}: ", e.getMessage());
        }catch (UnsupportedJwtException e){
            log.error("Token is unsupported {}: ", e.getMessage());
        }
        catch (IllegalArgumentException e){
            log.error("Claims string is empty: {}", e.getMessage());
        }
        log.info("Filter false");
        return false;
    }
}
