package com.it.o2o.dto;

import com.it.o2o.entity.Shop;
import com.it.o2o.enums.ShopStateEnum;

import java.util.List;

/**
 * 存储店铺的信息和状态码
 *
 * @author wjh
 * @create 2019-05-30-22:32
 */
public class ShopExecution {
    //结果状态
    private int state;

    //状态标识(状态描述)
    private String stateInfo;

    //店铺数量
    private int count;

    //操作的店铺(增删改时用到)
    private Shop shop;

    //店铺列表(查询店铺列表时使用)
    private List<Shop> shops;

    public ShopExecution() {
    }

    /**
     * 店铺操作失败时使用的构造器
     *
     * @param sateEnum 店铺状态的枚举类型
     */
    public ShopExecution(ShopStateEnum sateEnum) {
        this.state = sateEnum.getState();
        this.stateInfo = sateEnum.getStateInfo();
    }

    /**
     * 店铺操作成功时的构造器，需要返回单个的shop对象
     */
    public ShopExecution(ShopStateEnum sateEnum, Shop shop) {
        this.state = sateEnum.getState();
        this.stateInfo = sateEnum.getStateInfo();
        this.shop = shop;
    }

    /**
     * 店铺操作成功时的构造器，返回店铺的集合
     */
    public ShopExecution(ShopStateEnum sateEnum, List<Shop> shops) {
        this.state = sateEnum.getState();
        this.stateInfo = sateEnum.getStateInfo();
        this.shops = shops;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public List<Shop> getShops() {
        return shops;
    }

    public void setShops(List<Shop> shops) {
        this.shops = shops;
    }
}
