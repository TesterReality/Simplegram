package VueSpringProj.Simplegram.controller;

import VueSpringProj.Simplegram.models.chat.ChatMessage;
import VueSpringProj.Simplegram.models.chat.ChatNotification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

}
