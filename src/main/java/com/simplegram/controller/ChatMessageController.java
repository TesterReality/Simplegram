package com.simplegram.controller;

import com.simplegram.security.jwt.AuthTokenFilter;
import com.simplegram.security.services.UserDetailsImpl;
import com.simplegram.services.ChatMemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RequiredArgsConstructor
@RestController
//@RequestMapping("/im")
public class ChatMessageController {
    private final ChatMemberService chatMemberService;
    private final AuthTokenFilter authTokenFilter;

    @GetMapping("/im")
    @PreAuthorize("isAuthenticated()")
    //Здесь получаю сообщения с челиком или чатом
    public ResponseEntity sendMessage(@RequestParam("sel") String roomID) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String test = "fdfd";

        return ResponseEntity
                .ok()
                .body(roomID);
    }
}
