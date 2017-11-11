package controller;

import domain.ServiceMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;

import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
public class LogoutController {

    private static final String CHANNEL = "/system/logout";

    private SimpMessagingTemplate template;

    @Autowired
    public LogoutController(SimpMessagingTemplate messagingTemplate) {
        this.template = messagingTemplate;
    }

    @RequestMapping("/logout")
    public String logout(HttpSession httpSession, WebSocketSession socketSession) throws IOException {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        String user = securityContext.getAuthentication().getName();
        httpSession.invalidate();
        socketSession.close(CloseStatus.NORMAL);
        template.convertAndSend(CHANNEL, new ServiceMessage(ServiceMessage.Type.LOGGED_OUT, user));
        return "index";
    }
}
