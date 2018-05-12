package com.sxito.wcr.mapper;

import com.sxito.wcr.bean.StudentClass;
import com.sxito.wcr.bean.TableMessage;
import com.sxito.wcr.bean.UserInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by 62450 on 2017/11/9.
 */
@Mapper
public interface UserInfoMapper {
    UserInfo getByUsername(String username);

    int count(TableMessage tableMessage) throws Exception;

    List<UserInfo> findPage(TableMessage tableMessage) throws Exception;

    List<UserInfo> getClassStudent(int classId) throws Exception;

    void addStudentIntoClass(StudentClass userClass) throws Exception;

    UserInfo getUserInfoById(int id) throws Exception;

    List<UserInfo> getTeacher() throws Exception;

    List<UserInfo> getAllStudentPage(TableMessage tableMessage);

    int getAllStudentPageCount(TableMessage tableMessage);

    int updateUserInfoById(UserInfo userInfo) throws Exception;
}
