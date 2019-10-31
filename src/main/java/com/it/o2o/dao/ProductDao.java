package com.it.o2o.dao;

import com.it.o2o.entity.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wjh
 * @create 2019-06-02-22:22
 */
public interface ProductDao {


    /**
     * 更新商品信息
     *
     * @param product
     * @return
     */
    int updateProduct(Product product);

    /**
     * 添加商品
     *
     * @param product
     * @return
     */
    int insertProduct(Product product);

    /**
     * 根据productId查询对应的商品
     *
     * @param productId
     * @return
     */
    Product getProductById(Long productId);

    /**
     * 查询商品列表并分页，可输入的条件有：商品名（模糊），商品状态，店铺Id,商品类别
     *
     * @param productCondition
     * @param pageSize
     * @return
     */
    List<Product> queryProductList(
            @Param("productCondition") Product productCondition,
            @Param("rowIndex") int rowIndex, @Param("pageSize") int pageSize);


    /**
     * 查询对应的商品总数
     *
     * @param productCondition
     * @return
     */
    int queryProductCount(@Param("productCondition") Product productCondition);

    /**
     * 删除商品类别之前，将商品类别ID置为空
     *
     * @param productCategoryId
     * @return
     */
    int updateProductCategoryToNull(long productCategoryId);
}
