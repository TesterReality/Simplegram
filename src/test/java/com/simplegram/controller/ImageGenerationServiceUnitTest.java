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
import org.springframework.http.HttpMethod;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.Silent.class)
public class ImageGenerationServiceUnitTest {
    String testDirectoryPath = "build/test/uploads/avatars";

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

        File fileToCopy = Paths.get("uploads", "avatars", "0d8d049e-adbc-449c-82f4-3acf928033e6.png").toFile();
        File newFile = new File(testDirectoryPath + "/test.png");

        ArgumentCaptor<ResponseExtractor<Void>> captor = ArgumentCaptor.forClass(ResponseExtractor.class);


        FileInputStream inputStream = new FileInputStream(fileToCopy);
        FileOutputStream outputStream = new FileOutputStream(newFile);

        when(config.getImageGenerator()).thenReturn("https://api.multiavatar.com");
        when(config.getUploadPath()).thenReturn("uploads");
        when(restTemplate.execute(any(), any(), any(), captor.capture())).thenReturn(null);

        inputStream.close();
        outputStream.close();


        captor.getAllValues();

        RestTemplate restTemplate1 = new RestTemplate();
        ImageGenerationService imgService = new ImageGenerationService(null, config, userRepository, restTemplate);
        imgService.loadAvatarFromUrl(userLogin, userId);
        nameTempAvatar = imgService.getUserAvatarName();

        verify(restTemplate).execute(anyString(), any(HttpMethod.class), any(), any());
        verify(userRepository).updateAvatarByUUID(eq(userId), eq(nameTempAvatar));
        Assert.assertFalse(nameTempAvatar.isEmpty());

        //Assert.assertTrue(newFile.exists());
        // Assert.assertArrayEquals(FileUtils.readFileToByteArray(fileToCopy), FileUtils.readFileToByteArray(newFile));
    }
}
