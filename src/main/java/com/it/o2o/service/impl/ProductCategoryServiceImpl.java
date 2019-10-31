package com.it.o2o.service.impl;

import com.it.o2o.dao.ProductCategoryDao;
import com.it.o2o.dao.ProductDao;
import com.it.o2o.dto.ProductCategoryExecution;
import com.it.o2o.entity.ProductCategory;
import com.it.o2o.enums.ProductCategoryStateEnum;
import com.it.o2o.exceptions.ProductCategoryOperationException;
import com.it.o2o.service.ProductCategoryService;
import org.junit.platform.commons.util.PreconditionViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author wjh
 * @create 2019-06-02-17:50
 */
@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {
    @Autowired
    private ProductCategoryDao productCategoryDao;

    @Autowired
    private ProductDao productDao;

    Logger logger = LoggerFactory.getLogger(ProductCategoryServiceImpl.class);

    /**
     * 查询指定的某个店铺下的所有商品类别
     *
     * @param shopId
     * @return
     */
    @Override
    public List<ProductCategory> getProductCategoryList(long shopId) {
        return productCategoryDao.getProductCategoryList(shopId);
    }

    /**
     * 批量添加商品类别
     *
     * @param productCategoryList
     * @return
     * @throws PreconditionViolationException
     */
    @Override
    @Transactional
    public ProductCategoryExecution batchAddProductCategory(List<ProductCategory> productCategoryList) throws PreconditionViolationException {
        if (productCategoryList != null && productCategoryList.size() > 0) {
            try {
                int effectedNum = productCategoryDao.batchInsertProductCategory(productCategoryList);
                if (effectedNum <= 0) {
                    logger.error("店铺类别创建失败");
                    throw new ProductCategoryOperationException("店铺类别创建失败");
                } else {
                    return new ProductCategoryExecution(ProductCategoryStateEnum.SUCCESS);
                }

            } catch (Exception e) {
                logger.error(e.getMessage());
                throw new ProductCategoryOperationException("店铺类别创建失败:" + e.getMessage());
            }
        } else {
            return new ProductCategoryExecution(ProductCategoryStateEnum.EMPTY_LIST);
        }
    }

    /**
     * 将此类别下的所有商品里的类别id置为空，再删除该商品类别
     *
     * @param productCategoryId
     * @param shopId
     * @return
     * @throws PreconditionViolationException
     */
    @Override
    @Transactional
    public ProductCategoryExecution deleteProductCategory(long productCategoryId, long shopId) throws PreconditionViolationException {
        //将此类别下的所有商品里的类别id置为空
        try {
            int effectedNum = productDao.updateProductCategoryToNull(productCategoryId);
            if (effectedNum < 0) {
                logger.error("商品类别删除失败");
                throw new ProductCategoryOperationException("商品类别删除失败");
            }
        } catch (Exception e) {
            logger.error("商品类别删除失败");
            throw new ProductCategoryOperationException("商品类别删除失败:" + e.getMessage());
        }

        //删除该商品类别
        try {
            int effectedNum = productCategoryDao.deleteProductCategory(productCategoryId, shopId);
            if (effectedNum <= 0) {
                logger.error("商品类别删除失败");
                throw new ProductCategoryOperationException("商品类别删除失败");
            } else {
                return new ProductCategoryExecution(ProductCategoryStateEnum.SUCCESS);
            }
        } catch (ProductCategoryOperationException e) {
            logger.error(e.getMessage());
            throw new ProductCategoryOperationException("商品类别删除失败");
        }
    }

    @Override
    public List<ProductCategory> getByShopId(Long shopId) {
        return productCategoryDao.queryByShopId(shopId);
    }
}
