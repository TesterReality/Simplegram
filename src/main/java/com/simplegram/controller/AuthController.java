package com.simplegram.controller;

import com.simplegram.config.ConfigProperties;
import com.simplegram.entity.ChatMessage;
import com.simplegram.entity.ChatMessageAttachments;
import com.simplegram.entity.ChatRoom;
import com.simplegram.entity.User;
import com.simplegram.entity.enums.ChatMessageAttachmentsType;
import com.simplegram.entity.enums.ChatMessageType;
import com.simplegram.exceptions.BadRequestException;
import com.simplegram.payload.request.LoginRequest;
import com.simplegram.payload.request.SignupRequest;
import com.simplegram.payload.response.JwtResponse;
import com.simplegram.repository.UserRepository;
import com.simplegram.security.jwt.JwtUtils;
import com.simplegram.security.services.UserDetailsImpl;
import com.simplegram.services.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.FilenameUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableAsync;
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
import java.util.UUID;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Log4j2
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
@EnableAsync
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final JwtUtils jwtUtils;
    private final ImageGenerationService imageGenerationService;
    private final ConfigProperties config;
    private final ChatMemberService chatMemberService;
    private final ChatRoomService chatRoomService;
    private final ChatMessageService chatMessageService;
    private final ChatMessageAttachmentsService attachmentsService;

    @PostMapping("/signin")
    public JwtResponse authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getLogin(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getLogin(),
                userDetails.getAvatar());
    }

    @RequestMapping(path = "/signup", method = POST)
    public ResponseEntity<?> registerUser(@Valid @RequestPart(name = "userData") SignupRequest signupRequest,
                                          @RequestPart(name = "file", required = false) MultipartFile file) {
        if (userRepository.existsByLogin(signupRequest.getLogin())) {
            throw new BadRequestException("exception.loginAlreadyTaken");
        }
        System.out.println(signupRequest.getLogin());
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

        userRepository.save(user);

        if (user.getAvatar() == null) {
            imageGenerationService.loadAvatarFromUrl(user.getLogin(), user.getId());
        }

        //chatMemberService
        //

        ChatRoom chat = new ChatRoom();
        chat.setCreator(user);
        chat.setName("testChat");
        chatRoomService.createChatRoom(chat);

        List<String> testLogin = new ArrayList<>();
        testLogin.add("dudos");
        testLogin.add("memes");

        chatRoomService.addUsersFromLoginToChatRoom(chat.getId(), testLogin);

        ChatMessage message = new ChatMessage();
        message.setChatId(chat.getId());
        message.setSenderId(user.getId());
        message.setMessage("testMessage");
        message.setType(ChatMessageType.UNREAD.toString());
        message.setDate(LocalDateTime.now());

        chatMessageService.saveChatMessage(message);

        ChatMessageAttachments attachments = new ChatMessageAttachments();
        attachments.setIdMessage(message.getId());
        attachments.setUrl("test.png");
        attachments.setType(ChatMessageAttachmentsType.IMAGE.toString());

        attachmentsService.saveAttachment(attachments);

        boolean test = chatRoomService.isChatRoomExist(chat.getId());
        /*
        ChatMember chatMember = new ChatMember();
        chatMember.setId_chat("e5490215-a743-4247-97a5-ffabd7f756b4");
        chatMember.setId_user("488ff5ab-da40-48ae-929d-f549837e3216");

        chatMemberService.saveChatMember(chatMember);*/

        List<String> list = new ArrayList<>();
        list = chatMemberService.getAllRoomIDByUserID(user.getId());

        boolean tes1 = chatMemberService.isUserAlreadyExitsInChat(chat.getId(), user.getLogin());

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
