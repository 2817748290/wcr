package com.sxito.wcr.service.impl;

import com.sxito.wcr.bean.TableMessage;
import com.sxito.wcr.bean.UserInfo;
import com.sxito.wcr.mapper.UserInfoMapper;
import com.sxito.wcr.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Component
public class UserInfoServiceImpl implements UserInfoService{
    @Autowired
    private UserInfoMapper userInfoMapper;
    @Override
    @Transactional
    public TableMessage getAllStudentPage(TableMessage tableMessage) {
        List<UserInfo> userInfoList = userInfoMapper.getAllStudentPage(tableMessage);
        int count = userInfoMapper.getAllStudentPageCount(tableMessage);
        TableMessage tableMessageResult = new TableMessage();
        tableMessageResult.setTotal(count);
        tableMessageResult.setRows(userInfoList);
        return tableMessageResult;
    }

    @Override
    public Integer updateUserInfoById(UserInfo userInfo){

        int state = 0;

        try {
            state = userInfoMapper.updateUserInfoById(userInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return state;
        }

        return state;
    }
}
