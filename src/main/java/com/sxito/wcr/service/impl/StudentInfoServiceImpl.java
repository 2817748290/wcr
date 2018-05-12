package com.sxito.wcr.service.impl;

import com.sxito.wcr.bean.*;
import com.sxito.wcr.mapper.CourseClassMapper;
import com.sxito.wcr.mapper.StudentClassMapper;
import com.sxito.wcr.mapper.UserInfoMapper;
import com.sxito.wcr.service.CourseClassService;
import com.sxito.wcr.service.LoginService;
import com.sxito.wcr.service.StudentInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class StudentInfoServiceImpl implements StudentInfoService{
    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private CourseClassService courseClassService;
    @Autowired
    private StudentClassMapper studentClassMapper;
    @Autowired
    private CourseClassMapper courseClassMapper;
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Override
    public List<UserInfo> getClassStudent(int classId){
        List<UserInfo> userInfos = new ArrayList<UserInfo>();
        try {
            userInfos = userInfoMapper.getClassStudent(classId);
            System.out.println(userInfos.size());
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return userInfos;
    }

    @Override
    public UserInfo getByUsername(String username) {
        UserInfo userInfo = userInfoMapper.getByUsername(username);
        return userInfo;
    }

    @Override
    @Transactional
    public boolean addStudentIntoClass(Map map) throws RuntimeException{
        UserInfo userInfo = getByUsername((String)map.get("username"));
        if(userInfo == null){
            throw new RuntimeException("没有查到这个学生！");
        }else{
            StudentClass studentClass = new StudentClass();
            studentClass.setStudentId(userInfo.getId());
            studentClass.setClassId(Integer.parseInt((String)map.get("classId")));
            courseClassService.checkPermission(studentClass.getClassId());
            int i = 0;
            try {
                i = studentClassMapper.selectByClassIdAndStudentId(studentClass);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("参数错误！");
            }
            if(i != 0){
                throw new RuntimeException("插入失败,学生已加入班级！");
            }else {
                try {
                    studentClassMapper.insert(studentClass);
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new RuntimeException("加入失败！");
                }
            }

        }

        return false;
    }

    @Override
    public List<UserInfo> getTeacher() {
        List<UserInfo> userInfos = new ArrayList<UserInfo>();
        try{
            userInfos = userInfoMapper.getTeacher();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return userInfos;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void addClassByScanQcode(String code) throws RuntimeException{
        UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getDetails();

        StudentClass studentClass = new StudentClass();
        studentClass.setStudentId(userInfo.getId());
        try {
            studentClass.setClassId(Integer.parseInt(code));
        } catch (Exception e) {
            throw new RuntimeException("code错误！");
        }

        CourseClass courseClass = null;
        try {
            courseClass = courseClassMapper.getCourseClassByClassId(studentClass.getClassId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(courseClass == null){
            throw new RuntimeException("未找到班级！");

        }else {
            int i = 0;
            try {
                i = studentClassMapper.selectByClassIdAndStudentId(studentClass);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("参数错误！");
            }
            if(i != 0){
                throw new RuntimeException("插入失败,学生已加入班级！");
            }else {
                try {
                    if(courseClass.getAllowedAdd() == 0){
                        throw new RuntimeException("该班级已关闭二维码加入功能");
                    }else{
                        studentClassMapper.insert(studentClass);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new RuntimeException("加入失败！");
                }
            }
        }
    }
}
