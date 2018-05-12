package com.sxito.wcr.service;

import com.sxito.wcr.bean.CourseIssues;
import com.sxito.wcr.bean.Message;
import com.sxito.wcr.bean.TableMessage;

import java.util.List;
import java.util.Map;

public interface CourseIssuesService {
    CourseIssues checkPermission(int issuesId) throws RuntimeException;

    void addCourseIssues(CourseIssues courseIssues) throws RuntimeException;

    TableMessage getCourseIssues(TableMessage tableMessage);

    List<CourseIssues> getCourseIssuesByNodeIdList(int[] checkNodeId) throws Exception;

    void deleteCourseIssues(int id,int nodeId) throws RuntimeException;

    void updateCourseIssues(CourseIssues courseIssues) throws RuntimeException;

    List<CourseIssues> getCourseIssuesByIdList(List<Integer> list);
}
