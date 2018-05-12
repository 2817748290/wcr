package com.sxito.wcr.config.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sxito.wcr.bean.SocketMessage;
import com.sxito.wcr.bean.UserInfo;
import com.sxito.wcr.config.security.JwtTokenUtil;
import com.sxito.wcr.utils.DownloadUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import javax.websocket.CloseReason;
import java.io.IOException;

public class StudentWebSocket extends TextWebSocketHandler {
    
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    private ObjectMapper objectMapper = new ObjectMapper();

    private static Logger logger = LoggerFactory.getLogger(StudentWebSocket.class);

    /**
     * 连接成功时候，会触发UI上onopen方法
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        logger.info("connect to the websocket success......");
        try {
            UserInfo userInfo = jwtTokenUtil.verifyToken((String) session.getAttributes().get("token"));
            if (userInfo != null) {
                if (userInfo.getRoles().get(0).equals("ROLE_STUDENT")) {
                    int classId = (Integer) session.getAttributes().get("classId");
                    if (classId != 0) {
                        if (TeacherWebSocket.webSocketSet.containsKey(classId)) {
                            //是否已存在
                            if (TeacherWebSocket.webSocketSet.get(classId).getStudentList().containsKey(userInfo.getId())){
                                sendMessToTeacher(session,new SocketMessage(SocketMessage.RECONNECTION,"重连成功"));
                                sendMessage(session,new SocketMessage(SocketMessage.RECONNECTION,"重连成功"));
                                TeacherWebSocket.webSocketSet.get(classId).getStudentList().replace(userInfo.getId(),session);
                            }else{
                                String code = (String) session.getAttributes().get("code");
                                if(TeacherWebSocket.webSocketSet.get(classId).isAllowedAdd()){
                                    if(code.equals(TeacherWebSocket.webSocketSet.get(classId).getCode())){
                                        sendMessToTeacher(session,new SocketMessage(SocketMessage.LOGIN,null));
                                        sendMessage(session,new SocketMessage(SocketMessage.LOGIN,"加入成功"));
                                        TeacherWebSocket.webSocketSet.get(classId).getStudentList().put(userInfo.getId(),session);
                                    }else{
                                        sendMessage(session,new SocketMessage(SocketMessage.ERROR,"签到码验证失败！"));
                                        session.close(new CloseStatus(CloseReason.CloseCodes.CANNOT_ACCEPT.getCode(),"签到码验证失败！"));
                                    }
                                }else{
                                    sendMessage(session,new SocketMessage(SocketMessage.ERROR,"教师已关闭签到！"));
                                    session.close(new CloseStatus(CloseReason.CloseCodes.CANNOT_ACCEPT.getCode(),"教师已关闭签到！"));
                                }

                            }
                        }else {
                            sendMessage(session,new SocketMessage(SocketMessage.ERROR,"课堂交互未建立！"));
                            session.close(new CloseStatus(CloseReason.CloseCodes.CANNOT_ACCEPT.getCode(),"课堂交互未建立！"));
                        }
                    }
                } else {
                    sendMessage(session,new SocketMessage(SocketMessage.ERROR,"您没有权限加入课堂交互！"));
                    session.close(new CloseStatus(CloseReason.CloseCodes.CANNOT_ACCEPT.getCode(),"您没有权限加入课堂交互！"));
                }
            }else{
                sendMessage(session,new SocketMessage(SocketMessage.ERROR,"token验证未通过！"));
                session.close(new CloseStatus(CloseReason.CloseCodes.CANNOT_ACCEPT.getCode(),"token验证未通过！"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            try {
                session.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
    
    /**
     * 在UI在用js调用websocket.send()时候，会调用该方法
     */
    @Override
    protected void handleTextMessage(WebSocketSession session,TextMessage message)  {
        try {
            super.handleTextMessage(session, message);
            int classId = (Integer) session.getAttributes().get("classId");
            SocketMessage socketMessage = objectMapper.readValue(message.getPayload(), SocketMessage.class);
            //直接丢给teacher web端处理
            sendMessToTeacher(session,socketMessage);
        }catch (IOException e){
            e.printStackTrace();
            sendMessage(session,new SocketMessage(SocketMessage.ERROR,"请求数据有误！"));
        } catch (Exception e) {
            sendMessage(session,new SocketMessage(SocketMessage.ERROR,"未知错误！"));
        }



    }

    /**
     * 给老师发送信息
     * @param session
     * @param message
     */
    public void sendMessToTeacher(WebSocketSession session,SocketMessage message){
        logger.info("student:{} {}",message.getStudentId(),message.toString());
        UserInfo userInfo = jwtTokenUtil.verifyToken((String) session.getAttributes().get("token"));
        int classId = (int)session.getAttributes().get("classId");
        sendMessage(TeacherWebSocket.webSocketSet.get(classId).getTeacherSession(),new SocketMessage(message.getType(),userInfo.getId(),classId,message.getResult()));
    }

    /**
     * 学生端异常断开连接
     * @param session
     * @param exception
     * @throws Exception
     */
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        if (session.isOpen()) {
            session.close();
        }
        try {
            logger.info("websocket connection error closed......");
            sendMessToTeacher(session,new SocketMessage(SocketMessage.LOGOUT,null));
        }catch (Exception e){

        }
    }
    /**
     * 学生端断开连接
     * 不是主动发关闭信息，算异常断开
     * @param session
     * @throws Exception
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        try {
            logger.info("websocket connection closed......");
            sendMessToTeacher(session,new SocketMessage(SocketMessage.LOGOUT,null));
        }catch (Exception e){

        }

    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    public void sendMessage(WebSocketSession session, SocketMessage message){

        try {
            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
