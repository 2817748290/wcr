package com.sxito.wcr.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sxito.wcr.bean.ClassGroup;
import com.sxito.wcr.bean.CourseInfo;
import com.sxito.wcr.bean.UserInfo;
import com.sxito.wcr.mapper.ClassGroupMapper;
import com.sxito.wcr.mapper.UserInfoMapper;
import com.sxito.wcr.service.ClassGroupService;
import com.sxito.wcr.service.CourseClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by 62450 on 2017/12/2.
 */
@Component
public class ClassGroupServiceImpl implements ClassGroupService{
    @Autowired
    private CourseClassService courseClassService;
    @Autowired
    private ClassGroupMapper classGroupMapper;
    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    @Transactional
    public boolean addClassGroup(ClassGroup classGroup) throws RuntimeException{
        courseClassService.checkPermission(classGroup.getClassId());
        try {
            classGroupMapper.addClassGroup(classGroup);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("数据库操作失败！");
        }
        return false;
    }

    @Override
    @Transactional
    public ClassGroup checkPermission(int groupId) throws RuntimeException {
        ClassGroup classGroup=null;
        try {
            classGroup=classGroupMapper.getClassGroupByGroupId(groupId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("未找到该班级分组！");
        }
        if(classGroup!=null){
            courseClassService.checkPermission(classGroup.getClassId());
            return classGroup;
        }else{
            throw new RuntimeException("未找到该班级分组！");
        }
    }

    @Override
    @Transactional
    public List<ClassGroup> getClassGroupByClassId(int classId) throws RuntimeException {
        try {
            List<ClassGroup> classGroupList=classGroupMapper.getClassGroupByClassId(classId);
            return classGroupList;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("数据库操作失败！");
        }
    }

    @Override
    public ClassGroup getClassGroupByGroupId(int groupId) throws RuntimeException {
        try {
            ClassGroup classGroup=classGroupMapper.getClassGroupByGroupId(groupId);
            return classGroup;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("数据库操作失败！");
        }
    }

    @Override
    @Transactional
    public List<Map<String, Object>> getStudentListByGroupId(int id) throws RuntimeException {
        ClassGroup classGroup=getClassGroupByGroupId(id);
        ObjectMapper objectMapper=new ObjectMapper();
        List<Map<String,Object>> studentList= null;
        try {
            studentList = objectMapper.readValue(classGroup.getGroups(),ArrayList.class);
            for (Map<String,Object> map:studentList) {
                List<Map<String,Object>> userInfoList= (List<Map<String,Object>>) map.get("group");
                for (Map<String,Object> userMap:userInfoList) {
                    UserInfo userInfo=userInfoMapper.getUserInfoById((Integer) userMap.get("id"));
                    userMap.put("username",userInfo.getUsername());
                    userMap.put("nickname",userInfo.getNickname());
                    userMap.put("defaultClassId",userInfo.getDefaultClassId());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("数据库操作失败！");
        }
        return studentList;
    }

    @Override
    @Transactional
    public List<Map<String, Object>> getStudentListByGroups(String groups) throws RuntimeException {
        ObjectMapper objectMapper=new ObjectMapper();
        List<Map<String,Object>> studentList= null;
        try {
            studentList = objectMapper.readValue(groups,ArrayList.class);
            for (Map<String,Object> map:studentList) {
                List<Map<String,Object>> userInfoList= (List<Map<String,Object>>) map.get("group");
                for (Map<String,Object> userMap:userInfoList) {
                    UserInfo userInfo=userInfoMapper.getUserInfoById((Integer) userMap.get("id"));
                    userMap.put("username",userInfo.getUsername());
                    userMap.put("nickname",userInfo.getNickname());
                    userMap.put("headImgUrl",userInfo.getHeadImgUrl());
                    userMap.put("defaultClassId",userInfo.getDefaultClassId());

                    userMap.put("online",0);//用于前端课堂交互
                    userMap.put("heart",false);//用于前端课堂交互
                    userMap.put("responder",0);//用于前端课堂交互

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("数据库操作失败！");
        }
        return studentList;
    }
}
