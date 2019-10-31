package com.it.o2o.service;

import com.it.o2o.dto.ProductCategoryExecution;
import com.it.o2o.entity.ProductCategory;
import org.junit.platform.commons.util.PreconditionViolationException;

import java.util.List;

/**
 * @author wjh
 * @create 2019-06-02-17:49
 */
public interface ProductCategoryService {
    /**
     * 查询指定的某个店铺下的所有商品类别
     *
     * @param shopId
     * @return
     */
    List<ProductCategory> getProductCategoryList(long shopId);

    /**
     * 批量添加商品类别
     *
     * @param productCategoryList
     * @return
     * @throws PreconditionViolationException
     */
    ProductCategoryExecution batchAddProductCategory(List<ProductCategory> productCategoryList) throws PreconditionViolationException;

    /**
     * 将此类别下的所有商品里的类别id置为空，再删除该商品类别
     * @param productCategoryId
     * @param shopId
     * @return
     * @throws PreconditionViolationException
     */
    ProductCategoryExecution deleteProductCategory(long productCategoryId, long shopId) throws PreconditionViolationException;

    List<ProductCategory> getByShopId(Long shopId);
}
