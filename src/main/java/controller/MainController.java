package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

import static config.Application.users;

@Controller
public class MainController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @RequestMapping(value = "/")
    public String home() {
        return "chatroom";
    }

    @RequestMapping("/login")
    public String login() throws Exception {
        return "login";
    }

    @SubscribeMapping("/queue/online")
    public void retrieveOnlineUsers(Principal p) {
        String user = p.getName();
        users.add(user);
        messagingTemplate.convertAndSendToUser(user, "/queue/online", users);
    }

    @Scheduled(fixedRate = 10000)
    public void sendOnlineUsers() {
        messagingTemplate.convertAndSend("/system/online", users);
    }
}
