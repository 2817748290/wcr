package com.sxito.wcr.bean;

import java.util.Date;
import java.util.List;

public class CourseIssues {
    private int id;
    private int nodeId;
    private String content;
    private Date gmtCreate;
    private Date gmtModified;

    private CourseNode courseNode;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNodeId() {
        return nodeId;
    }

    public void setNodeId(int nodeId) {
        this.nodeId = nodeId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public CourseNode getCourseNode() {
        return courseNode;
    }

    public void setCourseNode(CourseNode courseNode) {
        this.courseNode = courseNode;
    }

    @Override
    public String toString() {
        return "CourseIssues{" +
                "id=" + id +
                ", nodeId=" + nodeId +
                ", content='" + content + '\'' +
                ", gmtCreate=" + gmtCreate +
                ", gmtModified=" + gmtModified +
                '}';
    }
}
