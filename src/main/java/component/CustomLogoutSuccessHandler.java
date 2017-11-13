package component;

import domain.ServiceMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;
import util.Channel;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static domain.ServiceMessage.Type.LOGGED_OUT;

@Component
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

    private SimpMessagingTemplate template;

    @Autowired
    public CustomLogoutSuccessHandler(SimpMessagingTemplate messagingTemplate) {
        this.template = messagingTemplate;
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        if (authentication != null) {
            String user = authentication.getName();
            request.getSession().invalidate();
            template.convertAndSend(Channel.SYSTEM_EVENT.value(), new ServiceMessage(LOGGED_OUT, user));
        }
        response.sendRedirect("/login");
    }
}
