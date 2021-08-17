package com.simplegram.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.simplegram.payload.request.LoginRequest;
import com.simplegram.payload.request.SignupRequest;
import org.apache.commons.codec.CharEncoding;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
//@ActiveProfiles("test")
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)

public class AuthControllerApiTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Test
    public void invoke_authenticateUser_success1() throws Exception {

        ObjectMapper mapper = new ObjectMapper();

        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setUsername("dudos1");
        signupRequest.setLogin("dudosik");
        signupRequest.setPassword("qwertyuiop");
        String json = mapper.writeValueAsString(signupRequest);

        MockMultipartFile file = new MockMultipartFile("userData", "url", MediaType.APPLICATION_JSON_VALUE, json.getBytes());

        mockMvc.perform(MockMvcRequestBuilders.multipart("/api/auth/signup")
                .file(file)
                .content(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .characterEncoding(CharEncoding.UTF_8))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void invoke_authenticateUser_success() throws Exception {
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


}
