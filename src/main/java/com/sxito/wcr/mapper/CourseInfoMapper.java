package com.sxito.wcr.mapper;

import com.sxito.wcr.bean.CourseInfo;
import com.sxito.wcr.bean.CourseNode;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface CourseInfoMapper {

    public CourseInfo getInitCourseInfo(Map paramMap) throws Exception;

    public Integer updateCourseInfo(Map paramMap) throws Exception;

    public Integer createCourseInfo(CourseInfo courseInfo) throws Exception;

    CourseInfo getCourseByCourseId(int courseId) throws Exception;

    List<CourseInfo> getCourseByTeacherId(int teacherId) throws Exception;

}


