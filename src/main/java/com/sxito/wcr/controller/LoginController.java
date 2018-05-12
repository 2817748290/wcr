package com.sxito.wcr.controller;


import com.sxito.wcr.bean.Message;
import com.sxito.wcr.bean.UserInfo;
import com.sxito.wcr.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @RequestMapping(value = "/public/login",method = RequestMethod.POST)
    public Message login(@RequestBody UserInfo user, HttpSession session, HttpServletResponse response){

        Message message = loginService.login(user,response);
        if(message != null){
            if(message.getStatus() == Message.SUCCESS){
                session.setAttribute("user",message.getResult());
            }
        }
        return message;
    }
}
