package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import util.Channel;

import static org.springframework.messaging.simp.SimpMessageType.CONNECT;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractSecurityWebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/websocketchat").withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic", "/system", "/notifications");
        config.setApplicationDestinationPrefixes("/app", "/user");
        config.setUserDestinationPrefix("/user");
    }

    @Override
    protected void configureInbound(MessageSecurityMetadataSourceRegistry messages) {
        messages
                .nullDestMatcher().authenticated()
                .simpTypeMatchers(CONNECT).authenticated()
                .simpSubscribeDestMatchers(Channel.SYSTEM_ERROR.value()).permitAll()
                .simpDestMatchers("/app/publish*").hasRole("USER")
                .simpSubscribeDestMatchers("/user/**", "/topic/**", "/system/*").hasRole("USER")
                .anyMessage().denyAll();
    }

    @Bean
    public SimpMessagingTemplate messagingTemplate(SimpMessagingTemplate brokerMessagingTemplate) {
        brokerMessagingTemplate.setDefaultDestination(Channel.TOPIC_ALL.value());
        return brokerMessagingTemplate;
    }

    @Override
    protected boolean sameOriginDisabled() {
        return true;
    }


}
