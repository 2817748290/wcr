package com.sxito.wcr.service.impl;

import com.sxito.wcr.bean.Message;
import com.sxito.wcr.bean.UserGroup;
import com.sxito.wcr.bean.UserInfo;
import com.sxito.wcr.config.security.JwtTokenUtil;
import com.sxito.wcr.mapper.UserGroupMapper;
import com.sxito.wcr.mapper.UserInfoMapper;
import com.sxito.wcr.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Component
public class LoginServiceImpl implements LoginService{

    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private UserGroupMapper userGroupMapper;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    public Message login(UserInfo userInfo, HttpServletResponse response) {
        UserInfo user = userInfoMapper.getByUsername(userInfo.getUsername());
        if(user != null){
            if (userInfo.getUsername().equals(user.getUsername())&&userInfo.getPassword().equals(user.getPassword())){
                user.setPassword("");
                UserGroup userGroup = userGroupMapper.getByUserId(user.getGroupId());
                user.setGroup(userGroup);
                List<String> roles = new ArrayList<>();
                roles.add(userGroup.getPermission());
                user.setRoles(roles);

                response.addHeader("refresh",jwtTokenUtil.create(user));
                return new Message(Message.SUCCESS,"登陆成功", user);
            }else{
                return new Message(Message.ERROR,"密码错误",null);
            }
        }else{
            return new Message(Message.ERROR,"用户名不存在",null);
        }

    }

//    public UserInfo getByUsername(String username){
//        if(username.equals("lrb")){
//            UserInfo userInfo = new UserInfo();
//            userInfo.setUsername("lrb");
//            userInfo.setPassword("123");
//            userInfo.setNickname("李若白");
//
//            return userInfo;
//        }else{
//            return null;
//        }
//    }
}
