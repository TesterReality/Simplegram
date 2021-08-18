package com.simplegram.advice;

import com.simplegram.exceptions.BadRequestException;
import com.simplegram.exceptions.UnauthorizedException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Locale;

import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.Silent.class)
public class ExceptionAdviceUnitTest {

    @Test
    public void errorAnswer_throwBadRequestException_400code() {
        MessageSource messageSource = mock(MessageSource.class);

        ExceptionAdvice test = new ExceptionAdvice(messageSource);

        ResponseEntity<String> answer = test.errorAnswer(new BadRequestException("test"));

        int code = answer.getStatusCode().value();
        Assert.assertEquals(code, HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void errorAnswer_throwUnauthorizedException_401code() {
        MessageSource messageSource = mock(MessageSource.class);

        ExceptionAdvice test = new ExceptionAdvice(messageSource);

        ResponseEntity<String> answer = test.errorAnswer(new UnauthorizedException("test"));

        int code = answer.getStatusCode().value();
        Assert.assertEquals(code, HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    public void errorAnswer_throwBadRequestExceptionWhenLoginAlreadyTaken_loginAlreadyTakenMessage() {
        ResourceBundleMessageSource source = new ResourceBundleMessageSource();
        source.setBasenames("messages/messages");
        source.setUseCodeAsDefaultMessage(true);
        source.setDefaultEncoding("UTF-8");

        MessageSource messageSource = source;

        ExceptionAdvice test = new ExceptionAdvice(messageSource);

        ResponseEntity<String> answer = test.errorAnswer(new BadRequestException("exception.loginAlreadyTaken"));

        String errorMessage = answer.getBody();
        Assert.assertEquals(errorMessage, "Ошибка! Этот логин уже занят");
    }
}
