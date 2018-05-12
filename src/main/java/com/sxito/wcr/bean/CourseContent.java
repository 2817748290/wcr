package com.sxito.wcr.bean;

import java.util.Date;

public class CourseContent {
    private int id;
    private int nodeId;
    private String fileName;
    private String fileType;
    private String filePath;
    private Date gmtCreate;
    private Date gmtModified;
    private int tags;

    @Override
    public String toString() {
        return "CourseContent{" +
                "id=" + id +
                ", nodeId=" + nodeId +
                ", fileName='" + fileName + '\'' +
                ", fileType='" + fileType + '\'' +
                ", filePath='" + filePath + '\'' +
                ", gmtCreate=" + gmtCreate +
                ", gmtModified=" + gmtModified +
                ", tags=" + tags +
                ", courseNode=" + courseNode +
                '}';
    }

    public int getTags() {
        return tags;
    }

    public void setTags(int tags) {
        this.tags = tags;
    }

    private CourseNode courseNode;


    public CourseNode getCourseNode() {
        return courseNode;
    }

    public void setCourseNode(CourseNode courseNode) {
        this.courseNode = courseNode;
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

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
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
