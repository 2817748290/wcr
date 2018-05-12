package com.sxito.wcr.service;

import com.sxito.wcr.bean.Message;
import com.sxito.wcr.bean.UserInfo;

import javax.servlet.http.HttpServletResponse;

public interface LoginService {
    public Message login(UserInfo userInfo,HttpServletResponse response);
}
