package com.sxito.wcr.controller;

import com.sxito.wcr.bean.CourseVote;
import com.sxito.wcr.bean.Message;
import com.sxito.wcr.bean.TableMessage;
import com.sxito.wcr.service.CourseVoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CourseVoteController {
    @Autowired
    private CourseVoteService courseVoteService;

    @RequestMapping(value = "/api/addCourseVote",method = RequestMethod.POST)
    public Message addCourseVote (@RequestBody CourseVote courseVote){
        try {
            courseVoteService.addCourseVote(courseVote);
            return new Message(Message.SUCCESS,"提交成功",null);
        }catch (RuntimeException e){
            e.printStackTrace();
            return new Message(Message.ERROR,e.getMessage(),new RuntimeException(e));
        }
    }

    @RequestMapping(value = "/api/getCourseVote",method = RequestMethod.POST)
    public Message getCourseVote(@RequestBody TableMessage tableMessage) throws Exception {
        try {
            courseVoteService.getCourseVote(tableMessage).result();
            return new Message(Message.SUCCESS,"成功",courseVoteService.getCourseVote(tableMessage).result());
        }catch (Exception e){
            e.printStackTrace();
            return new Message(Message.ERROR,"获取失败",new RuntimeException(e));
        }
    }

    @RequestMapping(value = "/api/deleteVote",method = RequestMethod.POST)
    public Message deleteCourseVote(@RequestBody CourseVote courseVote) {
        try {
            int id = courseVote.getId();
            int nodeId=courseVote.getNodeId();
            courseVoteService.deleteCourseVote(id,nodeId);
            return new Message(1, "成功", "success");
        }catch (RuntimeException e){
            e.printStackTrace();
            return new Message(Message.ERROR,e.getMessage(),new RuntimeException(e));
        }

    }
    @RequestMapping(value = "/api/updateVote",method = RequestMethod.POST)
    public Message updateCourseVote(@RequestBody CourseVote courseVote){
        try {
            courseVoteService.updateCourseVote(courseVote);
            return new Message(Message.SUCCESS,"修改成功",null);
        } catch (RuntimeException e){
            e.printStackTrace();
            return new Message(Message.ERROR,e.getMessage(),new RuntimeException(e));
        }

    }
}
