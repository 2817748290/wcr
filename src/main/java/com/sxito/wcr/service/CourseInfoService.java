package com.sxito.wcr.service;

import com.sxito.wcr.bean.CourseInfo;

import java.util.List;

public interface CourseInfoService {

    CourseInfo checkPermission(int courseId) throws RuntimeException;

    public CourseInfo getInitCourseInfo(int teacherId, int courseId);

    public Integer updateCourseInfo(int teacherId, int courseId, String description, String coverImgUrl,String course);

    public List<CourseInfo> createCourseInfo(CourseInfo courseInfo) throws RuntimeException;

    CourseInfo getCourseByCourseId(int courseId);

    List<CourseInfo> getCourseInfoByTeacherId(int teacherId);


}
