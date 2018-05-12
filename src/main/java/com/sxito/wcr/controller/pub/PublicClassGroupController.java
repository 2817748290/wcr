package com.sxito.wcr.controller.pub;

import com.sxito.wcr.bean.Message;
import com.sxito.wcr.service.ClassGroupService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * Created by 62450 on 2017/12/9.
 */
@RestController
@Api("公开的课程安排分组信息接口")
public class PublicClassGroupController {
    @Autowired
    private ClassGroupService classGroupService;

    @ApiOperation(value = "补全某历史分组内学生基础数据", notes = "补全某历史分组内学生基础数据注意事项", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @RequestMapping(value = "/public/getStudentListByGroupId", method = RequestMethod.POST)
    public Message getStudentListByGroupId(@RequestBody Map map){
        int id=(int)map.get("id");
        try {
            List<Map<String,Object>> list=classGroupService.getStudentListByGroupId(id);
            return new Message(1,"操作成功",list);
        }catch (RuntimeException e){
            return new Message(0,e.getMessage(),null);
        }
    }

    @ApiOperation(value = "补全某分组内学生基础数据", notes = "补全某分组内学生基础数据注意事项", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @RequestMapping(value = "/public/getStudentListByGroups", method = RequestMethod.POST)
    public Message getStudentListByGroups(@RequestBody Map map){
        String groups=(String)map.get("groups");
        try {
            List<Map<String,Object>> list=classGroupService.getStudentListByGroups(groups);
            return new Message(1,"操作成功",list);
        }catch (RuntimeException e){
            return new Message(0,e.getMessage(),null);
        }
    }
}
