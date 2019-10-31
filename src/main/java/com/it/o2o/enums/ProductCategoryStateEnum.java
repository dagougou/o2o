package com.it.o2o.enums;

/**
 * @author wjh
 * @create 2019-06-02-18:13
 */
public enum ProductCategoryStateEnum {
    SUCCESS(1, "创建成功"),
    INNER_ERROR(-1001, "内部系统错误"),
    EMPTY_LIST(-1002,"添加数小于一"),
    NOT_HAVE_PRIVILEGE(-1003, "无操作权限");

    private int state;
    private String stateInfo;

    private ProductCategoryStateEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public static ProductCategoryStateEnum stateOf(int state) {
        for (ProductCategoryStateEnum sateEnum : values()) {
            if (sateEnum.getState() == state) {
                return sateEnum;
            }
        }
        return null;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }
}
