package com.simplegram.security.jwt;

import com.simplegram.config.ConfigProperties;
import com.simplegram.security.services.UserDetailsImpl;
import io.jsonwebtoken.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Locale;

@RequiredArgsConstructor
@Log4j2
@Component
public class JwtUtils {

    private final ConfigProperties config;
    private final MessageSource messageSource;

    public String generateJwtToken(Authentication authentication) {
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject((userPrincipal.getLogin()))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + config.getJwtExpirationMs()))
                .signWith(SignatureAlgorithm.HS512, config.getJwtSecret())
                .compact();
    }

    public String getUserLoginFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(config.getJwtSecret()).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(config.getJwtSecret()).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            log.error(messageSource.getMessage("error.JWTsignature",
                    null, Locale.ENGLISH) + ": {}", e.getMessage());
        } catch (MalformedJwtException e) {
            log.error(messageSource.getMessage("error.JWTtoken",
                    null, Locale.ENGLISH) + ": {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error(messageSource.getMessage("error.JWTtokenExpired",
                    null, Locale.ENGLISH) + ": {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error(messageSource.getMessage("error.JWTtokenUnsupported",
                    null, Locale.ENGLISH) + ": {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error(messageSource.getMessage("error.JWTempty",
                    null, Locale.ENGLISH) + ": {}", e.getMessage());
        }
        return false;
    }
}
