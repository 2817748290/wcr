package com.sxito.wcr.bean;

import java.util.Date;
import java.util.List;

public class  CourseInfo {
    private int id;
    private String name;
    private String description;
    private String coverImgUrl;
    private boolean isBacked;
    private int type;
    private int teacherId;

    private Date gmtCreate;
    private Date gmtModified;

    private UserInfo teacher;
    private List<UserInfo> studentList;
    private List<CourseNode> courseNodeList;
    private List<CourseClass> courseClassList;
    private List<CourseIssues> courseIssuesList;
    private List<CourseArrange> courseArrangeList;
    private List<CourseContent> courseContentList;

    @Override
    public String toString() {
        return "CourseInfo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", coverImgUrl='" + coverImgUrl + '\'' +
                ", isBacked=" + isBacked +
                ", type=" + type +
                ", teacherId=" + teacherId +
                ", gmtCreate=" + gmtCreate +
                ", gmtModified=" + gmtModified +
                ", teacher=" + teacher +
                ", studentList=" + studentList +
                ", courseNodeList=" + courseNodeList +
                ", courseClassList=" + courseClassList +
                ", courseIssuesList=" + courseIssuesList +
                ", courseArrangeList=" + courseArrangeList +
                ", courseContentList=" + courseContentList +
                '}';
    }

    public List<CourseIssues> getCourseIssuesList() {
        return courseIssuesList;
    }

    public void setCourseIssuesList(List<CourseIssues> courseIssuesList) {
        this.courseIssuesList = courseIssuesList;
    }

    public List<CourseArrange> getCourseArrangeList() {
        return courseArrangeList;
    }

    public void setCourseArrangeList(List<CourseArrange> courseArrangeList) {
        this.courseArrangeList = courseArrangeList;
    }

    public List<CourseContent> getCourseContentList() {
        return courseContentList;
    }

    public void setCourseContentList(List<CourseContent> courseContentList) {
        this.courseContentList = courseContentList;
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

    public List<CourseClass> getCourseClassList() {
        return courseClassList;
    }

    public void setCourseClassList(List<CourseClass> courseClassList) {
        this.courseClassList = courseClassList;
    }

    public List<CourseNode> getCourseNodeList() {
        return courseNodeList;
    }

    public void setCourseNodeList(List<CourseNode> courseNodeList) {
        this.courseNodeList = courseNodeList;
    }

    public List<UserInfo> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<UserInfo> studentList) {
        this.studentList = studentList;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCoverImgUrl() {
        return coverImgUrl;
    }

    public void setCoverImgUrl(String coverImgUrl) {
        this.coverImgUrl = coverImgUrl;
    }

    public boolean isBacked() {
        return isBacked;
    }

    public void setBacked(boolean backed) {
        isBacked = backed;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public UserInfo getTeacher() {
        return teacher;
    }

    public void setTeacher(UserInfo teacher) {
        this.teacher = teacher;
    }


}
