package com.sxito.wcr.service.impl;

import com.sxito.wcr.bean.*;
import com.sxito.wcr.mapper.CourseClassMapper;
import com.sxito.wcr.mapper.CourseInfoMapper;
import com.sxito.wcr.mapper.StudentClassMapper;
import com.sxito.wcr.mapper.UserInfoMapper;
import com.sxito.wcr.service.CourseClassService;
import com.sxito.wcr.service.CourseInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 62450 on 2017/11/7.
 */
@Component
public class CourseClassServiceImpl implements CourseClassService{

    @Autowired
    private CourseInfoService courseInfoService;

    @Autowired
    private CourseClassMapper courseClassMapper;

    @Autowired
    private CourseInfoMapper courseInfoMapper;

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private StudentClassMapper studentClassMapper;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public CourseClass checkPermission(int classId) throws RuntimeException{

        CourseClass courseClass= null;
        try {
            courseClass = courseClassMapper.getCourseClassByClassId(classId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("未找到课程班级");
        }
        if(courseClass != null){
            CourseInfo courseInfo = courseInfoService.checkPermission(courseClass.getCouseId());
            courseClass.setCourseInfo(courseInfo);
            return courseClass;
        }else{
            throw new RuntimeException("未找到课程班级");
        }


    }

    @Override
    @Transactional
    public TableMessage getAllCourseClass(TableMessage tableMessage) {
        List<UserInfo> studentList=new ArrayList<UserInfo>();
        try {
            //table没有进行搜索则search为空,search不为空并且其值不为""时才进行搜索。(""为字符串空)
            if(tableMessage.getSearch()!=null && !tableMessage.getSearch().equals("")){
                //sql语句防注入，search值是table插件搜索时自动返回，所以在这搜索值前后加上%，让它能模糊查询。
                tableMessage.setSearch("%"+tableMessage.getSearch()+"%");
            }
            studentList=userInfoMapper.findPage(tableMessage);
            CourseClass courseClass=new CourseClass();
            //循环遍历拿出的学生数组，手动查询其行政班，放入bean中返回。
            for (UserInfo student:studentList) {
                courseClass=courseClassMapper.getDefaultClassByClassId(student.getDefaultClassId());
                student.setDefaultClass(courseClass);
            }
            tableMessage.setRows(studentList);
            tableMessage.setTotal(userInfoMapper.count(tableMessage));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tableMessage;
    }

    @Override
    public List<CourseClass> getCourseClassByCourseId(int courseId) {
        List<CourseClass> courseClass=new ArrayList<CourseClass>();
        try {
            courseClass=courseClassMapper.getCourseClassByCourseId(courseId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return courseClass;
    }

    @Override
    public Message getCourseClassByClassId(int classId) {
        CourseClass courseClass;
        try {
            courseClass=courseClassMapper.getCourseClassByClassId(classId);
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(0,"FAIR",null);
        }
        return new Message(1,"SUCCESS",courseClass);
    }

    @Override
    @Transactional
    public Message editClassName(CourseClass courseClass) {
        CourseClass courseClass1;
        try {
            courseClassMapper.editClassName(courseClass);
            courseClass1=courseClassMapper.getCourseClassByClassId(courseClass.getClassId());
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(0,"FAIR",null);
        }
        return new Message(1,"SUCCESS",courseClass1);
    }

    @Override
    //开启事务
    @Transactional
    //异常抛出，交给controller层处理
    public CourseClass changeAllowedAdd(CourseClass courseClass) throws RuntimeException{
        //权限检查，判断该用户是否有权限操作此数据
        //返回的是id返回这条数据，可接收可不接收
        //如果没有权限，会直接抛出异常，结束接下来的操作，而controller就能从异常中获取信息
        CourseClass courseClassTemp = checkPermission(courseClass.getClassId());

        courseClassTemp.setAllowedAdd(courseClass.getAllowedAdd());
        try {
            //执行数据库插入操作,抓取异常，失败抛出异常信息
            courseClassMapper.updateByPrimaryKeySelective(courseClassTemp);
            logger.warn("警告，课程班级:{},id:{}，改变允许通过二维码加入班级的状态",courseClassTemp.getClassName(),courseClassTemp.getClassId());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("更新失败");
        }
        return courseClassTemp;
    }

    @Override
    public Message addCourseClass(CourseClass courseClass) {
        try {
            courseClassMapper.addCourseClass(courseClass);
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(0,"FAIR",null);
        }
        return new Message(1,"SUCCESS",null);
    }

    @Override
    @Transactional
    public List<CourseClass> getCourseClassByCourseIdForCourseArrange(int courseId) {

        List<CourseClass> courseClass=new ArrayList<CourseClass>();

        try {
            courseClass = courseClassMapper.getCourseClassByCourseId(courseId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return courseClass;
    }

    @Override
    @Transactional
    public List<CourseClass> getStudentCourseByStudentId(int studentId) {
        List<CourseClass> courseClassList = new ArrayList<>();
        try {
            courseClassList = courseClassMapper.getStudentCourseByStudentId(studentId);
            for (CourseClass courseClass: courseClassList) {
                int courseId = courseClass.getCouseId();
                CourseInfo courseInfo = courseInfoMapper.getCourseByCourseId(courseId);
                courseClass.setCourseInfo(courseInfo);
                int teacherId = courseInfo.getTeacherId();
                UserInfo teacher = userInfoMapper.getUserInfoById(teacherId);
                courseInfo.setTeacher(teacher);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("更新失败");
        }

        return courseClassList;
    }

    @Override
    public void deleteClassStudent(StudentClass studentClass) throws RuntimeException {
        checkPermission(studentClass.getClassId());
        try {
            studentClassMapper.deleteClassStudent(studentClass);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("删除失败");
        }
    }
}
