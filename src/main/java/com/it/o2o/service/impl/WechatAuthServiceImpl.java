package com.it.o2o.service.impl;

import java.util.Date;

import ch.qos.logback.core.util.FileUtil;
import com.it.o2o.dao.UserInfoDao;
import com.it.o2o.dao.WechatAuthDao;
import com.it.o2o.dto.WechatAuthExecution;
import com.it.o2o.entity.UserInfo;
import com.it.o2o.entity.WechatAuth;
import com.it.o2o.enums.WechatAuthStateEnum;
import com.it.o2o.service.WechatAuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;


@Service
public class WechatAuthServiceImpl implements WechatAuthService {
    private static Logger log = LoggerFactory
            .getLogger(WechatAuthServiceImpl.class);
    @Autowired
    private WechatAuthDao wechatAuthDao;
    @Autowired
    private UserInfoDao userInfoDao;

    /**
     * 返回查询到的微信账号
     *
     * @param openId
     * @return
     */
    @Override
    public WechatAuth getWechatAuthByOpenId(String openId) {
        return wechatAuthDao.queryWechatInfoByOpenId(openId);
    }


    /**
     * 注册微信账号
     *
     * @param wechatAuth
     * @return
     * @throws RuntimeException
     */
    @Override
    @Transactional
    public WechatAuthExecution register(WechatAuth wechatAuth) throws RuntimeException {
        //空值判断
        if (wechatAuth == null || wechatAuth.getOpenId() == null) {
            return new WechatAuthExecution(WechatAuthStateEnum.NULL_AUTH_INFO);
        }
        try {
            //设置创建时间
            wechatAuth.setCreateTime(new Date());
            //如果微信账号中夹带着用户信息并且用户id为空，则认为该用户是第一次使用凭条（是通过微信登陆）
            if (wechatAuth.getUserInfo() != null && wechatAuth.getUserInfo().getUserId() == null) {
                //自动创建用户信息
                try {
                    wechatAuth.getUserInfo().setCreateTime(new Date());
                    wechatAuth.getUserInfo().setLastEditTime(new Date());
                    wechatAuth.getUserInfo().setEnableStatus(1);
                    UserInfo userInfo = wechatAuth.getUserInfo();
                    //添加用户信息
                    int effectedNum = userInfoDao.insertUserInfo(userInfo);
                    wechatAuth.setUserInfo(userInfo);
                    if (effectedNum <= 0) {
                        throw new RuntimeException("添加用户信息失败");
                    }
                } catch (Exception e) {
                    log.debug("insertPersonInfo error:" + e.toString());
                    throw new RuntimeException("insertPersonInfo error: " + e.getMessage());
                }
            }
            //创建专属于平台的微信账号
            int effectedNum = wechatAuthDao.insertWechatAuth(wechatAuth);
            if (effectedNum <= 0) {
                throw new RuntimeException("帐号创建失败");
            } else {
                return new WechatAuthExecution(WechatAuthStateEnum.SUCCESS,
                        wechatAuth);
            }
        } catch (Exception e) {
            log.debug("insertWechatAuth error:" + e.toString());
            throw new RuntimeException("insertWechatAuth error: "+ e.getMessage());
        }
    }
}
