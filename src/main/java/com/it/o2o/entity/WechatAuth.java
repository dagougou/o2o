package com.it.o2o.entity;

import java.util.Date;

/**
 * 微信账号关联实体类
 *
 * @author wjh
 * @create 2019-05-29-22:00
 */
public class WechatAuth {
    //id
    private Long wechatAuthId;
    //openId
    private String openId;
    //创建时间
    private Date createTime;
    //关联用户的实体类
    private UserInfo userInfo;

    public Long getWechatAuthId() {
        return wechatAuthId;
    }

    public void setWechatAuthId(Long wechatAuthId) {
        this.wechatAuthId = wechatAuthId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
}
