package com.it.o2o.exceptions;

/**
 * @author wjh
 * @create 2019-06-02-19:14
 */
public class ProductCategoryOperationException extends RuntimeException {
    public ProductCategoryOperationException(String errorMsg) {
        super(errorMsg);
    }
}
