package com.sxito.wcr.controller;

import com.sxito.wcr.bean.CourseInfo;
import com.sxito.wcr.bean.CourseNode;
import com.sxito.wcr.bean.Message;
import com.sxito.wcr.service.CourseNodeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@Api(value = "CourseNodeController",description = "课程内容操作相关api")
public class CourseNodeController {
    @Autowired
    private CourseNodeService courseNodeService;

    @ApiOperation(value = "课程节点通过课程id获取信息列表", notes = "课程节点通过课程id获取信息列表注意事项", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @RequestMapping(value = "/api/getCourseNodeByCourseId",method = RequestMethod.POST)
    @ResponseBody
    public Message getCourseNodeByCourseId(@RequestBody CourseInfo courseInfo){
        int courseId = courseInfo.getId();
        List<CourseNode> courseNodes= courseNodeService.getCourseNodeByCourseId(courseId);
        System.out.println(courseNodes);
        return new Message(1,"SUCCESS",courseNodes);
    }

    @ApiOperation(value = "课程节点通过节点id获取信息列表", notes = "课程节点通过节点id获取信息列表注意事项", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @RequestMapping(value = "/api/getCourseNodeByNodeId",method = RequestMethod.POST)
    @ResponseBody
    public Message getCourseNodeByNodeId(@RequestBody CourseNode courseNode){
        int nodeId = courseNode.getId();
        CourseNode courseNodes= courseNodeService.checkPermission(nodeId);
        return new Message(1,"SUCCESS",courseNodes);
    }

    @ApiOperation(value = "添加课程节点", notes = "添加课程节点注意事项", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @RequestMapping(value = "/api/addCourseNodeInfo",method = RequestMethod.POST)
    @ResponseBody
    public Message addCourseNodeInfo(@RequestBody CourseNode courseNode){
        try{
            if (courseNodeService.addCourseNodeInfo(courseNode)){
                CourseNode courseNode1 = courseNodeService.checkPermission(courseNode.getId());
                return new Message(1,"SUCCESS",courseNode1);
            }else{
                return new Message(1,"ERROR","创建失败");
            }
        }catch (Exception e){
            e.printStackTrace();
            return new Message(1,"ERROR","创建失败");
        }
    }

    @ApiOperation(value = "更新课程节点", notes = "更新课程节点顺序注意事项", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @RequestMapping(value = "/api/updateCourseNodeInfo",method = RequestMethod.POST)
    @ResponseBody
    public Message updateCourseNodeInfo(@RequestBody CourseNode courseNode){
        try{
            if(courseNodeService.updateCourseNodeInfo(courseNode)){
                return new Message(1,"SUCCESS","更新成功");
            }else{
                return new Message(1,"ERROR","更新失败");
            }
        }catch(Exception e){
            e.printStackTrace();
            return new Message(1,"ERROR","更新失败");
        }
    }

    @ApiOperation(value = "更新课程节点title", notes = "更新课程节点title注意事项", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @RequestMapping(value = "/api/updateCourseNodeInfoTitle",method = RequestMethod.POST)
    @ResponseBody
    public Message updateCourseNodeInfoTitle(@RequestBody CourseNode courseNode){
        try{
            if(courseNodeService.updateCourseNodeInfoTitle(courseNode)){
                return new Message(1,"SUCCESS","更新成功");
            }else{
                return new Message(1,"ERROR","更新失败");
            }
        }catch(Exception e){
            e.printStackTrace();
            return new Message(1,"ERROR","更新失败");
        }
    }

    @ApiOperation(value = "删除课程节点", notes = "删除课程节点注意事项", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @RequestMapping(value = "/api/deleteCourseNodeById",method = RequestMethod.POST)
    @ResponseBody
    public Message deleteCourseNodeById(@RequestBody int[] nodeId){
        for(int i=0;i<nodeId.length;i++){
            System.out.println(nodeId[i]);
        }
        if(nodeId.length==0){
            return new Message(0,"ERROR","删除失败");
        }
        boolean isSure = courseNodeService.delCourseNodeInfo(nodeId);
        if(isSure){
            return new Message(0,"SUCCESS","删除成功");
        }else{
            return new Message(0,"ERROR","删除失败");
        }
    }

}
