package com.sxito.wcr.config.websocket;

import com.sxito.wcr.bean.UserInfo;
import com.sxito.wcr.config.security.JwtTokenUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;
import java.util.Map;

public class HandShake implements HandshakeInterceptor {
    private static Logger logger = Logger.getLogger(TeacherWebSocket.class);
    @Bean
    public JwtTokenUtil newJwtTokenUtil() {
        return new JwtTokenUtil();
    }
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        if (request instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
            String token = ((ServletServerHttpRequest) request).getServletRequest().getParameter("token");
            int classId = Integer.parseInt(((ServletServerHttpRequest) request).getServletRequest().getParameter("classId"));
            if(token != null){
                attributes.put("token", token);
                attributes.put("classId", classId);
            }else{
                return false;
            }
        }
        return true;
    }
    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
    }
}