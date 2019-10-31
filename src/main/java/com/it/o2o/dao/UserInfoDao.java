package com.it.o2o.dao;

import java.util.List;

import com.it.o2o.entity.UserInfo;
import org.apache.ibatis.annotations.Param;


public interface UserInfoDao {

    /**
     * @param userInfoCondition
     * @param rowIndex
     * @param pageSize
     * @return
     */
    List<UserInfo> queryUserInfoList(
            @Param("personInfoCondition") UserInfo userInfoCondition,
            @Param("rowIndex") int rowIndex, @Param("pageSize") int pageSize);

    /**
     * @param userInfoCondition
     * @return
     */
    int queryUserInfoCount(
            @Param("personInfoCondition") UserInfo userInfoCondition);

    /**
     * 通过id查询用户信息
     *
     * @param userId
     * @return
     */
    UserInfo queryUserInfoById(long userId);

    /**
     * 添加用户信息
     *
     * @return
     */
    int insertUserInfo(UserInfo userInfo);

    /**
     * @return
     */
    int updateUserInfo(UserInfo userInfo);

    /**
     * @return
     */
    int deleteUserInfo(long userId);
}
