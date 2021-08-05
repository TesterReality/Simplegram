package com.simplegram.controller;

import com.simplegram.config.ConfigProperties;
import com.simplegram.entity.User;
import com.simplegram.payload.response.MessageResponse;
import com.simplegram.security.services.UserDetailsImpl;
import com.simplegram.payload.request.LoginRequest;
import com.simplegram.payload.request.SignupRequest;
import com.simplegram.payload.response.JwtResponse;
import com.simplegram.repository.UserRepository;
import com.simplegram.security.jwt.JwtUtils;
import com.simplegram.services.ImageGenerationService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.util.*;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RequiredArgsConstructor
@ComponentScan(basePackages = "com.simplegram")
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final JwtUtils jwtUtils;
    private final ImageGenerationService imageGenerationService;
    private final MessageSource messageSource;
    private final ConfigProperties config;

    private boolean isValidAvatar;
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getLogin(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = new ArrayList<String>();
        roles.add(userDetails.getRole());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getLogin(),
                userDetails.getAvatar(),
                roles));
    }

    @RequestMapping(path = "/signup", method = POST)
    public ResponseEntity<?> registerUser(@RequestPart(name = "userData") SignupRequest signupRequest,
                                          @RequestPart(name = "file", required = false) MultipartFile file) {
        isValidAvatar = false;
        if (userRepository.existsByLogin(signupRequest.getLogin())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse(messageSource.getMessage("error.loginBusy",
                            null, Locale.ENGLISH)));
        }

        // Создаем новый аккаунт
        User user = new User();
        user.setUsername(signupRequest.getUsername());
        user.setLogin(signupRequest.getLogin());
        user.setPassword(encoder.encode(signupRequest.getPassword()));
        user.setOnlineDate(LocalDateTime.now());

        if (file != null) {
            if (!file.isEmpty()) {
                try {
                    isValidAvatar = true;
                    String pathToUserAvatrs = config.getUploadPath() + "avatars/";
                    String orgName = file.getOriginalFilename();
                    File dirUpload = new File(pathToUserAvatrs);
                    File f = new File(dirUpload.getAbsolutePath());
                    if (!f.exists()) {
                        f.mkdirs();
                    }
                    String fullAvatarName = UUID.randomUUID().toString() + "." + getFileExtension(orgName);
                    File f1 = new File(dirUpload.getAbsolutePath() + "/" + fullAvatarName);
                    try (OutputStream os = new FileOutputStream(f1)) {
                        os.write(file.getBytes());
                    }
                    user.setAvatar(fullAvatarName);
                } catch (IOException e) {
                    //Если не удалось записать файл
                    e.printStackTrace();
                }
            }
        }
        if (!isValidAvatar)
            //значит пользователь не загрузил аватарку или с ней что-то не так
            user.setAvatar(imageGenerationService.loadAvatarFromUrl(user.getLogin(), config.getUploadPath()));

        userRepository.save(user);

        return ResponseEntity.ok(messageSource.getMessage("success.registration",
                null, Locale.ENGLISH));
    }

    //метод определения расширения файла
    private static String getFileExtension(String fileName) {
        if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        else return "";
    }
}
