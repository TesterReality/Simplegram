package com.simplegram.security.jwt;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.MessageSource;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;

@RequiredArgsConstructor
@Log4j2
@Component
public class AuthEntryPointJwt implements AuthenticationEntryPoint {

    private final MessageSource messageSource;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {

        log.error(messageSource.getMessage("error.unauthorized",
                null, Locale.ENGLISH) + ": {}", authException.getMessage());
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, messageSource.getMessage("error.unauthorized",
                null, Locale.ENGLISH));
    }

}