package com.sxito.wcr.service;

import com.sxito.wcr.bean.ArrangeData;
import com.sxito.wcr.bean.CourseArrange;
import com.sxito.wcr.bean.TableMessage;
import io.swagger.models.auth.In;

import java.util.List;

public interface CourseArrangeService {
    CourseArrange checkPermission(int id) throws RuntimeException;

    public TableMessage getInitCourse(TableMessage tableMessage);

    public Integer updateCourseArrangeById(CourseArrange courseArrange);

    public Integer updateStateCourseArrange(int id);

    public Integer deleteCourseArrangeById(int id);

    public CourseArrange getCourseArrangById(int id);

    public void addCourseArrange(CourseArrange courseArrange)throws RuntimeException;

    List<ArrangeData> getArrangeData(int id)throws RuntimeException;
}
