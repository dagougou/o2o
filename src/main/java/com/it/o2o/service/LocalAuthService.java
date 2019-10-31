package com.it.o2o.service;

import com.it.o2o.dto.LocalAuthExecution;
import com.it.o2o.entity.LocalAuth;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

public interface LocalAuthService {
    /**
     * 通过账号和密码查询用户
     *
     * @param userName
     * @return
     */
    LocalAuth getLocalAuthByUserNameAndPwd(String userName, String password);

    /**
     * @param userId
     * @return
     */
    LocalAuth getLocalAuthByUserId(long userId);

    /**
     * @param localAuth
     * @return
     * @throws RuntimeException
     */
    LocalAuthExecution bindLocalAuth(LocalAuth localAuth)
            throws RuntimeException;

    /**
     * 修改账号登陆密码
     *
     * @param userName
     * @param password
     * @param newPassword
     * @return
     */
    LocalAuthExecution modifyLocalAuth(Long userId, String userName, String password, String newPassword);
}
