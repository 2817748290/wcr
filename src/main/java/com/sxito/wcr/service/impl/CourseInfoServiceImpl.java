package com.sxito.wcr.service.impl;

import com.sxito.wcr.bean.CourseInfo;

import com.sxito.wcr.bean.UserInfo;
import com.sxito.wcr.config.exception.PermissionDeniedException;
import com.sxito.wcr.mapper.CourseInfoMapper;
import com.sxito.wcr.mapper.UserInfoMapper;
import com.sxito.wcr.service.CourseInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CourseInfoServiceImpl implements CourseInfoService {

    @Autowired
    private CourseInfoMapper courseInfoMapper;

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    public CourseInfo checkPermission(int courseId) throws RuntimeException {
        UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getDetails();

        CourseInfo courseInfo = null;
        try {
            courseInfo = courseInfoMapper.getCourseByCourseId(courseId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("未找到课程信息");
        }
        if(courseInfo != null){
            if(courseInfo.getTeacherId() == userInfo.getId()){
                userInfo.setPassword(null);
                courseInfo.setTeacher(userInfo);
                return courseInfo;
            }else{
                throw new PermissionDeniedException("权限不足");
            }
        }else {
            throw new RuntimeException("未找到课程信息");
        }
    }

    @Transactional
    @Override
    public CourseInfo getInitCourseInfo(int teacherId, int courseId) {

        Map paramMap=new HashMap();
        paramMap.put("teacherId",teacherId);
        paramMap.put("courseId",courseId);

        CourseInfo courseInfo = null;
        try {
            courseInfo = courseInfoMapper.getInitCourseInfo(paramMap);
            UserInfo teacher = userInfoMapper.getUserInfoById(courseInfo.getTeacherId());
            courseInfo.setTeacher(teacher);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return courseInfo;
    }

    @Override
    public Integer updateCourseInfo(int teacherId, int courseId, String description, String coverImgUrl, String name) {
        Map paramMap=new HashMap();
        paramMap.put("teacherId",teacherId);
        paramMap.put("courseId",courseId);
        paramMap.put("name", name);
        paramMap.put("description",description);
        paramMap.put("coverImgUrl",coverImgUrl);


        int updateNum = 0;
        try {
            updateNum = courseInfoMapper.updateCourseInfo(paramMap);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return updateNum;
    }

    @Override
    public List<CourseInfo> getCourseInfoByTeacherId(int teacherId){
        List<CourseInfo> courseInfoList ;
        try {
            courseInfoList =  courseInfoMapper.getCourseByTeacherId(teacherId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return courseInfoList;
    }


    @Override
    public List<CourseInfo> createCourseInfo(CourseInfo courseInfo) throws RuntimeException{
        int createCourse = 0;
        try {
            createCourse = courseInfoMapper.createCourseInfo(courseInfo);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("新建失败");
        }

        return getCourseInfoByTeacherId(courseInfo.getTeacherId());
    }

    @Override
    public CourseInfo getCourseByCourseId(int courseId) {
        CourseInfo courseInfo = new CourseInfo();
        try {
            courseInfo = courseInfoMapper.getCourseByCourseId(courseId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return courseInfo;
    }


}
