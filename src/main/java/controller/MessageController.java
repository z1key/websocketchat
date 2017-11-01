package controller;

import domain.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

/**
 * Created by UserMessage on 29.10.2017.
 */

@Controller
public class MessageController {

    @MessageMapping("/publish")
    @SendTo("/topic/all")
    public Message publish(Message message) {
        return message;
    }
}
