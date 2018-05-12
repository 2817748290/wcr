package com.sxito.wcr.config.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sxito.wcr.bean.SocketMessage;
import com.sxito.wcr.bean.TeacherWebSocketSession;
import com.sxito.wcr.bean.UserInfo;
import com.sxito.wcr.config.security.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@ServerEndpoint("/TeacherWebSocket")
@Component
public class TeacherWebSocket extends TextWebSocketHandler {
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    private ObjectMapper objectMapper = new ObjectMapper();

    public static ConcurrentHashMap<Integer, TeacherWebSocketSession> webSocketSet = new ConcurrentHashMap<>();

    private static Logger logger = LoggerFactory.getLogger(TeacherWebSocket.class);


    /**
     * 连接成功时候，会触发UI上onopen方法
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        logger.info("教师请求建立教室....");
        try {
            UserInfo userInfo = jwtTokenUtil.verifyToken((String) session.getAttributes().get("token"));
            if (userInfo != null) {
                if (userInfo.getRoles().get(0).equals("ROLE_TEACHER")) {
                    int classId = (Integer) session.getAttributes().get("classId");
                    if (classId != 0 ) {
                        if (webSocketSet.containsKey(classId)) {
                            //掉线了,重连
                            webSocketSet.get(classId).setStatus(1);
                            webSocketSet.get(classId).setTeacherSession(session);
                            Map<Integer,Integer> onlineStudent = new HashMap<>();
                            for (Map.Entry<Integer, WebSocketSession>  entry : webSocketSet.get(classId).getStudentList().entrySet()) {
                                if(entry.getValue().isOpen()){
                                    onlineStudent.put(entry.getKey(),1);
                                }else{
                                    onlineStudent.put(entry.getKey(),2);
                                }
                            }
                            SocketMessage socketMessage = new SocketMessage(SocketMessage.NORMAL,onlineStudent);
                            socketMessage.setAllSend(1);//重连成功
                            sendMessage(session,socketMessage);
                        } else {
                            TeacherWebSocketSession teacherWebSocketSession = new TeacherWebSocketSession();
                            teacherWebSocketSession.setClassId(classId);
                            teacherWebSocketSession.setStatus(1);
                            teacherWebSocketSession.setAllowedAdd(true);
                            teacherWebSocketSession.setTeacherSession(session);
                            webSocketSet.put(classId, teacherWebSocketSession);
                            SocketMessage socketMessage = new SocketMessage(SocketMessage.NORMAL,"创建成功！");
                            socketMessage.setAllSend(0);//创建成功
                            sendMessage(session,socketMessage);
                        }
                    }else{
                        sendMessage(session,new SocketMessage(SocketMessage.ERROR,"您没有选择课程安排！"));
                        session.close(new CloseStatus(CloseReason.CloseCodes.CANNOT_ACCEPT.getCode(),"您没有选择课程安排！"));
                    }
                } else {
                    session.close(new CloseStatus(CloseReason.CloseCodes.CANNOT_ACCEPT.getCode(),"您没有权限创建课堂交互！"));
                }
            }else{
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
            switch (socketMessage.getType()){
                case SocketMessage.HEART_BEAT:
                    sendMessageToAllStudent(session,socketMessage);
                    break;
                case SocketMessage.SIGN_IN_REFRESH:
                    webSocketSet.get(classId).setCode((String) socketMessage.getResult());
                    break;
                case SocketMessage.SIGN_IN_CHANGE:
                    webSocketSet.get(classId).setAllowedAdd((Boolean) socketMessage.getResult());
                    break;
                case SocketMessage.LOGOUT:
                    sendMessageToAllStudent(session,socketMessage);
                    session.close(new CloseStatus(CloseReason.CloseCodes.CANNOT_ACCEPT.getCode(),"成功关闭！"));
                    webSocketSet.remove(classId);
                    break;
                default:
                    if(socketMessage.getAllSend() == 1){
                        sendMessageToAllStudent(session,socketMessage);
                    }else{
                        sendMessageToStudent(session,socketMessage);
                    }
                    break;
            }
        }catch (IOException e){
            e.printStackTrace();
            sendMessage(session,new SocketMessage(SocketMessage.ERROR,"请求数据有误！"));
        } catch (Exception e) {
            sendMessage(session,new SocketMessage(SocketMessage.ERROR,"未知错误！"));
        }



    }

    /**
     * 给某个学生用户发送消息
     *
     * @param message
     */
    public void sendMessageToStudent(WebSocketSession session, SocketMessage message) {
        int classId = (Integer) session.getAttributes().get("classId");
        logger.info("class:{} -one:{}- {}",classId,message.getStudentId(),message.toString());
        boolean temp = false;
        for (Map.Entry<Integer, WebSocketSession>  entry : webSocketSet.get(classId).getStudentList().entrySet()) {
            if(entry.getKey() == message.getStudentId()){
                temp = true;
                if(entry.getValue().isOpen()){
                    sendMessage(entry.getValue(),message);
                }else{
                    sendMessage(session,new SocketMessage(SocketMessage.LOGOUT,entry.getKey(),classId,null));
                }
            }
        }
        if(temp){
            sendMessage(session,new SocketMessage(SocketMessage.LOGOUT,message.getStudentId(),classId,null));
        }
    }

    /**
     * 给所有学生用户发送消息
     *
     * @param message
     */
    public void sendMessageToAllStudent(WebSocketSession session,SocketMessage message) {
        int classId = (Integer) session.getAttributes().get("classId");
        logger.info("class:{} -all- {}",classId,message.toString());
        for (Map.Entry<Integer, WebSocketSession>  entry : webSocketSet.get(classId).getStudentList().entrySet()) {
            if(entry.getValue().isOpen()){
                sendMessage(entry.getValue(),message);
            }else{
                sendMessage(session,new SocketMessage(SocketMessage.LOGOUT,entry.getKey(),classId,null));
            }
        }


    }

    /**
     * 老师端异常断开连接
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
            int classId = Integer.parseInt((String) session.getAttributes().get("classId"));
            webSocketSet.get(classId).setStatus(0);
        }catch (Exception e){

        }
    }
    /**
     * 老师端断开连接
     * 不是主动发关闭信息，算异常断开
     * @param session
     * @throws Exception
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        try {
            logger.info("websocket connection closed......");
            int classId = Integer.parseInt((String) session.getAttributes().get("classId"));
            webSocketSet.get(classId).setStatus(0);
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
