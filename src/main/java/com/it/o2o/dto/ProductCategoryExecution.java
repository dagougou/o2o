package com.it.o2o.dto;

import com.it.o2o.entity.ProductCategory;
import com.it.o2o.enums.ProductCategoryStateEnum;

import java.util.List;

/**
 * @author wjh
 * @create 2019-06-02-19:06
 */
public class ProductCategoryExecution {
    //结果状态
    private int state;
    //状态标识(状态描述)
    private String stateInfo;

    private List<ProductCategory> productCategoryList;

    public ProductCategoryExecution() {
    }

    //操作失败的构造器
    public ProductCategoryExecution(ProductCategoryStateEnum ps) {
        this.state = ps.getState();
        this.stateInfo = ps.getStateInfo();
    }

    //操作成功的构造器

    public ProductCategoryExecution(ProductCategoryStateEnum ps,List<ProductCategory> productCategoryList){
        this.state = ps.getState();
        this.stateInfo = ps.getStateInfo();
        this.productCategoryList = productCategoryList;
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

    public List<ProductCategory> getProductCategoryList() {
        return productCategoryList;
    }

    public void setProductCategoryList(List<ProductCategory> productCategoryList) {
        this.productCategoryList = productCategoryList;
    }
}
