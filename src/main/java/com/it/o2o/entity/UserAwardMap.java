package com.it.o2o.entity;

import java.util.Date;

//顾客奖品映射
public class UserAwardMap {

    private Long userAwardId;
    //创建时间
    private Date createTime;
    //使用状态
    private Integer usedStatus;
    //消耗的积分
    private Integer point;
    //顾客信息实体类
    private UserInfo user;
    //奖品实体类
    private Award award;
    //店铺信息实体类
    private Shop shop;
    //操作员信息实体类
    private UserInfo operator;

    public Long getUserAwardId() {
        return userAwardId;
    }

    public void setUserAwardId(Long userAwardId) {
        this.userAwardId = userAwardId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getUsedStatus() {
        return usedStatus;
    }

    public void setUsedStatus(Integer usedStatus) {
        this.usedStatus = usedStatus;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public UserInfo getUser() {
        return user;
    }

    public void setUser(UserInfo user) {
        this.user = user;
    }

    public Award getAward() {
        return award;
    }

    public void setAward(Award award) {
        this.award = award;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public UserInfo getOperator() {
        return operator;
    }

    public void setOperator(UserInfo operator) {
        this.operator = operator;
    }
}
