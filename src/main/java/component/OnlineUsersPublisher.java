package component;

import domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.Collectors;

import static config.Application.users;

@Component
public class OnlineUsersPublisher {

    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private OnlineUsersPublisher(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @Scheduled(fixedRate = 10000)
    public void sendOnlineUsers() {
        messagingTemplate.convertAndSend("/system/online", getUserNames());
    }

    public static Set<String> getUserNames() {
        return users.stream().map(User::getName).collect(Collectors.toSet());
    }
}
