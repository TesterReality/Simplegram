package VueSpringProj.Simplegram.controller;

import VueSpringProj.Simplegram.websocket.Greeting;
import VueSpringProj.Simplegram.websocket.HelloMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
public class GreetingController {

    @MessageMapping("/hello")//откуда получаем
    @SendTo("/topic/greetings")//куда отдаем
    public Greeting greeting(HelloMessage message) throws Exception {
        Thread.sleep(1000);
        return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
    }
}