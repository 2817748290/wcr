package com.sxito.wcr.mapper;

import com.sxito.wcr.bean.CourseClass;
import com.sxito.wcr.bean.CourseContent;
import com.sxito.wcr.bean.TableMessage;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CourseContentMapper {

    CourseContent getById(int id);

    List<CourseContent> findPage(TableMessage tableMessage) throws Exception;

    void addCourseContent(CourseContent courseContent) throws Exception;

    Integer deleteFile(int id) throws Exception;

    Integer getFileInfoCount(TableMessage tableMessage);

}
