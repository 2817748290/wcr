package com.sxito.wcr.controller.app;

import com.sxito.wcr.bean.CourseClass;
import com.sxito.wcr.bean.Message;
import com.sxito.wcr.bean.UserInfo;
import com.sxito.wcr.service.CourseClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/app")
public class StudentCourseListController {

    @Autowired
    private CourseClassService courseClassService;

    @RequestMapping(value = "/getStudentCourseByStudentId", method = RequestMethod.POST)
    @ResponseBody
    public Message getStudentCourseByStudentId(){
        UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getDetails();
        int studentId = userInfo.getId();
        List<CourseClass> courseClassList = courseClassService.getStudentCourseByStudentId(studentId);
        return new Message(1,"Success",courseClassList);
    }
}
