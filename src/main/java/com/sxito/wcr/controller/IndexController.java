package com.sxito.wcr.controller;

import com.sxito.wcr.bean.Message;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xlj
 * Created on 2017/11/12.
 */
@RestController
public class IndexController {


    @RequestMapping(value = "/api/getCarousel" ,method = RequestMethod.GET)
    @ResponseBody
    public Message getCarousel(){
        List list = new ArrayList();
        list.add("../../../static/1.png");
        list.add("../../../static/2.png");
        list.add("../../../static/3.png");

        return new Message(Message.SUCCESS,"已获取轮播图信息！",list);
    }
}