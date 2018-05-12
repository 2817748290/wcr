package com.sxito.wcr.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sxito.wcr.bean.*;
import com.sxito.wcr.service.*;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.spring.web.json.Json;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api/courseArrange")
public class CourseArrangeController {

    @Autowired
    private CourseArrangeService courseArrangeService;

    @Autowired
    private CourseClassService courseClassService;

    @Autowired
    private StudentInfoService studentInfoService;

    @Autowired
    private CourseVoteService courseVoteService;

    @Autowired
    private CourseIssuesService courseIssuesService;

    @RequestMapping(value = "/getInitCourseArrange", method = RequestMethod.POST)
    @ResponseBody
    public Map getInitCourseA(@RequestBody TableMessage tableMessage) throws Exception{
        return courseArrangeService.getInitCourse(tableMessage).result();
    }

    @RequestMapping(value = "/updateCourseArrange", method = RequestMethod.POST)
    @ResponseBody
    public Message updateCourseA(@RequestBody CourseArrange courseArrange) throws Exception{

        int state = courseArrangeService.updateCourseArrangeById(courseArrange);

        if(state ==1){
            return new Message(Message.SUCCESS,"修改成功！",state);
        }else{
            return new Message(Message.ERROR,"修改异常！",state);
        }
    }

    @ApiOperation(value = "添加课程安排", notes = "添加课程安排注意事项", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @RequestMapping(value = "/addCourseArrange", method = RequestMethod.POST)
    @ResponseBody
    public Message addCourseArrange(@RequestBody CourseArrange courseArrange){
        try {
            courseArrangeService.addCourseArrange(courseArrange);
            return new Message(1,"操作成功",null);
        }catch (RuntimeException e){
            return new Message(0,e.getMessage(),null);
        }
    }

    @RequestMapping(value = "/getCourseClasses", method = RequestMethod.POST)
    @ResponseBody
    public Message getCourseClasses(@RequestBody Map map){
        int courseId=Integer.parseInt((String)map.get("courseId"));
        List<CourseClass> courseClassList = courseClassService.getCourseClassByCourseIdForCourseArrange(courseId);
        return new Message(1,"heihei",courseClassList);
    }

    @ApiOperation(value = "为了分组拿对应班级的所有成员", notes = "注意事项", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @RequestMapping(value = "/getClassStudentsForGroup", method = RequestMethod.POST)
    @ResponseBody
    public Message getClassStudentsForGroup(@RequestBody Map map){
        int classId=Integer.parseInt((String)map.get("classId"));
        List<UserInfo> userInfoList = studentInfoService.getClassStudent(classId);
        return new Message(1,"SUCCESS",userInfoList);
    }

    @ResponseBody
    @RequestMapping(value = "/updateStateCourseArrange", method = RequestMethod.POST)
    public Message updateStateCourseArrange(@RequestBody Map map){
        int id=Integer.valueOf((Integer)map.get("id"));
        int state = courseArrangeService.updateStateCourseArrange(id);
        if(state == 1){
            return new Message(1,"heihei","success");
        }else{
            return new Message(-1,"heihei","fail");
        }

    }

    @ResponseBody
    @RequestMapping(value = "/deleteCourseArrange", method = RequestMethod.POST)
    public Message deleteCourseArrangeById(@RequestBody Map map){
        int id=Integer.valueOf((Integer)map.get("id"));
        int state = courseArrangeService.deleteCourseArrangeById(id);
        if(state == 1){
            return new Message(1,"heihei","success");
        }else{
            return new Message(-1,"heihei","fail");
        }

    }

    @ResponseBody
    @RequestMapping(value = "/getCourseArrangeById", method = RequestMethod.POST)
    public Message getCourseArrangeById(@RequestBody Map map){
        //贼坑
        int arrangeId=Integer.valueOf((Integer) map.get("arrangeId"));
        return new Message(Message.SUCCESS,"查看",
                courseArrangeService.getCourseArrangById(arrangeId));
    }


    @ResponseBody
    @RequestMapping(value = "/getCourseArrangeNodeId", method = RequestMethod.POST)
    public Message getCourseArrangeNodeId(@RequestBody int[] checkNodeId){
        List<CourseVote> courseVotes = new ArrayList<CourseVote>();
        List<CourseIssues> courseIssues = new ArrayList<CourseIssues>();
        ArrangeData arrangeData = new ArrangeData();
        try {
            courseVotes = courseVoteService.getCourseVodeByNodeIdList(checkNodeId);
            courseIssues = courseIssuesService.getCourseIssuesByNodeIdList(checkNodeId);
            ObjectMapper obj = new ObjectMapper();
            String checkNodeIdString = obj.writeValueAsString(checkNodeId);
            String courseVotesString = obj.writeValueAsString(courseVotes);
            String courseIssuesString = obj.writeValueAsString(courseIssues);
            arrangeData.setVote(courseVotesString);
            arrangeData.setIssue(courseIssuesString);
            arrangeData.setNodeNormal(checkNodeIdString);
            if(courseVotes==null){
                return new Message(Message.ERROR,"添加课程安排异常！",null);
            }else{
                return new Message(Message.SUCCESS,"添加课程安排成功！",arrangeData);
            }
        }catch (Exception e){
            e.printStackTrace();
            return new Message(Message.ERROR,"添加课程安排异常！",null);
        }
    }

}
