package com.sxito.wcr.controller;

import com.sxito.wcr.bean.UserInfo;
import com.sxito.wcr.config.security.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
public class AuthController {
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    /**
     * 该链接尝试获取登录用户,返回该认证用户的信息,请求该链接需要在header中放入x-authorization: token
     */
    @GetMapping("/api/detail")
    public UserInfo userDetail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (Objects.isNull(authentication)) {
            System.out.println("null");
            return null;
        }
        System.out.println("avc");
        return (UserInfo) authentication.getDetails();
    }
    @GetMapping("/login")
    public String login() {
        UserInfo tokenUserDTO = new UserInfo();
        tokenUserDTO.setUsername("zpliu");
        tokenUserDTO.setNickname("小刘");
        List<String> roles = new ArrayList<>();
        roles.add("ROLE_ADMIN");
        tokenUserDTO.setRoles(roles);

        return jwtTokenUtil.create(tokenUserDTO);
    }
}
