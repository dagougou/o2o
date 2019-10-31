package com.it.o2o.service;

import com.it.o2o.dto.WechatAuthExecution;
import com.it.o2o.entity.WechatAuth;


public interface WechatAuthService {

    /**
     * 通过openId查询微信账号
     *
     * @param openId
     * @return
     */
    WechatAuth getWechatAuthByOpenId(String openId);

    /**
     * 注册本平台微信账号
     *
     * @param wechatAuth
     * @return
     * @throws RuntimeException
     */
    WechatAuthExecution register(WechatAuth wechatAuth) throws RuntimeException;

}
