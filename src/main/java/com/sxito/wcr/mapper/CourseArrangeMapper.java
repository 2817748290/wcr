package com.sxito.wcr.mapper;

import com.sxito.wcr.bean.CourseArrange;
import com.sxito.wcr.bean.TableMessage;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CourseArrangeMapper {

    List<CourseArrange> getCourseArrangeByCourseId(int courseId) throws Exception;

    Integer updateCourseArrangeById(CourseArrange courseArrange) throws  Exception;

    Integer deleteCourseArrangeById(int id) throws Exception;

    CourseArrange getCourseArrangeById(int id) throws Exception;

    Integer updateStateCourseArrange(int id)throws Exception;

    List<CourseArrange> searchCourseArrange(TableMessage tableMessage) throws Exception;

    List<CourseArrange> findPage(TableMessage tableMessage) throws Exception;

    Integer count(TableMessage tableMessage) throws Exception;

    Integer searchCount(TableMessage tableMessage) throws Exception;

    Integer addCourseArrange(CourseArrange courseArrange) throws Exception;

}