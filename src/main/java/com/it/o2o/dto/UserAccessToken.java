package com.it.o2o.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author wjh
 * @create 2019-06-07-12:41
 */
public class UserAccessToken {
    //获取到凭证
    @JsonProperty("access_token")
    private String accessToken;
    //获取有效时间 单位秒
    @JsonProperty("expires_in")
    private String expiresIn;
    //表示更新令牌，用来获取下一次的访问令牌
    @JsonProperty("refresh_token")
    private String refreshToken;
    //该用户在此公众微信号下的身份标识，具有唯一性
    @JsonProperty("openid")
    private String openId;
    //表示权限范围
    @JsonProperty("scope")
    private String scope;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(String expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }
}
