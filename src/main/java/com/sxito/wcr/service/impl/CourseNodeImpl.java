package com.sxito.wcr.service.impl;

import com.sxito.wcr.bean.CourseInfo;
import com.sxito.wcr.bean.CourseNode;
import com.sxito.wcr.mapper.CourseInfoMapper;
import com.sxito.wcr.mapper.CourseNodeMapper;
import com.sxito.wcr.service.CourseInfoService;
import com.sxito.wcr.service.CourseNodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CourseNodeImpl implements CourseNodeService {
    @Autowired
    private CourseInfoService courseInfoService;
    @Autowired
    private CourseNodeMapper courseNodeMapper;
    @Autowired
    private CourseInfoMapper courseInfoMapper;

    @Override
    public CourseNode checkPermission(int nodeId) throws RuntimeException {
        CourseNode courseNode = courseNodeMapper.getCourseNodeByNodeId(nodeId);
        if(courseNode != null){
//            CourseInfo courseInfo = courseInfoService.checkPermission(courseNode.getCourseId());
//            courseNode.setCourseInfo(courseInfo);
            return courseNode;
        }else{
            throw new RuntimeException("未找到课程节点");
        }
    }

    public boolean addCourseNodeInfo(CourseNode courseNode){
        try{
            if (courseInfoMapper.getCourseByCourseId(courseNode.getCourseId()) == null){
                return false;
            }else {
                if(courseNode.getParentId()!=0){
                    System.out.println(courseNode.getParentId());
                    if(courseNodeMapper.getCourseNodeByNodeId(courseNode.getParentId())!=null){
                        courseNodeMapper.addCourseNodeInfo(courseNode);
                    }else{
                        return false;
                    }
                }else{
                    return false;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public List<CourseNode> getCourseNodeByCourseId(int courseId) throws RuntimeException{
        List<CourseNode> courseNodes = new ArrayList<CourseNode>();
        courseNodes = courseNodeMapper.getCourseNodeByCourseId(courseId);
        if(courseNodes !=null){
            return courseNodes;
        }else{
            throw new RuntimeException("该称课程下不存在课程节点");
        }
    }

    public boolean updateCourseNodeInfo(CourseNode courseNode){
        try{
            if(courseInfoMapper.getCourseByCourseId(courseNode.getCourseId()) == null){
                return false;
            }else{
                CourseNode courseNode1 = courseNodeMapper.getCourseNodeByNodeId(courseNode.getId());

                courseNodeMapper.updateCourseNodeInfo(courseNode);

            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean delCourseNodeInfo(int[] nodeId) throws RuntimeException{
        CourseNode courseNode = courseNodeMapper.getCourseNodeByNodeId(nodeId[0]);
        if (courseNode!=null){
            courseNodeMapper.delCourseNodeInfo(nodeId);
            return true;
        }else{
            throw new RuntimeException("未找到课程节点");
        }
    }

    public boolean updateCourseNodeInfoTitle(CourseNode courseNode){
        try{
                CourseNode courseNode1 = courseNodeMapper.getCourseNodeByNodeId(courseNode.getId());
                courseNodeMapper.updateCourseNodeInfoTitle(courseNode);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
