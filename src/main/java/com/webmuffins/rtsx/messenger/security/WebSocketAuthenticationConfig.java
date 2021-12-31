package com.webmuffins.rtsx.messenger.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import static java.util.Objects.isNull;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketAuthenticationConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        ChannelInterceptor channelInterceptor = new ChannelInterceptor() {
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
                if (isNull(accessor)) {
                    return message;
                }

                UsernamePasswordAuthenticationToken simpUser = (UsernamePasswordAuthenticationToken) accessor.getHeader("simpUser");
                if (isNull(simpUser)) {
                    return message;
                }

                if (StompCommand.SEND.equals(accessor.getCommand())) {
                    SecurityContextHolder.getContext().setAuthentication(simpUser);
                }
                return message;
            }
        };

        registration.interceptors(channelInterceptor);
    }
}
