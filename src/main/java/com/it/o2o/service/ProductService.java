package com.it.o2o.service;

import com.it.o2o.dto.ProductExecution;
import com.it.o2o.entity.Product;
import com.it.o2o.exceptions.ProductOperationException;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.util.List;

/**
 * @author wjh
 * @create 2019-06-02-22:57
 */
public interface ProductService {
    /**
     * 添加商品信息，图片信息
     *
     * @return
     * @throws ProductOperationException
     */
    ProductExecution addProduct(Product product, CommonsMultipartFile thumbnail, List<CommonsMultipartFile> productImgList) throws ProductOperationException;

    /**
     * 根据id查询商品
     *
     * @param id
     * @return
     */
    public Product getProductById(Long id);

    /**
     * 按条件查询商品
     *
     * @param productCondition
     * @param pageIndex
     * @param pageSize
     * @return
     */
    ProductExecution getProductList(Product productCondition, int pageIndex, int pageSize);

    /**
     * 更新商品信息
     *
     * @param product
     * @param thumbnail
     * @param productImgList
     * @return
     */
    ProductExecution updateProduct(Product product, CommonsMultipartFile thumbnail, List<CommonsMultipartFile> productImgList) throws ProductOperationException;
}



