package com.it.o2o.dao;

import com.it.o2o.entity.ProductImg;

import java.util.List;

/**
 * @author wjh
 * @create 2019-06-02-22:24
 */
public interface ProductImgDao {
    /**
     * 批量添加商品详情图片
     *
     * @param productImgList
     * @return
     */
    int batchInsetProductImg(List<ProductImg> productImgList);

    /**
     * 删除指定商品的详情图
     *
     * @param productId
     * @return
     */
    int deleteProductImgByProductId(long productId);

    List<ProductImg> queryProductImgList(long productId);
}
