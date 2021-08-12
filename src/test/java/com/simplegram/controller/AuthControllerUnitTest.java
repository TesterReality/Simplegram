package com.simplegram.controller;

import com.simplegram.config.ConfigProperties;
import com.simplegram.entity.User;
import com.simplegram.exceptions.BadRequestException;
import com.simplegram.payload.request.LoginRequest;
import com.simplegram.payload.request.SignupRequest;
import com.simplegram.payload.response.JwtResponse;
import com.simplegram.repository.UserRepository;
import com.simplegram.security.jwt.JwtUtils;
import com.simplegram.security.services.UserDetailsImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class AuthControllerUnitTest {
    String accessToken = UUID.randomUUID().toString();

    @Test
    public void authenticateUser_userExits_expectSuccess() throws Exception {
        LoginRequest request = new LoginRequest();
        request.setLogin("test");
        request.setPassword("test");

        UserDetailsImpl details = new UserDetailsImpl("15", "Andrew", "test", "test", null);

        Authentication authentication = mock(Authentication.class);
        AuthenticationManager authenticationManager = mock(AuthenticationManager.class);
        JwtUtils jwtutils = mock(JwtUtils.class);

        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(details);
        when(jwtutils.generateJwtToken(eq(authentication))).thenReturn(accessToken);

        AuthController controller = new AuthController(authenticationManager, null, null, jwtutils, null, null);
        JwtResponse response = controller.authenticateUser(request);
        Assertions.assertEquals(response.getAccessToken(), accessToken);
    }

    @Test
    public void registerUser_existLogin_exceptBadRequestException() throws Exception {
        SignupRequest request = new SignupRequest();
        request.setLogin("test");
        request.setPassword("test");
        request.setUsername("Bob");

        UserRepository repository = mock(UserRepository.class);

        when(repository.existsByLogin(any())).thenReturn(false);
        when(repository.existsByLogin(Mockito.eq("test"))).thenReturn(true);

        AuthController controller = new AuthController(null, repository, null, null, null, null);
        Assert.assertThrows(BadRequestException.class, () -> controller.registerUser(request, null));
    }

    @Test
    public void registerUser_userExits_expectSuccess() throws Exception {
        SignupRequest request = new SignupRequest();
        request.setLogin("test");
        request.setPassword("test");
        request.setUsername("Alice");

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);

        UserRepository repository = mock(UserRepository.class);
        ConfigProperties config = mock(ConfigProperties.class);
        PasswordEncoder encoder = mock(PasswordEncoder.class);
        MockMultipartFile firstFile = new MockMultipartFile("data", "filename.png", "application/json", "hackerString".getBytes());

        when(repository.existsByLogin(any())).thenReturn(false);
        when(repository.existsByLogin(Mockito.eq("test"))).thenReturn(true);
        when(config.getUploadPath()).thenReturn("uploads");
        when(repository.save(captor.capture())).thenAnswer(e -> e.getArguments()[0]);
        //when(firstFile.transferTo(any())).thenAnswer();

        AuthController controller = new AuthController(null, repository, encoder, null, null, config);

        request.setLogin("test1");
        controller.registerUser(request, firstFile);

        User userWithAvatar = captor.getValue();
        Assert.assertNotNull(userWithAvatar.getAvatar());
    }

}
