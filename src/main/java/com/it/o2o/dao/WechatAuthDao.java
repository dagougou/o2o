package com.it.o2o.dao;

import com.it.o2o.entity.WechatAuth;

public interface WechatAuthDao {
    /**
     * 根据openid查询微信账号
     *
     * @param openId
     * @return
     */
    WechatAuth queryWechatInfoByOpenId(String openId);

    /**
     * 添加微信账号
     *
     * @param wechatAuth
     * @return
     */
    int insertWechatAuth(WechatAuth wechatAuth);

    /**
     * @param wechatAuthId
     * @return
     */
    int deleteWechatAuth(Long wechatAuthId);
}
