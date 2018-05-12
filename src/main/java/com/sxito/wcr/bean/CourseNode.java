package com.sxito.wcr.bean;

import java.util.Date;

public class CourseNode {
    private int id;
    private String title;
    private int level;
    private int levelOrder;
    private int parentId;
    private int courseId;

    private Date gmtCreate;


    private Date gmtModified;

    private CourseNode childNode;
    private CourseInfo courseInfo;

    @Override
    public String toString() {
        return "CourseNode{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", level=" + level +
                ", level_order="+levelOrder+
                ", parentId=" + parentId +
                ", courseId=" + courseId +
                ", gmtCreate=" + gmtCreate +
                ", gmtModified=" + gmtModified +
                ", childNode=" + childNode +
                ", courseInfo=" + courseInfo +
                '}';
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

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int couseId) {
        this.courseId = couseId;
    }

    public CourseInfo getCourseInfo() {
        return courseInfo;
    }

    public void setCourseInfo(CourseInfo courseInfo) {
        this.courseInfo = courseInfo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public CourseNode getChildNode() {
        return childNode;
    }

    public void setChildNode(CourseNode childNode) {
        this.childNode = childNode;
    }

    public int getLevelOrder() {
        return levelOrder;
    }

    public void setLevelOrder(int level_order) {
        this.levelOrder = level_order;
    }
}
