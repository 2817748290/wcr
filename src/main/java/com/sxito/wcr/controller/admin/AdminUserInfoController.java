package com.sxito.wcr.controller.admin;

import com.sxito.wcr.bean.Message;
import com.sxito.wcr.bean.TableMessage;
import com.sxito.wcr.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminUserInfoController {
    @Autowired
    private UserInfoService userInfoService;
    @RequestMapping(value = "/getAllstudent",method = RequestMethod.POST)
    public Message getAllStudent(@RequestBody TableMessage tableMessage){

        return new Message(1,"success",userInfoService.getAllStudentPage(tableMessage));
    }
}
