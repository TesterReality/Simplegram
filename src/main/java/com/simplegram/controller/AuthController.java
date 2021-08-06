package com.simplegram.controller;

import com.simplegram.config.ConfigProperties;
import com.simplegram.entity.User;
import com.simplegram.payload.request.LoginRequest;
import com.simplegram.payload.request.SignupRequest;
import com.simplegram.payload.response.JwtResponse;
import com.simplegram.payload.response.MessageResponse;
import com.simplegram.repository.UserRepository;
import com.simplegram.security.jwt.JwtUtils;
import com.simplegram.security.services.UserDetailsImpl;
import com.simplegram.services.ImageGenerationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.FilenameUtils;
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
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Log4j2
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

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getLogin(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getLogin(),
                userDetails.getAvatar()));
    }

    @RequestMapping(path = "/signup", method = POST)
    public ResponseEntity<?> registerUser(@RequestPart(name = "userData") SignupRequest signupRequest,
                                          @RequestPart(name = "file", required = false) MultipartFile file) {
        if (userRepository.existsByLogin(signupRequest.getLogin())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse(messageSource.getMessage("error.loginAlreadyTaken",
                            null, Locale.ENGLISH)));
        }

        User user = new User();
        user.setUsername(signupRequest.getUsername());
        user.setLogin(signupRequest.getLogin());
        user.setPassword(encoder.encode(signupRequest.getPassword()));
        user.setOnlineDate(LocalDateTime.now());

        File userAvatarsDir = Paths.get(config.getUploadPath(), "avatars").toFile();
        if (!userAvatarsDir.exists()) {
            userAvatarsDir.mkdirs();
        }

        if (file != null && !file.isEmpty()) {
            try {
                String fullAvatarName = UUID.randomUUID().toString() + "." + FilenameUtils.getExtension(file.getOriginalFilename());
                file.transferTo(Paths.get(userAvatarsDir.getAbsolutePath(), fullAvatarName));
                user.setAvatar(fullAvatarName);
            } catch (IOException e) {
                log.error(e, e);
            }
        }
        if (user.getAvatar() == null) {
            String userAvatarName = imageGenerationService.loadAvatarFromUrl(user.getLogin(), userAvatarsDir);
            user.setAvatar(userAvatarName);
        }

        userRepository.save(user);
        return ResponseEntity.ok(messageSource.getMessage("success.registration",
                null, Locale.ENGLISH));
    }
}
