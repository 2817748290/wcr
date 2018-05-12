package com.sxito.wcr.controller.pub;

import com.sxito.wcr.bean.CourseIssues;
import com.sxito.wcr.bean.Message;
import com.sxito.wcr.bean.TableMessage;
import com.sxito.wcr.service.CourseIssuesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class PublicCourseIssuesController {

    @Autowired
    private CourseIssuesService courseIssuesService;

    @RequestMapping(value = "/public/getCourseIssuesByIdList",method = RequestMethod.POST)
    public Message getCourseIssuesByIdList(@RequestBody List<Integer> list){
        return new Message(Message.SUCCESS, "获取成功", courseIssuesService.getCourseIssuesByIdList(list));
    }

    @RequestMapping(value = "/public/getCourseIssuesByNodeIdList",method = RequestMethod.POST)
    public Message getCourseIssuesByNodeIdList(@RequestBody int[] list){
        List<CourseIssues> courseIssuesList = new ArrayList<>();
        try {
            courseIssuesList = courseIssuesService.getCourseIssuesByNodeIdList(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Message(Message.SUCCESS, "获取成功",courseIssuesList);
    }
}
