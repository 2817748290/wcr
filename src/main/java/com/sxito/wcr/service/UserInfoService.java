package com.sxito.wcr.service;

import com.sxito.wcr.bean.TableMessage;
import com.sxito.wcr.bean.UserInfo;

import java.util.List;

public interface UserInfoService {

    TableMessage getAllStudentPage(TableMessage tableMessage);

    Integer updateUserInfoById(UserInfo userInfo) throws Exception;

}
