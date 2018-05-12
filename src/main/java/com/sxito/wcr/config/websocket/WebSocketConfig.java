package com.sxito.wcr.config.websocket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer{
    @Bean
    public WebSocketHandler newStudentHandler() {
        return new StudentWebSocket();
    }
    @Bean
    public WebSocketHandler newTeacherHandler() {
        return new TeacherWebSocket();
    }
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(newStudentHandler(),"/studentwebsocket").setAllowedOrigins("*").addInterceptors(new StudentHandShake());
        registry.addHandler(newTeacherHandler(),"/teacherwebsocket").setAllowedOrigins("*").addInterceptors(new HandShake());
    }
}