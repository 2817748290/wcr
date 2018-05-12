package com.sxito.wcr.bean;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.sxito.wcr.config.CustomJsonDateDeserializer;

import java.util.Date;

public class CourseArrange {
    private Integer id;
    private Integer classId;
    private Integer courseId;
    private String topic;
    private Date courseOntime;
    private Date courseDowntime;
    private Integer state;
    /**
     * 0 默认状态，未发布
     * 1 已发布
     * 2 未上课
     * 3 已上课
     * 4 未关闭（课堂交互）
     */
    private String groups;
    private int arrangeDataId;

    private ArrangeData arrangeData;
    private CourseInfo courseInfo;
    private CourseClass courseClass;

    public CourseArrange(Integer id, Integer classId, Integer courseId, String topic, Date courseOntime, Date courseDowntime, Integer state, String groups, int arrangeDataId, ArrangeData arrangeData, CourseInfo courseInfo, CourseClass courseClass) {
        this.id = id;
        this.classId = classId;
        this.courseId = courseId;
        this.topic = topic;
        this.courseOntime = courseOntime;
        this.courseDowntime = courseDowntime;
        this.state = state;
        this.groups = groups;
        this.arrangeDataId = arrangeDataId;
        this.arrangeData = arrangeData;
        this.courseInfo = courseInfo;
        this.courseClass = courseClass;
    }

    public CourseArrange() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic == null ? null : topic.trim();
    }

    public Date getCourseOntime() {
        return courseOntime;
    }

    @JsonDeserialize(using = CustomJsonDateDeserializer.class)
    public void setCourseOntime(Date courseOntime) {
       this.courseOntime = courseOntime;
    }

    public Date getCourseDowntime() {
        return courseDowntime;
    }

    @JsonDeserialize(using = CustomJsonDateDeserializer.class)
    public void setCourseDowntime(Date courseDowntime) {
        this.courseDowntime = courseDowntime;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public CourseInfo getCourseInfo() {
        return courseInfo;
    }

    public void setCourseInfo(CourseInfo courseInfo) {
        this.courseInfo = courseInfo;
    }

    public CourseClass getCourseClass() {
        return courseClass;
    }

    public void setCourseClass(CourseClass courseClass) {
        this.courseClass = courseClass;
    }

    public String getGroups() {
        return groups;
    }

    public void setGroups(String groups) {
        this.groups = groups;
    }

    public int getArrangeDataId() {
        return arrangeDataId;
    }

    public void setArrangeDataId(int arrangeDataId) {
        this.arrangeDataId = arrangeDataId;
    }

    public ArrangeData getArrangeData() {
        return arrangeData;
    }

    public void setArrangeData(ArrangeData arrangeData) {
        this.arrangeData = arrangeData;
    }
}