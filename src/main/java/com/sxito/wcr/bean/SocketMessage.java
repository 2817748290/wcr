package com.sxito.wcr.bean;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * websocket交互信息类
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SocketMessage {
    public static final int ERROR = -2;//错误
    public static final int NORMAL = -1;//连接成功
    public static final int SIGN_IN_CHANGE = 0;//签到开关
    public static final int SIGN_IN_REFRESH = 1;//签到码刷新
    public static final int SEND_ONE = 2;//单独推送
    public static final int SEND_ALL = 3;//全体推送
    public static final int FEED_BACK = 4;//学生回馈
    public static final int ISSUES = 5;//议题
    public static final int VOTE = 6;//表决
    public static final int RESPONDER = 7;//抢答
    public static final int SELECT_PERSON = 8;//选人
    public static final int DISCUSSION = 9;//讨论
    public static final int INFORMATION = 10;//信息

    public static final int LOGIN = 11;//连线
    public static final int LOGOUT = 12;//离线
    public static final int RECONNECTION = 13;//重连
    public static final int HEART_BEAT = 14;//心跳包检测

    private int type;//发送类型
    private int allSend;//是否发送给所有人
    private int studentId;
    private int classId;
    private Object result;

    public SocketMessage() {

    }
    public SocketMessage(int type, Object result) {
        this.type = type;
        this.result = result;
    }
    public SocketMessage(int type,int isAllSend, Object result) {
        this.type = type;
        this.allSend = isAllSend;
        this.result = result;
    }
    public SocketMessage(int type,int studentId, int classId, Object result) {
        this.type = type;
        this.studentId = studentId;
        this.classId = classId;
        this.result = result;
    }
    public SocketMessage(int type,int isAllSend, int studentId, int classId, Object result) {
        this.type = type;
        this.allSend = isAllSend;
        this.studentId = studentId;
        this.classId = classId;
        this.result = result;
    }

    @Override
    public String toString() {
        return "SocketMessage{" +
                "type=" + type +
                ", isAllSend=" + allSend +
                ", studentId=" + studentId +
                ", classId=" + classId +
                ", result=" + result +
                '}';
    }

    public int getAllSend() {
        return allSend;
    }

    public void setAllSend(int allSend) {
        this.allSend = allSend;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
