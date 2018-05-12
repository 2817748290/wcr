package com.sxito.wcr.controller;

import com.sxito.wcr.bean.CourseIssues;
import com.sxito.wcr.bean.Message;
import com.sxito.wcr.bean.TableMessage;
import com.sxito.wcr.bean.UserInfo;
import com.sxito.wcr.service.CourseIssuesService;
import com.sxito.wcr.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
public class CourseIssuesController {

    @Autowired
    private CourseIssuesService courseIssuesService;

    @RequestMapping(value = "/api/addCourseIssues",method = RequestMethod.POST)
    public Message addCourseIssues(@RequestBody CourseIssues courseIssues)  {
        try {
            courseIssuesService.addCourseIssues(courseIssues);
            return new Message(Message.SUCCESS,"提交成功",null);
        } catch (RuntimeException e){
            e.printStackTrace();
            return new Message(Message.ERROR,e.getMessage(),new RuntimeException(e));
        }
    }

    @RequestMapping(value = "/api/getCourseIssues",method = RequestMethod.POST)
    public Message getCourseIssues(@RequestBody TableMessage tableMessage) throws Exception {
        try {
            courseIssuesService.getCourseIssues(tableMessage).result();
            return new Message(Message.SUCCESS, "获取成功", courseIssuesService.getCourseIssues(tableMessage).result());

        }catch (Exception e){
            e.printStackTrace();
            return new Message(Message.ERROR,"获取失败",new RuntimeException(e));
        }
    }

    @RequestMapping(value = "/api/deleteIssues",method = RequestMethod.POST)
    public Message deleteCourseIssues(@RequestBody CourseIssues courseIssues)  {
        try {
            int id = courseIssues.getId();
            int nodeId = courseIssues.getNodeId();
            courseIssuesService.deleteCourseIssues(id,nodeId);
            return new Message(1,"成功","success");
        }catch (RuntimeException e){
            e.printStackTrace();
            return new Message(Message.ERROR,e.getMessage(),new RuntimeException(e));
        }

    }
    @RequestMapping(value = "/api/updateIssues",method = RequestMethod.POST)
    public Message updateCourseIssues(@RequestBody CourseIssues courseIssues){
        try {
            courseIssuesService.updateCourseIssues(courseIssues);
            return new Message(Message.SUCCESS,"修改成功",null);
        } catch (RuntimeException e){
            e.printStackTrace();
            return new Message(Message.ERROR,e.getMessage(),new RuntimeException(e));
        }

    }

}

