package com.sxito.wcr.service;

import com.sxito.wcr.bean.ClassGroup;

import java.util.List;
import java.util.Map;

/**
 * Created by 62450 on 2017/12/2.
 */
public interface ClassGroupService {
    boolean addClassGroup(ClassGroup classGroup)throws RuntimeException;

    ClassGroup checkPermission(int groupId) throws RuntimeException;

    List<ClassGroup> getClassGroupByClassId(int classId)throws RuntimeException;

    ClassGroup getClassGroupByGroupId(int groupId);

    List<Map<String,Object>> getStudentListByGroupId(int id)throws RuntimeException;

    List<Map<String,Object>> getStudentListByGroups(String groups)throws RuntimeException;
}
