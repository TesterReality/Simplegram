package com.simplegram.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.simplegram.entity.User;
import com.simplegram.payload.request.LoginRequest;
import com.simplegram.payload.request.SignupRequest;
import com.simplegram.repository.UserRepository;
import org.apache.commons.codec.CharEncoding;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class AuthControllerApiTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private UserRepository userRepository;

    @Before
    public void generateTestData() {
        User user = new User();
        user.setUsername("dudosikName");
        user.setLogin("dudosik");
        user.setPassword(encoder.encode("qwertyuiop"));
        user.setOnlineDate(LocalDateTime.now());

        userRepository.save(user);
    }

    @After
    public void deleteAllData() {
        userRepository.deleteAll();
    }

    @Test
    public void registerUser_success() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setUsername("dudos1");
        signupRequest.setLogin("dudosik1");
        signupRequest.setPassword("qwertyuiop");
        String json = mapper.writeValueAsString(signupRequest);

        MockMultipartFile file = new MockMultipartFile("userData", "url", MediaType.APPLICATION_JSON_VALUE, json.getBytes());

        mockMvc.perform(MockMvcRequestBuilders.multipart("/api/auth/signup")
                .file(file)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .characterEncoding(CharEncoding.UTF_8))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void registerUser_loginAlreadyTaken_expectBadRequestException() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setUsername("dudos1");
        signupRequest.setLogin("dudosik");
        signupRequest.setPassword("qwertyuiop");
        String json = mapper.writeValueAsString(signupRequest);

        MockMultipartFile file = new MockMultipartFile("userData", "url", MediaType.APPLICATION_JSON_VALUE, json.getBytes());

        mockMvc.perform(MockMvcRequestBuilders.multipart("/api/auth/signup")
                .file(file)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .characterEncoding(CharEncoding.UTF_8))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void authenticateUser_success() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setLogin("dudosik");
        loginRequest.setPassword("qwertyuiop");
        String json = mapper.writeValueAsString(loginRequest);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/signin")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andDo(print());
    }

    @Test
    public void authenticateUser_userUnregistered_expectBadRequestException() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setLogin("dudosikBad");
        loginRequest.setPassword("qwertyuiop");
        String json = mapper.writeValueAsString(loginRequest);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/signin")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andDo(print());
    }

}
