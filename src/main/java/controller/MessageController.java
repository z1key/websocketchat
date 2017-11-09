package controller;

import domain.Message;
import org.apache.commons.lang3.StringUtils;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Created by UserMessage on 29.10.2017.
 */

@Controller
public class MessageController {

    @MessageMapping("/publishAll")
    @SendTo("/topic/all")
    public Message publish(@RequestBody Message message) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        String name = securityContext.getAuthentication().getName();
        if (StringUtils.isNotBlank(name) && StringUtils.isNotBlank(message.getContent())) {
            message.setSender(name);
        } else {
            return null;
        }
        return message;
    }
}
