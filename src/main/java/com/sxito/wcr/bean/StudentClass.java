package com.sxito.wcr.bean;

import java.util.Date;
import java.util.List;

public class StudentClass {
    private int classId;
    private int studentId;
    private List<UserInfo> studentList;
    private Date gmtCreate;
    private Date gmtModified;

    @Override
    public String toString() {
        return "StudentClass{" +
                "classId=" + classId +
                ", studentId=" + studentId +
                ", studentList=" + studentList +
                ", gmtCreate=" + gmtCreate +
                ", gmtModified=" + gmtModified +
                '}';
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public List<UserInfo> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<UserInfo> studentList) {
        this.studentList = studentList;
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
