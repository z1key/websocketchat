package controller;

import config.Application;
import domain.Message;
import domain.UserMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;


@Controller
public class LoginController {

    private static final String ROOM = "/topic/all";

    private SimpMessagingTemplate template;

    @Autowired
    public LoginController(SimpMessagingTemplate messagingTemplate) {
        this.template = messagingTemplate;
    }

    @MessageMapping("/login")
    public void login(UserMessage user) throws Exception {
        if (Application.users.contains(user)) {
            template.convertAndSend("UserMessage already exists!");
        }
        Application.users.add(user);
        template.convertAndSend(ROOM, new Message("User " + user.getName() + " has joined chat!"));
    }
}
