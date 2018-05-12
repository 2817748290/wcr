package com.sxito.wcr.controller;

import com.sxito.wcr.bean.CourseInfo;
import com.sxito.wcr.bean.Message;
import com.sxito.wcr.bean.UserInfo;
import com.sxito.wcr.service.CourseInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@Api(value = "CourseInfoController",description = "课程信息相关api")
public class CourseInfoController {

    @Autowired
    private CourseInfoService courseInfoService;

    @ApiOperation(value = "获取对应课程信息列表", notes = "获取对应课程信息列表注意事项", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @RequestMapping(value = "/getCourseInfoList",method = RequestMethod.GET)
        public Message getCourseInfoList()throws Exception{

        UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getDetails();
        int teacherId = userInfo.getId();
        List<CourseInfo> courseInfo = courseInfoService.getCourseInfoByTeacherId(teacherId);
        return new Message(Message.SUCCESS,"已获取对应课程信息列表！",courseInfo);
    }

    @ApiOperation(value = "获取对应课程信息", notes = "获取对应课程信息注意事项", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @RequestMapping(value = "/getInitCourseInfo", method = RequestMethod.POST)
    public Message getInitCourseInfo(@RequestBody Map map){

        UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getDetails();
        int teacherId = userInfo.getId();
        int courseId = Integer.parseInt((String) map.get("courseId"));
        CourseInfo courseInfo = courseInfoService.getInitCourseInfo(teacherId, courseId);

        return new Message(Message.SUCCESS,"已获取对应课程信息！",courseInfo);
    }

    @ApiOperation(value = "修改对应课程信息", notes = "修改对应课程信息注意事项", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @RequestMapping(value = "/updateCourseInfo", method = RequestMethod.POST)
    public Message updateCourseInfo(@RequestBody Map map){

        String description = (String)map.get("description");
        String coverImgUrl = (String)map.get("coverImgUrl");
        String name = (String)map.get("name");
        UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getDetails();
        int teacherId = userInfo.getId();
        int courseId = Integer.parseInt((String) map.get("courseId"));
        int state = courseInfoService.updateCourseInfo(teacherId,courseId,description,coverImgUrl,name);

        if(state==1){
            return new Message(Message.SUCCESS,"已更新对应课程信息！",state);
        }else {
            return new Message(Message.ERROR,"更新对应课程信息出现异常！",state);
        }
    }

    @ApiOperation(value = "根据课程编号获取对应课程信息", notes = "根据课程编号获取对应课程信息注意事项", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @RequestMapping(value = "/getCourseByCourseId",method = RequestMethod.POST)
    @ResponseBody
    public Message getCourseByCourseId(@RequestBody Map map){
        int courseId=Integer.parseInt((String)map.get("courseId"));
        CourseInfo courseInfo = courseInfoService.getCourseByCourseId(courseId);
        return new Message(Message.SUCCESS,"已更新对应课程信息！",courseInfo);
    }

    @ApiOperation(value = "添加新课程信息", notes = "添加新课程信息注意事项", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @RequestMapping(value = "/createCourseInfo",method = RequestMethod.POST)
    @ResponseBody
    public Message createCourseInfo(@RequestBody CourseInfo courseInfo){
        try{
            UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getDetails();
            int teacherId = userInfo.getId();
            courseInfo.setTeacherId(teacherId);
            List<CourseInfo> courseInfos = courseInfoService.createCourseInfo(courseInfo);
            return new Message(Message.SUCCESS,"创建成功！",courseInfos);
        }catch (RuntimeException e){
            return new Message(Message.SUCCESS,e.getMessage(),null);
        }
    }
}
