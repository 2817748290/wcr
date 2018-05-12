package com.sxito.wcr.controller;

import com.sxito.wcr.bean.CourseContent;
import com.sxito.wcr.bean.Message;
import com.sxito.wcr.bean.TableMessage;
import com.sxito.wcr.service.CourseContentService;
import com.sxito.wcr.utils.CommonUtil;
import com.sxito.wcr.utils.UploadUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

/**
 * @author xlj
 */
@Api(description = "教师对添加上课文件内容的管理")
@Controller
public class CourseContentController {

    @Autowired
    private CourseContentService contentService;

    @ApiOperation(value = "根据内容id获取对应内容信息", notes = "根据内容id获取对应内容信息注意事项", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @RequestMapping(value = "/api/getFileInfoById",method = RequestMethod.POST)
    @ResponseBody
    public Message getFileInfoById(@RequestBody TableMessage tableMessage){
        try{
            return new Message(1,"获取成功", contentService.getFileInfoById(tableMessage));
        }catch (RuntimeException e){
            return new Message(1,e.getMessage(), null);
        }
    }

    @ApiOperation(value = "根据文件内容id删除对应内容", notes = "根据文件内容id删除对应内容注意事项", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @RequestMapping(value = "/api/deleteFile",method = RequestMethod.POST)
    @ResponseBody
    public Message deleteFile(@RequestBody CourseContent courseContent){
        try{
            int id = courseContent.getId();
            int state = contentService.deleteFile(id);

            if(state == 1){
                return new Message(1,"成功","success");
            }else{
                return new Message(-1,"失败","fail");
            }
        }catch (RuntimeException e){
            return new Message(1,e.getMessage(), null);
        }

    }

    @ApiOperation(value = "上传文件信息", notes = "上传文件信息注意事项", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @RequestMapping(value = "/api/addContent/upload",method = RequestMethod.POST)
    @ResponseBody
    public Message upload(@RequestParam MultipartFile file,CourseContent courseContent) {

        try {
            courseContent.setGmtCreate(new Date());
            courseContent.setFileName(file.getOriginalFilename());
            courseContent.setFileType(courseContent.getFileName().substring(courseContent.getFileName().lastIndexOf(".") + 1, courseContent.getFileName().length()));

            String video = "swf.avi.rmvb.rm.asf.divx.mpg.mpeg.mpe.wmv.mp4.mkv.vob";
            String slide = ".ppt.pptx";
            String fileB = ".docx.doc.wps.xls.txt.pdf.PDF.xlsx";

            if (video.indexOf(courseContent.getFileType()) != -1) {
                courseContent.setTags(1);
            } else if (slide.indexOf(courseContent.getFileType()) != -1) {
                courseContent.setTags(2);
            } else if (fileB.indexOf(courseContent.getFileType()) != -1) {
                courseContent.setTags(3);
            } else {
                courseContent.setTags(4);
            }

            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            if (file.isEmpty()) {
                return new Message(Message.ERROR, "文件为空", null);
            } else {
                String save = "courseInfoUpload";
                Map map = UploadUtil.singleFileUpload(file, save, CommonUtil.getIpAddr(request));
                if ((Boolean) map.get("status")) {
                    courseContent.setFilePath("/public/download?filename=" + map.get("path"));
                    contentService.addCourseContent(courseContent);
                    return new Message(Message.SUCCESS, "保存成功", "/public/download?filename=" + map.get("path"));
                } else {
                    return new Message(Message.ERROR, "保存失败", null);
                }
            }
        }catch (RuntimeException e){
            return new Message(1,e.getMessage(), null);
        }
    }

}
