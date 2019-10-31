package com.it.o2o.entity;

import java.util.Date;

/**
 * @author wjh
 * @create 2019-06-09-21:45
 */
//顾客消费的商品映射
public class ProductSellDaily {
    private Date createTime;
    private Integer total;
    private Product product;
    private Shop shop;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }
}
