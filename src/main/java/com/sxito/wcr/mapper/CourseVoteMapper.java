package com.sxito.wcr.mapper;

import com.sxito.wcr.bean.CourseVote;
import com.sxito.wcr.bean.TableMessage;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CourseVoteMapper {
     void addCourseVote(CourseVote courseVote) throws Exception ;

    int count(TableMessage tableMessage) throws Exception ;

    List<CourseVote> getCourseVote(TableMessage tableMessage);

    Integer deleteCourseVote(int id) throws Exception;

    void updateCourseVote(CourseVote courseVote) throws Exception ;

    CourseVote getById(int voteId);

    List<CourseVote> getCourseVodeByNodeId(int[] checkNodeId) throws Exception;
}
