package component;

import domain.ServiceMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import util.Channel;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static domain.ServiceMessage.Type.LOGGED_IN;

@Component
public class CustomLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final SimpMessagingTemplate template;

    @Autowired
    public CustomLoginSuccessHandler(SimpMessagingTemplate messagingTemplate) {
        this.template = messagingTemplate;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        User user = (User) authentication.getPrincipal();
        template.convertAndSend(Channel.SYSTEM_EVENT.value(), new ServiceMessage(LOGGED_IN, user.getUsername()));
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
