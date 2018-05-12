package com.sxito.wcr.service.impl;

import com.sxito.wcr.bean.*;
import com.sxito.wcr.mapper.CourseContentMapper;
import com.sxito.wcr.service.CourseContentService;
import com.sxito.wcr.service.CourseNodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


@Component
public class CourseContentServiceImpl implements CourseContentService {

    @Autowired
    private CourseContentMapper courseContentMapper;

    @Autowired
    private CourseNodeService courseNodeService;

    @Override
    public CourseContent checkPermission(int id) throws RuntimeException {
        CourseContent courseContent = null;
        try {
            courseContent = courseContentMapper.getById(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("未找到课程内容文件");
        }
        if (courseContent != null) {
            CourseNode courseNode = courseNodeService.checkPermission(courseContent.getNodeId());
            courseContent.setCourseNode(courseNode);
            return courseContent;
        } else {
            throw new RuntimeException("未找到课程内容文件");
        }
    }


    @Override
    @Transactional
    public Map getFileInfoById(TableMessage tableMessage) throws RuntimeException{
        try {
            List<CourseContent> courseContent = null;
            courseContent = courseContentMapper.findPage(tableMessage);
            tableMessage.setTotal(courseContentMapper.getFileInfoCount(tableMessage));
            tableMessage.setRows(courseContent);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("获取失败");
        }
        return tableMessage.result();
    }

    @Override
    public CourseContent addCourseContent(CourseContent courseContent) throws RuntimeException{
        try {
            courseNodeService.checkPermission(courseContent.getNodeId());
            courseContentMapper.addCourseContent(courseContent);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("失败");
        }
        return courseContent;
    }

    @Override
    @Transactional
    public Integer deleteFile(int id) throws RuntimeException{
        int state = 0;
        try {
            state = courseContentMapper.deleteFile(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("删除失败");
        }
        return state;
    }
}
