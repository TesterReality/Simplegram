package com.simplegram.controller;

import com.simplegram.config.ConfigProperties;
import com.simplegram.repository.UserRepository;
import com.simplegram.services.ImageGenerationService;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.*;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;
import org.springframework.core.io.Resource;

import java.io.*;
import java.nio.file.Paths;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.Silent.class)
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

        ConfigProperties config = mock(ConfigProperties.class);
        UserRepository userRepository = mock(UserRepository.class);
        RestTemplate restTemplate = mock(RestTemplate.class);
        ResponseEntity<Resource> responseEntity = mock(ResponseEntity.class);

        File fileToCopy = Paths.get("uploads", "avatars", "0d6b2ec1-46e3-4a10-b2cc-5b5082d91492.png").toFile();

        ResourceLoader resourceLoader = new DefaultResourceLoader();
        Resource resource = resourceLoader.getResource("file:" + fileToCopy.getAbsolutePath());

        FileInputStream inputStream = new FileInputStream(fileToCopy);

        when(responseEntity.getBody()).thenReturn(resource);
        when(config.getImageGenerator()).thenReturn("https://api.multiavatar.com");
        when(config.getUploadPath()).thenReturn(testDirectoryPath);
        when(restTemplate.exchange(anyString(), any(HttpMethod.class), any(), eq(Resource.class)))
                .thenReturn(responseEntity);

        inputStream.close();

        ImageGenerationService imgService = new ImageGenerationService(null, config, userRepository, restTemplate);
        imgService.loadAvatarFromUrl(userLogin, userId);
        nameTempAvatar = imgService.getUserAvatarName();

        verify(restTemplate).exchange(anyString(), any(HttpMethod.class), any(HttpEntity.class), eq(Resource.class));
        verify(userRepository).updateAvatarByUUID(eq(userId), eq(nameTempAvatar));
        Assert.assertFalse(nameTempAvatar.isEmpty());

        File newFiles = Paths.get(testDirectoryPath, "avatars", nameTempAvatar).toFile();

        Assert.assertArrayEquals(FileUtils.readFileToByteArray(fileToCopy), FileUtils.readFileToByteArray(newFiles));
    }
}
