package com.sxito.wcr.mapper;

import com.sxito.wcr.bean.UserGroup;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserGroupMapper {

    UserGroup getByUserId(int id);

}
