package controller;

import component.OnlineUsersPublisher;
import domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

import static config.Application.users;

@Controller
public class MainController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private OnlineUsersPublisher onlineUsersPublisher;

    @RequestMapping(value = "/")
    public String home() {
        return "chatroom";
    }

    @RequestMapping("/login")
    public String login() throws Exception {
        return "login";
    }

    @SubscribeMapping("/notifications")
    public void retrieveOnlineUsers(Principal p) {
        User user = new User(p.getName());
        users.add(user);
        onlineUsersPublisher.sendOnlineUsers();
        messagingTemplate.convertAndSendToUser(user.getName(), "/notifications", user);
    }
}
