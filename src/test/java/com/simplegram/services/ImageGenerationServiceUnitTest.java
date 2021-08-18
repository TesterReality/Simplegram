package com.simplegram.services;

import com.simplegram.config.ConfigProperties;
import com.simplegram.repository.UserRepository;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ImageGenerationServiceUnitTest {
    //if you will run in Debug, read this: https://github.com/mockito/mockito/issues/1267
    String testDirectoryPath = "build/test/uploads";

    @Before
    public void createTestDir() throws IOException {
        File userTestDirectory = new File(testDirectoryPath + "/test");
        FileUtils.createParentDirectories(userTestDirectory);
    }

    @After
    public void delTestDir() throws IOException {
        File userTestDirectory = new File(testDirectoryPath);
        FileUtils.deleteDirectory(userTestDirectory);
    }

    @Test
    public void loadAvatarFromUrl_updateAvatarByUUID_wasCalledSuccess() throws Exception {
        String userLogin = "Bob";
        String userId = "0x000";
        String nameTempAvatar = null;
        String correctURL = null;

        ConfigProperties config = mock(ConfigProperties.class);
        UserRepository userRepository = mock(UserRepository.class);
        RestTemplate restTemplate = mock(RestTemplate.class);
        ResponseEntity<byte[]> responseEntity = mock(ResponseEntity.class);

        File testImage = Paths.get("uploads",
                "avatars", "0d8d049e-adbc-449c-82f4-3acf928033e6.png").toFile();

        ArgumentCaptor<String> captorUserAvatarName = ArgumentCaptor.forClass(String.class);

        when(responseEntity.getBody()).thenReturn(Files.readAllBytes(testImage.toPath()));

        when(config.getImageGenerator()).thenReturn("https://api.multiavatar.com");
        when(config.getUploadPath()).thenReturn(testDirectoryPath);
        when(restTemplate.exchange(anyString(), any(HttpMethod.class), any(), eq(byte[].class)))
                .thenReturn(responseEntity);
        when(restTemplate.getMessageConverters()).thenReturn(new ArrayList<>());

        doNothing().when(userRepository).updateAvatarByUUID(anyString(), captorUserAvatarName.capture());

        ImageGenerationService imgService = new ImageGenerationService(null, config, userRepository, restTemplate);
        imgService.loadAvatarFromUrl(userLogin, userId);

        nameTempAvatar = captorUserAvatarName.getValue();

        correctURL = config.getImageGenerator() + "/" + userLogin + ".png";

        verify(restTemplate).exchange(eq(correctURL), any(HttpMethod.class), any(HttpEntity.class), eq(byte[].class));
        verify(userRepository).updateAvatarByUUID(eq(userId), eq(nameTempAvatar));
        Assert.assertFalse(nameTempAvatar.isEmpty());

        File userImage = Paths.get(testDirectoryPath, "avatars", nameTempAvatar).toFile();

        Assert.assertTrue(FileUtils.contentEquals(testImage, userImage));
    }
}
