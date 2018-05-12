package com.sxito.wcr.mapper;

import com.sxito.wcr.bean.ClassGroup;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by 62450 on 2017/12/2.
 */
@Mapper
public interface ClassGroupMapper {
    void addClassGroup(ClassGroup classGroup)throws Exception;

    ClassGroup getClassGroupByGroupId(int groupId)throws Exception;

    List<ClassGroup> getClassGroupByClassId(int classId)throws Exception;
}
