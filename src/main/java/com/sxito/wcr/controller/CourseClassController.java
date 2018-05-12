package com.sxito.wcr.controller;

import com.sxito.wcr.bean.CourseClass;
import com.sxito.wcr.bean.Message;
import com.sxito.wcr.bean.StudentClass;
import com.sxito.wcr.bean.TableMessage;
import com.sxito.wcr.service.CourseClassService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * Created by 62450 on 2017/11/7.
 */
@Api(description = "教师对课程班级的管理")
@Controller
public class CourseClassController {

    @Autowired
    private CourseClassService courseClassService;

    private Logger logger = LoggerFactory.getLogger(CourseClassController.class);

    @RequestMapping(value = "/api/getAllCourseClass",method = RequestMethod.POST)
    @ResponseBody
    public Map getAllCourseClass(@RequestBody TableMessage tableMessage){
        return courseClassService.getAllCourseClass(tableMessage).result();
    }

    @RequestMapping(value = "/api/getCourseClassByCourseId",method = RequestMethod.POST)
    @ResponseBody
    public Message getCourseClassByCourseId(@RequestBody Map map){
        int courseId=Integer.parseInt((String)map.get("courseId"));
        return new Message(1,"SUCCESS",courseClassService.getCourseClassByCourseId(courseId));
    }

    @RequestMapping(value = "/api/getCourseClassByClassId",method = RequestMethod.POST)
    @ResponseBody
    public Message getCourseClassByClassId(@RequestBody Map map){
        int classId=Integer.parseInt((String)map.get("classId"));
       logger.warn("classId:-----------------------{}",classId);
        return courseClassService.getCourseClassByClassId(classId);
    }

    @RequestMapping(value = "/api/editClassName",method = RequestMethod.POST)
    @ResponseBody
    public Message editClassName(@RequestBody CourseClass courseClass){
        return courseClassService.editClassName(courseClass);
    }

    @RequestMapping(value = "/api/addCourseClass",method = RequestMethod.POST)
    @ResponseBody
    public Message addCourseClass(@RequestBody CourseClass courseClass){
        return courseClassService.addCourseClass(courseClass);
    }

    @ApiOperation("改变是否允许通过班级二维码加入")
    @RequestMapping(value = "/api/changeAllowedAdd",method = RequestMethod.POST)
    @ResponseBody
    public Message changeAllowedAdd(
            @ApiParam(name="传入课程班级id，和开关值",value ="{\"classId\":1,\"allowedAdd\":0(关闭)1(打开)}",required = true)
            @RequestBody CourseClass courseClass){
        try {
            CourseClass courseClassTemp = courseClassService.changeAllowedAdd(courseClass);
            return new Message(1,"Success",courseClassTemp);
        }catch (RuntimeException e){
            return new Message(0,e.getMessage(),null);
        }
    }

    @ApiOperation(value = "删除该课程班级下的学生", notes = "查删除该课程班级下的学生注意事项", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @RequestMapping(value = "/api/deleteClassStudent", method = RequestMethod.POST)
    @ResponseBody
    public Message deleteClassStudent(@RequestBody StudentClass studentClass){
        try {
            courseClassService.deleteClassStudent(studentClass);
            return new Message(1,"操作成功",null);
        }catch (RuntimeException e){
            return new Message(0,e.getMessage(),null);
        }
    }
}
