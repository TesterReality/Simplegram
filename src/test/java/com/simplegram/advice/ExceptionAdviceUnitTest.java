package com.simplegram.advice;

import com.simplegram.exceptions.BadRequestException;
import com.simplegram.exceptions.UnauthorizedException;
import com.simplegram.payload.response.ExceptionResponse;
import com.simplegram.repository.UserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

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
    public void invalidDataAnswer_throwMethodArgumentNotValidException_400code() {
        MessageSource messageSource = mock(MessageSource.class);

        ExceptionAdvice test = new ExceptionAdvice(messageSource);
        List<FieldError> testField = new ArrayList<>();

        BindingResult bindingResult = mock(BindingResult.class);

        when(bindingResult.getFieldErrors()).thenReturn(testField);

        ResponseEntity<ExceptionResponse> answer = test.invalidDataAnswer(new MethodArgumentNotValidException(null,bindingResult));

        int code = answer.getStatusCode().value();
        Assert.assertEquals(code, HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void errorAnswer_throwBadRequestExceptionWhenLoginAlreadyTaken_loginAlreadyTakenMessage() {
        MessageSource messageSource = mock(MessageSource.class);

        ExceptionAdvice test = new ExceptionAdvice(messageSource);

        test.errorAnswer(new BadRequestException("exception.loginAlreadyTaken"));
        verify(messageSource).getMessage(eq("exception.loginAlreadyTaken"),any(), any(Locale.class));
    }
}
