package controller;

import domain.Message;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class MessageController {

    private static final Logger logger = LoggerFactory.getLogger(MessageController.class);

    @MessageMapping("/publish")
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
