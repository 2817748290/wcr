package com.sxito.wcr.controller;

import com.sxito.wcr.bean.ClassGroup;
import com.sxito.wcr.bean.Message;
import com.sxito.wcr.service.ClassGroupService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Created by 62450 on 2017/12/2.
 */
@RestController
@RequestMapping("/api/classGroup")
public class ClassGroupController {
    @Autowired
    private ClassGroupService classGroupService;

    @ApiOperation(value = "添加班级分组", notes = "添加班级分组注意事项", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @RequestMapping(value = "/addClassGroup", method = RequestMethod.POST)
    public Message addClassGroup(@RequestBody ClassGroup classGroup){
        try {
            boolean result= classGroupService.addClassGroup(classGroup);
            return new Message(1,"添加成功",null);
        }catch (RuntimeException e){
            return new Message(0,e.getMessage(),null);
        }
    }

    @ApiOperation(value = "查看所有班级分组", notes = "查看班级分组注意事项", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @RequestMapping(value = "/getClassGroupByClassId", method = RequestMethod.POST)
    public Message getClassGroupByClassId(@RequestBody Map map){
        int classId=(int)map.get("classId");
        try {
            List<ClassGroup> classGroupList=classGroupService.getClassGroupByClassId(classId);
            return new Message(1,"操作成功",classGroupList);
        }catch (RuntimeException e){
            return new Message(0,e.getMessage(),null);
        }
    }
    @ApiOperation(value = "查看班级某分组", notes = "查看班级分组注意事项", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @RequestMapping(value = "/getClassGroupByGroupId", method = RequestMethod.POST)
    public Message getClassGroupByGroupId(@RequestBody Map map){
        int groupId=(int)map.get("groupId");
        try {
            ClassGroup classGroup=classGroupService.getClassGroupByGroupId(groupId);
            return new Message(1,"操作成功",classGroup);
        }catch (RuntimeException e){
            return new Message(0,e.getMessage(),null);
        }
    }

}
