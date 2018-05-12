package com.sxito.wcr.service;

import com.sxito.wcr.bean.CourseInfo;
import com.sxito.wcr.bean.Message;
import com.sxito.wcr.bean.StudentClass;
import com.sxito.wcr.bean.UserInfo;

import java.util.List;
import java.util.Map;


public interface StudentInfoService {
    List<UserInfo> getClassStudent(int classId);

    UserInfo getByUsername(String username);

    List<UserInfo> getTeacher();

    boolean addStudentIntoClass(Map map);

    void addClassByScanQcode(String code);

}
