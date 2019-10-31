package com.it.o2o.service.impl;

import com.it.o2o.dao.UserInfoDao;
import com.it.o2o.entity.UserInfo;
import com.it.o2o.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wjh
 * @create 2019-06-07-17:32
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {
    @Autowired
    UserInfoDao userInfoDao;

    @Override
    public UserInfo getUserinfoById(Long userId) {
        return userInfoDao.queryUserInfoById(userId);
    }
}
