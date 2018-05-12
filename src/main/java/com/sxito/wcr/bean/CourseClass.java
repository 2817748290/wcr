package com.sxito.wcr.bean;

import java.util.Date;
import java.util.List;

public class CourseClass {
    private int classId;
    private String className;
    private int couseId;
    private int type;
    private int allowedAdd;
    private Date gmtCreate;
    private Date gmtModified;

    private List<UserInfo> studentList;
    private CourseInfo courseInfo;


    @Override
    public String toString() {
        return "CourseClass{" +
                "classId=" + classId +
                ", className='" + className + '\'' +
                ", couseId=" + couseId +
                ", gmtCreate=" + gmtCreate +
                ", gmtModified=" + gmtModified +
                ", studentList=" + studentList +
                ", courseInfo=" + courseInfo +
                '}';
    }



    public int getAllowedAdd() {
        return allowedAdd;
    }

    public void setAllowedAdd(int allowedAdd) {
        this.allowedAdd = allowedAdd;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public int getCouseId() {
        return couseId;
    }

    public void setCouseId(int couseId) {
        this.couseId = couseId;
    }

    public List<UserInfo> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<UserInfo> studentList) {
        this.studentList = studentList;
    }

    public CourseInfo getCourseInfo() {
        return courseInfo;
    }

    public void setCourseInfo(CourseInfo courseInfo) {
        this.courseInfo = courseInfo;
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


