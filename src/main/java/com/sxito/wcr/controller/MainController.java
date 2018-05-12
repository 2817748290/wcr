package com.sxito.wcr.controller;

import com.sxito.wcr.bean.Message;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Api(value = "MainController",description = "界面测试api")
public class MainController {

    @ApiOperation(value="主页面访问", notes="1.0")
    @RequestMapping(value = "/",method = RequestMethod.GET)
    public String index(){
        return "index";
    }

    @ApiOperation(value="webSocket测试页面", notes="1.0")
    @RequestMapping(value = "/web",method = RequestMethod.GET)
    public String web(){
        return "websocket";
    }

    @ApiImplicitParams(
        {@ApiImplicitParam(name = "id", value = "唯一id", required = true, dataType = "Long", paramType = "path"),
         @ApiImplicitParam(name = "id", value = "唯一id", required = true, dataType = "Long", paramType = "path")
        }
     )
    @RequestMapping(value = "/api/test",method = RequestMethod.GET)
    @ResponseBody
    public Message test(){
        return new Message(Message.ERROR,"hello!word!!","wcr牛啊!!");
    }
    @RequestMapping(value = "/doc",method = RequestMethod.GET)
    public String swaggerindex() {
        System.out.println("swagger-ui.html");
        return "redirect:swagger-ui.html";
    }

}
