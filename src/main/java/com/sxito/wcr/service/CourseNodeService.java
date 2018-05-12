package com.sxito.wcr.service;

import com.sxito.wcr.bean.CourseNode;

import java.util.List;

public interface CourseNodeService {
    CourseNode checkPermission(int nodeId) throws RuntimeException;

    boolean addCourseNodeInfo(CourseNode courseNode) throws Exception;

    List<CourseNode> getCourseNodeByCourseId(int courseId) throws RuntimeException;

    boolean updateCourseNodeInfo(CourseNode courseNode) throws Exception;

    boolean delCourseNodeInfo(int[] nodeId) throws RuntimeException;

    boolean updateCourseNodeInfoTitle(CourseNode courseNode) throws Exception;
}
