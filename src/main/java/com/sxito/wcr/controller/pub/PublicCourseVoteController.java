package com.sxito.wcr.controller.pub;

import com.sxito.wcr.bean.CourseVote;
import com.sxito.wcr.bean.Message;
import com.sxito.wcr.service.CourseVoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class PublicCourseVoteController {
    @Autowired
    private CourseVoteService courseVoteService;

    @RequestMapping(value = "/public/getCourseVoteByIdList",method = RequestMethod.POST)
    public Message getCourseVoteByIdList(@RequestBody List<Integer> list){
        return new Message(Message.SUCCESS, "获取成功", courseVoteService.getCourseVoteByIdList(list));
    }

    @RequestMapping(value = "/public/getCourseVoteByNodeIdList",method = RequestMethod.POST)
    public Message getCourseVoteByNodeIdList(@RequestBody int[] list){
        List<CourseVote> courseVoteList = new ArrayList<>();
        try {
            courseVoteList = courseVoteService.getCourseVodeByNodeIdList(list);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Message(Message.SUCCESS, "获取成功", courseVoteList);

    }

}
