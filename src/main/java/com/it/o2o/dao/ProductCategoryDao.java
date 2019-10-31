package com.it.o2o.dao;

import com.it.o2o.entity.ProductCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wjh
 * @create 2019-06-02-17:36
 */
public interface ProductCategoryDao {
    /**
     * 通过店铺的id，查询出该店铺的所有商品类别
     *
     * @param ShopId
     * @return
     */
    List<ProductCategory> getProductCategoryList(long ShopId);

    /**
     * 批量添加商品类别
     *
     * @param productCategoryList
     * @return
     */
    int batchInsertProductCategory(List<ProductCategory> productCategoryList);

    /**
     * 删除指定的商品类别
     * @param productCategoryId
     * @param shopId
     * @return
     */
    int deleteProductCategory(@Param("productCategoryId") long productCategoryId,@Param("shopId") long shopId);

    /**
     * 根据shopId查询出商品类别的列表
     * @param shopId
     * @return
     */
    List<ProductCategory> queryByShopId(Long shopId);
}
