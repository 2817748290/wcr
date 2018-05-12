package com.sxito.wcr.controller.pub;

import com.sxito.wcr.bean.ArrangeData;
import com.sxito.wcr.bean.Message;
import com.sxito.wcr.service.CourseArrangeService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
public class PublicCourseArrangeController {
    @Autowired
    private CourseArrangeService courseArrangeService;

    @ApiOperation(value = "查看所有安排详情", notes = "查看所有安排详情注意事项", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @RequestMapping(value = "/public/getArrangeData", method = RequestMethod.POST)
    public Message getArrangeData(@RequestBody Map map){
        int id=(int)map.get("id");
        try {
            List<ArrangeData> arrangeDataList=courseArrangeService.getArrangeData(id);
            return new Message(1,"操作成功",arrangeDataList);
        }catch (RuntimeException e){
            return new Message(0,e.getMessage(),null);
        }
    }
}
