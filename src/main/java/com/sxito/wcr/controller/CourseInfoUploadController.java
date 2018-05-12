package com.sxito.wcr.controller;

import com.sxito.wcr.bean.Message;
import com.sxito.wcr.utils.CommonUtil;
import com.sxito.wcr.utils.DownloadUtil;
import com.sxito.wcr.utils.UploadUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by lzp on 2017/6/18.
 * 上传下载示例
 */
@Controller
@RequestMapping("/api/courseInfo")
@Api(value = "CourseInfoUploadController",description = "课程信息文件上传相关api")
public class CourseInfoUploadController {

    @ApiOperation(value = "课程信息封面及富文本图片上传", notes = "图片上传注意事项", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @RequestMapping(value = "/upload", method = RequestMethod.POST )
    @ResponseBody
    public Message upload(@RequestParam MultipartFile file) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        if (file.isEmpty()) {
            return new Message(Message.ERROR,"文件为空",null);
        }else{
            String save = "courseInfoUpload";

            Map map = UploadUtil.singleFileUpload(file,save, CommonUtil.getIpAddr(request));
            if((Boolean) map.get("status")){
                return new Message(Message.SUCCESS,"保存成功", "/public/download?filename="+map.get("path"));
            }else{
                return new Message(Message.ERROR,"保存失败",null);
            }
        }
    }

    @ApiOperation(value = "课程信息封面及富文本图片下载", notes = "图片下载相关事项", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @RequestMapping(value = "/download", method = RequestMethod.POST)
    @ResponseBody
    public void downFile(
            @RequestParam(value = "filename") String odexName,
            HttpServletResponse response,
            HttpServletRequest request) {
        DownloadUtil.downUploadFile(odexName,request,response);
    }

}
