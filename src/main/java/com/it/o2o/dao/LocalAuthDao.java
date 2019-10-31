package com.it.o2o.dao;

import java.util.Date;

import com.it.o2o.entity.LocalAuth;
import org.apache.ibatis.annotations.Param;


public interface LocalAuthDao {

    /**
     * @param userName
     * @param password
     * @return
     */
    LocalAuth queryLocalByUserNameAndPwd(@Param("userName") String userName, @Param("password") String password);

    /**
     * @param userId
     * @return
     */
    LocalAuth queryLocalByUserId(@Param("userId") long userId);

    /**
     * @param localAuth
     * @return
     */
    int insertLocalAuth(LocalAuth localAuth);

    /**
     * @return
     */
    int updateLocalAuth(@Param("userId") Long userId,
                        @Param("userName") String userName,
                        @Param("password") String password,
                        @Param("newPassword") String newPassword,
                        @Param("lastEditTime") Date lastEditTime);
}
