package com.sxito.wcr.mapper;

import com.sxito.wcr.bean.CourseArrange;
import com.sxito.wcr.bean.CourseClass;
import com.sxito.wcr.bean.TableMessage;
import com.sxito.wcr.bean.UserInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by 62450 on 2017/11/7.
 */
@Mapper
public interface CourseClassMapper {

    CourseClass getDefaultClassByClassId(int defaultClassId) throws Exception;

    List<CourseClass> getCourseClassByCourseId(int courseId) throws Exception;

    CourseClass getCourseClassByClassId(int classId) throws Exception;

    void editClassName(CourseClass courseClass) throws Exception;

    void addCourseClass(CourseClass courseClass) throws Exception;

    List<CourseClass> getStudentCourseByStudentId(int studentId) throws Exception;

    void updateByPrimaryKeySelective(CourseClass courseClass) throws Exception;

}
