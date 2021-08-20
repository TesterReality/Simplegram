package com.simplegram.controller;

import com.simplegram.security.jwt.AuthTokenFilter;
import com.simplegram.services.ChatMemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/chats")
public class ChatsController {
    private final ChatMemberService chatMemberService;
    private final AuthTokenFilter authTokenFilter;

    @PostMapping("/getChat")
    // public ResponseEntity authenticateUser(@RequestParam(name = "userlogin") String userLogin) {
    public ResponseEntity authenticateUser() {

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();
        String hh = userDetails.getUsername();
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
                .badRequest()
                .body("d");
    }
}
