package com.it.o2o.service.impl;

import java.util.Date;

import com.it.o2o.dao.LocalAuthDao;
import com.it.o2o.dto.LocalAuthExecution;
import com.it.o2o.entity.LocalAuth;
import com.it.o2o.enums.LocalAuthStateEnum;
import com.it.o2o.service.LocalAuthService;
import com.it.o2o.util.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class LocalAuthServiceImpl implements LocalAuthService {

    @Autowired
    private LocalAuthDao localAuthDao;

    @Override
    public LocalAuth getLocalAuthByUserNameAndPwd(String userName, String password) {
        return localAuthDao.queryLocalByUserNameAndPwd(userName, MD5.getMd5(password));
    }

    @Override
    public LocalAuth getLocalAuthByUserId(long userId) {
        return localAuthDao.queryLocalByUserId(userId);
    }


    /**
     * 绑定账号
     *
     * @param localAuth
     * @return
     * @throws RuntimeException
     */
    @Override
    @Transactional
    public LocalAuthExecution bindLocalAuth(LocalAuth localAuth) throws RuntimeException {
        //空值判断
        if (localAuth == null || localAuth.getPassword() == null
                || localAuth.getUserName() == null
                || localAuth.getUserInfo().getUserId() == null) {
            return new LocalAuthExecution(LocalAuthStateEnum.NULL_AUTH_INFO);
        }
        //查看此用户是否已绑定过平台账号，保证平台账号的唯一性
        LocalAuth tempAuth = localAuthDao.queryLocalByUserId(localAuth.getUserInfo().getUserId());
        if (tempAuth != null) {
            //如果有，直接退出
            return new LocalAuthExecution(LocalAuthStateEnum.ONLY_ONE_ACCOUNT);
        }
        try {
            //没有绑定过账号，则创建一个账号与改用户进行绑定
            localAuth.setCreateTime(new Date());
            localAuth.setLastEditTime(new Date());
            //密码使用MD5进行加密，然后存储到数据库中
            localAuth.setPassword(MD5.getMd5(localAuth.getPassword()));
            int effectedNum = localAuthDao.insertLocalAuth(localAuth);
            //判断账号是否创建成功
            if (effectedNum <= 0) {
                throw new RuntimeException("帐号绑定失败");
            } else {
                return new LocalAuthExecution(LocalAuthStateEnum.SUCCESS, localAuth);
            }
        } catch (Exception e) {
            throw new RuntimeException("insertLocalAuth error: " + e.getMessage());
        }
    }

    /**
     * 修改密码
     *
     * @param userId
     * @param userName
     * @param password
     * @param newPassword
     * @return
     */
    @Override
    @Transactional
    public LocalAuthExecution modifyLocalAuth(Long userId, String userName,
                                              String password, String newPassword) {
        if (userId != null && userName != null && password != null
                && newPassword != null && !password.equals(newPassword)) {
            try {
                int effectedNum = localAuthDao.updateLocalAuth(userId, userName,
                        MD5.getMd5(password),
                        MD5.getMd5(newPassword), new Date());
                if (effectedNum <= 0) {
                    throw new RuntimeException("更新密码失败");
                }
                return new LocalAuthExecution(LocalAuthStateEnum.SUCCESS);
            } catch (Exception e) {
                throw new RuntimeException("更新密码失败:" + e.toString());
            }
        } else {
            return new LocalAuthExecution(LocalAuthStateEnum.NULL_AUTH_INFO);
        }
    }
}
