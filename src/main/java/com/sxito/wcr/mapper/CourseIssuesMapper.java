package com.sxito.wcr.mapper;

import com.sxito.wcr.bean.CourseIssues;
import com.sxito.wcr.bean.TableMessage;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CourseIssuesMapper {

    void addCourseIssues(CourseIssues courseIssues) throws Exception;

    int count(TableMessage tableMessage) throws Exception;

    List<CourseIssues> getIssues(TableMessage tableMessage);

    CourseIssues getById(int issuesId);

    List<CourseIssues> getCourseIssuesByNodeIdList(int[] checkNodeId) throws Exception;

    int deleteCourseIssues(int id) throws Exception;

    void updateCourseIssues(CourseIssues courseIssues) throws Exception;
}
