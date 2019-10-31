package com.it.o2o.dao;

import com.it.o2o.BaseTest;
import com.it.o2o.entity.LocalAuth;
import com.it.o2o.entity.UserInfo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * @author wjh
 * @create 2019-06-08-16:42
 */
public class LocalAuthDaoTest extends BaseTest {
    @Autowired
    private LocalAuthDao localAuthDao;

    @Test
    public void testInsertLocalAuth() {
        LocalAuth localAuth = new LocalAuth();
        localAuth.setUserName("李四");
        localAuth.setPassword("123456");
        localAuth.setCreateTime(new Date());
        localAuth.setLastEditTime(new Date());

        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(1l);
        localAuth.setUserInfo(userInfo);

        int i = localAuthDao.insertLocalAuth(localAuth);

        System.out.println(i);
    }

    @Test
    public void testGetLocalAuthByUserId() {
        LocalAuth localAuth = localAuthDao.queryLocalByUserId(1L);

        System.out.println(localAuth.getUserName());
        System.out.println(localAuth.getUserInfo().getName());

    }

    @Test
    public void testGetLocalAuthByNameAndPwd(){
        LocalAuth localAuth = localAuthDao.queryLocalByUserNameAndPwd("李四", "123456");
        System.out.println(localAuth.getUserName());
        System.out.println(localAuth.getUserInfo().getName());

        int i = localAuthDao.updateLocalAuth(1L, "李四", "123456", "888888", new Date());
        System.out.println(i);

    }
}
