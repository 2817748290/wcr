package com.sxito.wcr.mapper;

import com.sxito.wcr.bean.CourseNode;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CourseNodeMapper {
    CourseNode getCourseNodeByNodeId(int nodeId) throws RuntimeException;

    void addCourseNodeInfo(CourseNode courseNode) throws Exception;

    List<CourseNode> getCourseNodeByCourseId(int courseId) throws RuntimeException;

    void updateCourseNodeInfo(CourseNode courseNode) throws Exception;

    void delCourseNodeInfo(int[] nodeId) throws RuntimeException;

    void updateCourseNodeInfoTitle(CourseNode courseNode) throws Exception;
}
