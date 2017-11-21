package component;

import domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import static config.Application.users;

@Component
public class SessionDisconnectedEventListener implements ApplicationListener<SessionDisconnectEvent> {

    private static final Logger logger = LoggerFactory.getLogger(SessionDisconnectedEventListener.class);

    @Override
    public void onApplicationEvent(SessionDisconnectEvent sessionDisconnectEvent) {
        User user = new User(sessionDisconnectEvent.getUser().getName());
        logger.info(user.getName() + " has logged out.");
        users.remove(user);
    }
}