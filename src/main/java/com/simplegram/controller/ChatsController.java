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
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Log4j2
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/chats")
public class ChatsController {
    private final ChatMemberService chatMemberService;
    private final AuthTokenFilter authTokenFilter;

    // @PostMapping("/getChat")
    @GetMapping("/getChat")
    @PreAuthorize("isAuthenticated()")
    //public ResponseEntity authenticateUser(@RequestParam String login) {
    public ResponseEntity authenticateUser() {

        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<String> usersRoomId = chatMemberService.getAllRoomIDByUserID(userDetails.getId());


        //   String hh = userDetails.getUsername();
        // List<String> allUserChatsID = chatMemberService.getAllRoomIDByUserID(hh.getId());
        //authTokenFilter.
        /*
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getLogin(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();*/
        String test = "fdfd";

        return ResponseEntity
                .ok()
                .body(userDetails.getLogin());
    }
}
