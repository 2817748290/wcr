package com.sxito.wcr.bean;

import org.springframework.web.socket.WebSocketSession;

import javax.websocket.Session;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 维护课堂交互信息的类
 */
public class TeacherWebSocketSession {
    private int classId;
    private int status;
    private String code;
    private boolean allowedAdd;
    private WebSocketSession teacherSession;
    private ConcurrentHashMap<Integer,WebSocketSession> studentList = new ConcurrentHashMap<>();

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isAllowedAdd() {
        return allowedAdd;
    }

    public void setAllowedAdd(boolean allowedAdd) {
        this.allowedAdd = allowedAdd;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public WebSocketSession getTeacherSession() {
        return teacherSession;
    }

    public void setTeacherSession(WebSocketSession teacherSession) {
        this.teacherSession = teacherSession;
    }

    public ConcurrentHashMap<Integer, WebSocketSession> getStudentList() {
        return studentList;
    }

    public void setStudentList(ConcurrentHashMap<Integer, WebSocketSession> studentList) {
        this.studentList = studentList;
    }
}
