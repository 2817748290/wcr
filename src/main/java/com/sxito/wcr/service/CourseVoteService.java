package com.sxito.wcr.service;

import com.sxito.wcr.bean.CourseVote;
import com.sxito.wcr.bean.TableMessage;

import java.util.List;

public interface CourseVoteService {
    CourseVote checkPermission(int voteId) throws RuntimeException;

    CourseVote getById(int id);

    void addCourseVote (CourseVote courseVote)throws RuntimeException;

    TableMessage getCourseVote(TableMessage tableMessage);

    List<CourseVote> getCourseVodeByNodeIdList(int[] checkNodeId) throws Exception;

    void deleteCourseVote(int id, int nodeId)throws RuntimeException;

    void updateCourseVote(CourseVote courseVote)throws RuntimeException;

    List<CourseVote> getCourseVoteByIdList(List<Integer> list);
}
