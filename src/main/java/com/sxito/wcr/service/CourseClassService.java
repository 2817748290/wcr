package com.sxito.wcr.service;

import com.sxito.wcr.bean.CourseClass;
import com.sxito.wcr.bean.Message;
import com.sxito.wcr.bean.StudentClass;
import com.sxito.wcr.bean.TableMessage;

import java.util.List;

/**
 * Created by 62450 on 2017/11/7.
 */
public interface CourseClassService {
    CourseClass checkPermission(int classId) throws RuntimeException;

    TableMessage getAllCourseClass(TableMessage tableMessage);

    List<CourseClass> getCourseClassByCourseId(int courseId);

    Message getCourseClassByClassId(int classId);

    Message editClassName(CourseClass courseClass);

    Message addCourseClass(CourseClass courseClass);

    List<CourseClass> getCourseClassByCourseIdForCourseArrange(int courseId);

    CourseClass changeAllowedAdd(CourseClass courseClass) throws RuntimeException;

    List<CourseClass> getStudentCourseByStudentId(int studentId);

    void deleteClassStudent(StudentClass studentClass) throws RuntimeException;
}
