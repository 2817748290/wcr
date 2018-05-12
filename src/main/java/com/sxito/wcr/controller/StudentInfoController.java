package com.sxito.wcr.controller;

import com.sxito.wcr.bean.*;
import com.sxito.wcr.service.CourseClassService;
import com.sxito.wcr.service.CourseInfoService;
import com.sxito.wcr.service.StudentInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@Api(value = "StudentInfoController",description = "学生班级操作相关api")
public class StudentInfoController {

    @Autowired
    private StudentInfoService studentInfoService;

    @Autowired
    private CourseInfoService courseInfoService;

    @Autowired
    private CourseClassService courseClassService;

    @ApiOperation(value = "获取对应班级学生信息列表", notes = "获取班级中的学生信息注意事项", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @RequestMapping(value = "/api/getClassStudent",method = RequestMethod.POST)
    @ResponseBody
    public Message getClassStudent(@RequestBody Map map){
        int classId = Integer.parseInt((String)map.get("classId"));
        List<UserInfo> userInfos = studentInfoService.getClassStudent(classId);
        return new Message(1,"SUCCESS",userInfos);
    }

    @ApiOperation(value = "往班级中添加学生", notes = "往班级中添加学生注意事项", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @RequestMapping(value = "/api/addStudentIntoClass",method = RequestMethod.POST)
    @ResponseBody
    public Message addStudentIntoClass(@RequestBody Map map){
        try {
            boolean bs = studentInfoService.addStudentIntoClass(map);
            return new Message(1,"添加成功",null);
        }catch (RuntimeException e){
            return new Message(0,e.getMessage(),null);
        }

    }


    @ApiOperation(value = "根据老师的id获取老师的课程信息", notes = "根据老师的id获取老师的课程信息注意事项", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @RequestMapping(value = "/api/getCourseByTeacherId",method = RequestMethod.POST)
    @ResponseBody
    public Message getCourseByTeacherId(@RequestBody Map map){
        List<CourseInfo> courseInfos = courseInfoService.getCourseInfoByTeacherId(Integer.parseInt((String)map.get("teacher_id")));
        return new Message(1,"SUCCESS",courseInfos);
    }

    @ApiOperation(value = "根据用户的group_id获取老师的课程信息", notes = "根据用户的group_id获取老师的课程信息注意事项", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @RequestMapping(value = "/api/getTeacher",method = RequestMethod.GET)
    @ResponseBody
    public Message getTeacher(){
        List<UserInfo> teacherInfos = studentInfoService.getTeacher();
        return new Message(1,"SUCCESS",teacherInfos);
    }

    @ApiOperation(value = "根据teacher_id获取课程信息", notes = "根据teacher_id获取课程信息注意事项", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @RequestMapping(value = "/api/addStudentGetCourseByTeacherId",method = RequestMethod.POST)
    @ResponseBody
    public Message getCourseByTeacherId(@RequestBody UserInfo userInfo){
        int teacherId = userInfo.getId();
        List<CourseInfo> courseInfos = courseInfoService.getCourseInfoByTeacherId(teacherId);
        return new Message(1,"SUCCESS",courseInfos);
    }

    @ApiOperation(value = "根据teacher_id获取课程信息", notes = "根据teacher_id获取课程信息注意事项", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @RequestMapping(value = "/api/addStudentGetClassByCourseId",method = RequestMethod.POST)
    @ResponseBody
    public Message getClassByCourseId(@RequestBody CourseInfo courseInfo){
        int courseInfoId = courseInfo.getId();
        List<CourseClass> courseClasses = courseClassService.getCourseClassByCourseId(courseInfoId);
        return new Message(1,"SUCCESS",courseClasses);
    }
}
