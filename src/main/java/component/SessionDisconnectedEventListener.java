package component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
public class SessionDisconnectedEventListener implements ApplicationListener<SessionDisconnectEvent> {

    private static final Logger logger = LoggerFactory.getLogger(SessionDisconnectedEventListener.class);

    @Override
    public void onApplicationEvent(SessionDisconnectEvent sessionDisconnectEvent) {
        logger.info(sessionDisconnectEvent.getUser().getName());
    }
}