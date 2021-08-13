package com.simplegram.controller;

import com.simplegram.config.ConfigProperties;
import com.simplegram.entity.User;
import com.simplegram.repository.UserRepository;
import com.simplegram.services.ImageGenerationService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.nio.file.Paths;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.Silent.class)
public class ImageGenerationServiceUnitTest {

    @Test
    public void loadAvatarFromUrl_updateAvatarByUUID_wasCalledSuccess() throws Exception {
        String userLogin = "Bob";
        String userId = "0x000";

        ConfigProperties config = mock(ConfigProperties.class);
        UserRepository userRepository = mock(UserRepository.class);
        RestTemplate restTemplate = mock(RestTemplate.class);
        ResponseExtractor<Void> responseExtractor = mock(ResponseExtractor.class);

        when(config.getImageGenerator()).thenReturn("https://api.multiavatar.com");
        when(config.getUploadPath()).thenReturn("uploads");
        when(restTemplate.execute(any(), any(), any(), any())).thenReturn(true);

        ImageGenerationService imgService = new ImageGenerationService(null, config, userRepository, restTemplate);
        imgService.loadAvatarFromUrl(userLogin, userId);

        verify(restTemplate).execute(anyString(), any(HttpMethod.class), any(), any());
        verify(userRepository).updateAvatarByUUID(any(), any());

        RestTemplate restTemplate1 = new RestTemplate();
        imgService = new ImageGenerationService(null, config, userRepository, restTemplate1);
        imgService.loadAvatarFromUrl(userLogin, userId);

        String nameTempAvatar = imgService.getUserAvatarName();
        File test = Paths.get("uploads", "avatars", nameTempAvatar).toFile();

        Assert.assertTrue(test.exists());
    }
}
