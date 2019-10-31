package com.it.o2o.service;

import com.it.o2o.entity.UserInfo;
import org.springframework.stereotype.Service;

/**
 * @author wjh
 * @create 2019-06-07-17:31
 */
public interface UserInfoService {

    /**
     * 根据用户id查询用户信息
     *
     * @param userId
     * @return
     */
    UserInfo getUserinfoById(Long userId);
}
