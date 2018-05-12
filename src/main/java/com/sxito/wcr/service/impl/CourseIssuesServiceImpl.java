package com.sxito.wcr.service.impl;

import com.sxito.wcr.bean.*;
import com.sxito.wcr.mapper.CourseIssuesMapper;
import com.sxito.wcr.mapper.CourseNodeMapper;
import com.sxito.wcr.service.CourseInfoService;
import com.sxito.wcr.service.CourseIssuesService;
import com.sxito.wcr.service.CourseNodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.web.PortResolverImpl;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
public class CourseIssuesServiceImpl implements CourseIssuesService {
    @Autowired
    private CourseIssuesMapper courseIssuesMapper;
    @Autowired
    private CourseNodeMapper courseNodeMapper;
    @Autowired
    private CourseNodeService courseNodeService;

    @Override
    public CourseIssues checkPermission(int issuesId) throws RuntimeException {
        CourseIssues courseIssues = courseIssuesMapper.getById(issuesId);
        if(courseIssues != null){
            CourseNode courseNode = courseNodeService.checkPermission(courseIssues.getNodeId());
            courseIssues.setCourseNode(courseNode);
            return courseIssues;
        }else{
            throw new RuntimeException("未找到课程节点");
        }
    }

    @Override
    @Transactional
        public void addCourseIssues(CourseIssues courseIssues) throws RuntimeException {
                if (courseIssues.getContent()!=null&&courseIssues.getContent()!=""){
                    courseNodeService.checkPermission(courseIssues.getNodeId());
                    try {
                        courseIssuesMapper.addCourseIssues(courseIssues);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else {
                    throw new RuntimeException("议题不能为空");
                }
        }

        @Override
        public TableMessage getCourseIssues(TableMessage tableMessage) {
            List<CourseIssues> courseIssuesList= new ArrayList<>();
            try {
                if (tableMessage.getSearch()!=null){
                }else {
                    courseIssuesList=courseIssuesMapper.getIssues(tableMessage);
                    CourseNode courseNode =new CourseNode();
                        for (CourseIssues courseIssues:courseIssuesList) {
                            courseNode = courseNodeMapper.getCourseNodeByNodeId(tableMessage.getNodeId());
                            courseIssues.setCourseNode(courseNode);
                    }
                    tableMessage.setRows(courseIssuesList);
                }
                tableMessage.setTotal(courseIssuesMapper.count(tableMessage));}
            catch (Exception e) {
                e.printStackTrace();
            }
            return tableMessage;
        }

    @Override
    public List<CourseIssues> getCourseIssuesByNodeIdList(int[] checkNodeId) throws Exception {
        List<CourseIssues> courseIssues = new ArrayList<CourseIssues>();
        try {
            if (checkNodeId.length == 0) {
                return null;
            } else {
                courseIssues = courseIssuesMapper.getCourseIssuesByNodeIdList(checkNodeId);
                return courseIssues;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    @Override
    @Transactional
    public void deleteCourseIssues(int id, int nodeId) throws RuntimeException{
        if (id!=0) {
            courseNodeService.checkPermission(nodeId);
            try {
                courseIssuesMapper.deleteCourseIssues(id);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            throw  new RuntimeException("不可删除");
        }
    }

    @Override
    @Transactional
    public void updateCourseIssues(CourseIssues courseIssues) throws RuntimeException{
        if (courseIssues.getId()!=0) {
            if (courseIssues.getContent() != null) {
                courseNodeService.checkPermission(courseIssues.getNodeId());
                try {
                    courseIssuesMapper.updateCourseIssues(courseIssues);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                throw new RuntimeException("议题不能为空");
            }
        }else{
                throw new RuntimeException("不可删除");
        }
    }

    @Override
    @Transactional
    public List<CourseIssues> getCourseIssuesByIdList(List<Integer> list) {
        List<CourseIssues> courseIssuesList = new ArrayList<>();
        for (int i:list) {
            CourseIssues courseIssues = courseIssuesMapper.getById(i);
            if(courseIssues != null){
                courseIssuesList.add(courseIssues);
            }
        }
        return courseIssuesList;
    }

}
