package com.sxito.wcr.service;

import com.sxito.wcr.bean.CourseClass;
import com.sxito.wcr.bean.CourseContent;
import com.sxito.wcr.bean.Message;
import com.sxito.wcr.bean.TableMessage;

import java.util.List;
import java.util.Map;

public interface CourseContentService {
    CourseContent checkPermission(int id) throws RuntimeException;

    Map getFileInfoById(TableMessage tableMessage) throws RuntimeException;

    CourseContent addCourseContent(CourseContent courseContent) throws RuntimeException;

    public Integer deleteFile(int id) throws RuntimeException;
}
