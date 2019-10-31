package com.it.o2o.exceptions;

/**
 * 店铺操纵异常
 * @author wjh
 * @create 2019-05-31-16:56
 */
public class ShopOperationException extends RuntimeException {
    public ShopOperationException(String errorMsg){
        super(errorMsg);
    }
}
