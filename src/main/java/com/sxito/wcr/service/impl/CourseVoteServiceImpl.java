package com.sxito.wcr.service.impl;

import com.sxito.wcr.bean.CourseNode;
import com.sxito.wcr.bean.CourseVote;
import com.sxito.wcr.bean.TableMessage;
import com.sxito.wcr.mapper.CourseNodeMapper;
import com.sxito.wcr.mapper.CourseVoteMapper;
import com.sxito.wcr.service.CourseNodeService;
import com.sxito.wcr.service.CourseVoteService;
import com.sxito.wcr.service.DataCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class CourseVoteServiceImpl implements CourseVoteService{
    @Autowired
    private CourseVoteMapper courseVoteMapper;
    @Autowired
    private CourseNodeMapper courseNodeMapper;
    @Autowired
    private CourseNodeService courseNodeService;

    @Override
    public CourseVote checkPermission(int voteId) throws RuntimeException {
        CourseVote courseVote = courseVoteMapper.getById(voteId);
        if(courseVote != null){
            CourseNode courseNode = courseNodeService.checkPermission(courseVote.getNodeId());
            courseVote.setCourseNode(courseNode);
            return courseVote;
        }else{
            throw new RuntimeException("未找到课程节点 ");
        }
    }
    @Override
    @Cacheable(value="CourseVote" ,key="#id")
    public CourseVote getById(int id) {
        System.out.println("=====================");
        CourseVote courseVote = courseVoteMapper.getById(id);
        return courseVote;
    }
    @Override
    @Transactional
    public void addCourseVote(CourseVote courseVote) throws RuntimeException{
        if (courseVote.getContent()!=null&&courseVote.getContent()!=""){
            if (courseVote.getTitle()!=null&&courseVote.getTitle()!=""){
                courseNodeService.checkPermission(courseVote.getNodeId());
                try{
                    courseVoteMapper.addCourseVote(courseVote);
                }catch (Exception e){
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            } else {
                throw new RuntimeException("表决题目不能为空");
            }
        } else {
            throw new RuntimeException("表决选项不能为空");
        }

    }

    @Override
    public TableMessage getCourseVote(TableMessage tableMessage) {
        List<CourseVote> courseVoteList= new ArrayList<>();
        try {
            if (tableMessage.getSearch()!=null){

            }else {
                courseVoteList=courseVoteMapper.getCourseVote(tableMessage);
                CourseNode courseNode =new CourseNode();
                for (CourseVote courseVote:courseVoteList) {
                    courseNode = courseNodeMapper.getCourseNodeByNodeId(tableMessage.getNodeId());
                    courseVote.setCourseNode(courseNode);
                }
                tableMessage.setRows(courseVoteList);
            }
            tableMessage.setTotal(courseVoteMapper.count(tableMessage));}

        catch (Exception e) {
            e.printStackTrace();
        }

        return tableMessage;
    }

    @Override
    public List<CourseVote> getCourseVodeByNodeIdList(int[] checkNodeId){
        List<CourseVote> courseVotes = new ArrayList<CourseVote>();
        try{
            if(checkNodeId.length==0){
                return null;
            }else{
                courseVotes = courseVoteMapper.getCourseVodeByNodeId(checkNodeId);
                return courseVotes;
            }
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    @Transactional
    @CacheEvict(value="CourseVote" ,key="#id")
    public void deleteCourseVote(int id, int nodeId) throws RuntimeException{
        if (id!=0) {
            courseNodeService.checkPermission(nodeId);
            try {
                courseVoteMapper.deleteCourseVote(id);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            throw  new RuntimeException("不可删除");
        }
    }

    @Override
    @Transactional
    @CachePut(value="CourseVote" ,key="#courseVote.id")
    public void updateCourseVote(CourseVote courseVote) throws RuntimeException{
        if (courseVote.getId()!=0) {
            if (courseVote.getContent() != null && courseVote.getContent() != "") {
                if (courseVote.getContent() != null && courseVote.getContent() != "") {
                    courseNodeService.checkPermission(courseVote.getNodeId());
                    try {
                        courseVoteMapper.updateCourseVote(courseVote);
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw new RuntimeException(e);
                    }
                } else {
                    throw new RuntimeException("表决题目不能为空");
                }
            } else {
                throw new RuntimeException("表决内容不能为空");
            }
        }else{
            throw new RuntimeException("不可删除");
        }
    }

    @Autowired CourseVoteService courseVoteService;

    @Override
    @Transactional
    public List<CourseVote> getCourseVoteByIdList(List<Integer> list) {
        List<CourseVote> courseVoteList = new ArrayList<>();
        for (int i:list) {
            CourseVote courseVote = courseVoteService.getById(i);
            if(courseVote!=null){
                courseVoteList.add(courseVote);
            }

        }
        return courseVoteList;
    }

}
