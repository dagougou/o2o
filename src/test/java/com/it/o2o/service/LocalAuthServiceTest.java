package com.it.o2o.service;

import com.it.o2o.BaseTest;
import com.it.o2o.dto.LocalAuthExecution;
import com.it.o2o.entity.LocalAuth;
import com.it.o2o.entity.UserInfo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * @author wjh
 * @create 2019-06-08-17:14
 */
public class LocalAuthServiceTest extends BaseTest {
    @Autowired
    LocalAuthService localAuthService;

    @Test
    public void testBindLocalAuth() {
        LocalAuth localAuth = new LocalAuth();
        localAuth.setUserName("王五");
        localAuth.setPassword("123456");
        localAuth.setCreateTime(new Date());
        localAuth.setLastEditTime(new Date());

        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(2l);
        localAuth.setUserInfo(userInfo);

        LocalAuthExecution localAuthExecution = localAuthService.bindLocalAuth(localAuth);

        System.out.println(localAuthExecution.getStateInfo());

    }

    @Test
    public void TestModifyLocalAuth() {
        LocalAuthExecution localAuthExecution = localAuthService.modifyLocalAuth(2l, "王五", "123456", "888888");
        System.out.println(localAuthExecution.getStateInfo());
    }

}
