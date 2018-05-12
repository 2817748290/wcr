package com.sxito.wcr.bean;

import java.util.Date;

public class CourseVote {
    private int id;
    private int nodeId;
    private String title;
    private String content;
    private Date gmtCreate;
    private Date gmtModified;

    public CourseNode getCourseNode() {
        return courseNode;
    }

    public void setCourseNode(CourseNode courseNode) {
        this.courseNode = courseNode;
    }

    private CourseNode courseNode;

    private CourseVote courseVote;

    public CourseVote getCourseVote() {
        return courseVote;
    }

    public void setCourseVote(CourseVote courseVote) {
        this.courseVote = courseVote;
    }

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
}
