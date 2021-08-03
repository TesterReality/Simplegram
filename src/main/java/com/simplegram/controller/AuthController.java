package com.simplegram.controller;

import com.simplegram.models.ERole;
import com.simplegram.models.ImgRegUrl;
import com.simplegram.models.User;
import com.simplegram.payload.request.LoginRequest;
import com.simplegram.payload.request.SignupRequest;
import com.simplegram.payload.response.JwtResponse;
import com.simplegram.payload.response.MessageResponse;
import com.simplegram.repository.UserRepository;
import com.simplegram.security.jwt.JwtUtils;
import com.simplegram.security.services.UserDetailsImpl;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Value("${simplegram.app.uploadPath}")
    private String uploadsDir;

    private final static Logger log = LoggerFactory.getLogger(AuthController.class);

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
    public ResponseEntity<?> registerUser(@ModelAttribute SignupRequest signupRequest) {

        if (userRepository.existsByLogin(signupRequest.getLogin())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("{error.loginBusy}"));
        }

        // Создаем новый аккаунт
        User user = new User(signupRequest.getUsername(),
                signupRequest.getLogin(),
                encoder.encode(signupRequest.getPassword()),
                DigestUtils.md5Hex(signupRequest.getLogin()),
                new Timestamp(System.currentTimeMillis()));


        if (signupRequest.getFile() != null) {
            try {

                String pathToUserAvatrs = uploadsDir + "avatars/";
                String orgName = signupRequest.getFile().getOriginalFilename();
                File dirUpload = new File(pathToUserAvatrs);
                File f = new File(dirUpload.getAbsolutePath());
                if (!f.exists()) {
                    f.mkdirs();
                }
                String fullAvatarName = DigestUtils.md5Hex(signupRequest.getLogin()) + "." + getFileExtension(orgName);
                File f1 = new File(dirUpload.getAbsolutePath() + "/" + fullAvatarName);

                try (OutputStream os = new FileOutputStream(f1)) {
                    os.write(signupRequest.getFile().getBytes());
                }
                user.setAvatar(fullAvatarName);

            } catch (IOException e) {
                //Если не удалось записать файл
                e.printStackTrace();
            }
        } else {
            //значит пользователь не загрузил аватарку, но мы получили от него url изображения с ресурса
            new ImgRegUrl(signupRequest.getImgUrl(), user.getLogin(), uploadsDir).loadAvatarFromUrl();
            user.setAvatar(user.getAvatar() + ".png");
        }
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("Пользователь успешно зарегистрирован!"));
    }

    //метод определения расширения файла
    private static String getFileExtension(String fileName) {
        if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        else return "";
    }
}
