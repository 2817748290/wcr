package com.sxito.wcr.controller.app;

import com.sxito.wcr.bean.Message;
import com.sxito.wcr.service.StudentInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app")
public class StudentClassController {
    @Autowired
    private StudentInfoService studentInfoService;

    @RequestMapping("/addClassByScanQcode")
    public Message addClassByScanQcode(@RequestParam String code){
        if(code == null && code.equals("")){
            return new Message(0,"code码为空",null);
        }else{
            try {
                studentInfoService.addClassByScanQcode(code);
            }catch (RuntimeException e){
                return new Message(0,e.getMessage(),null);
            }

            return new Message(0,"加入成功",null);
        }

    }
}
