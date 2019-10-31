package com.it.o2o.service.impl;

import com.it.o2o.dao.ProductDao;
import com.it.o2o.dao.ProductImgDao;
import com.it.o2o.dto.ProductExecution;
import com.it.o2o.entity.Product;
import com.it.o2o.entity.ProductImg;
import com.it.o2o.enums.ProductStateEnum;
import com.it.o2o.exceptions.ProductOperationException;
import com.it.o2o.service.ProductService;
import com.it.o2o.util.ImgUtil;
import com.it.o2o.util.PageCalcuator;
import com.it.o2o.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author wjh
 * @create 2019-06-02-23:06
 */
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductDao productDao;
    @Autowired
    private ProductImgDao productImgDao;

    @Override
    @Transactional
    public ProductExecution addProduct(Product product, CommonsMultipartFile thumbnail, List<CommonsMultipartFile> productImgList) throws ProductOperationException {
        //空值判断
        if (product != null && product.getShop() != null && product.getShop().getShopId() != null) {
            //给商品设置默认属性
            product.setCreateTime(new Date());
            product.setLastEditTime(new Date());
            //默认为伤架状态
            product.setEnableStatus(1);
            //处理缩略图
            if (thumbnail != null) {
                addThumbnail(product, thumbnail);
            }
            try {
                //创建商品信息
                int effectedNum = productDao.insertProduct(product);
                if (effectedNum <= 0) {
                    throw new ProductOperationException("创建商品失败");
                }
            } catch (Exception e) {
                throw new ProductOperationException("创建商品失败" + e.getMessage());
            }
            //如果商品详情图不为空
            if (productImgList != null && productImgList.size() > 0) {
                addProductImgList(product, productImgList);
            }
            return new ProductExecution(ProductStateEnum.SUCCESS, product);
        } else {
            //传参为空返回空值错误
            return new ProductExecution(ProductStateEnum.EMPTY);
        }
    }

    /**
     * 根据id查询商品
     *
     * @param id
     * @return
     */
    public Product getProductById(Long id) {
        return productDao.getProductById(id);
    }

    /**
     * 按照条件查询出商品列表
     *
     * @param productCondition
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @Override
    public ProductExecution getProductList(Product productCondition, int pageIndex, int pageSize) {
        int rowIndex = PageCalcuator.calcuatorRowIndex(pageIndex, pageSize);
        List<Product> productList = productDao.queryProductList(productCondition, rowIndex, pageSize);
        int count = productDao.queryProductCount(productCondition);
        ProductExecution pe = new ProductExecution();
        pe.setProductList(productList);
        pe.setCount(count);
        return pe;
    }

    /**
     * 更新商品信息，如果传入图片，将删除以前的图片，在重新存放新图片
     *
     * @param product
     * @param thumbnail
     * @param productImgList
     * @return
     * @throws ProductOperationException
     */
    @Override
    @Transactional
    public ProductExecution updateProduct(Product product, CommonsMultipartFile thumbnail, List<CommonsMultipartFile> productImgList) throws ProductOperationException {
        //空值判断
        if (product != null && product.getShop() != null && product.getShop().getShopId() != null) {
            product.setLastEditTime(new Date());
            //若商品的缩略图不为空，删除原来的图片，在添加新图片
            if (thumbnail != null) {
                //先查询出现在的商品
                Product tempProduct = productDao.getProductById(product.getProductId());
                //删除原来的图片
                if (tempProduct.getImgAddr() != null) {
                    ImgUtil.deleteFileOrPath(tempProduct.getImgAddr());
                }
                addThumbnail(product, thumbnail);
            }
            //如果有新的商品详情图，删除原来的图片，在添加新图片
            if (productImgList != null && productImgList.size() > 0) {
                //删除所有详情图
                deleteProductImgList(product.getProductId());
                //添加新的详情图
                addProductImgList(product, productImgList);
            }
            try {
                //更新商品信息
                int effectedNum = productDao.updateProduct(product);
                if (effectedNum <= 0) {
                    throw new ProductOperationException("商品更新失败");
                }
                return new ProductExecution(ProductStateEnum.SUCCESS, product);
            } catch (Exception e) {
                throw new ProductOperationException("商品更新失败");
            }
        } else {
            return new ProductExecution(ProductStateEnum.EMPTY);
        }
    }

    /**
     * 添加详情图片
     *
     * @param product
     */
    private void addProductImgList(Product product, List<CommonsMultipartFile> productImgListFile) {
        String dest = PathUtil.getShopImagePath(product.getShop().getShopId());
        List<ProductImg> productImgList = new ArrayList<>();
        for (CommonsMultipartFile productImgFile : productImgListFile) {
            String imgAddr = ImgUtil.generateNormalImg(productImgFile, dest);
            //创建一个ProductImg对象，赋值后，加入到list集合
            ProductImg productImg = new ProductImg();
            productImg.setImgAddr(imgAddr);
            productImg.setProductId(product.getProductId());
            productImg.setCreateTime(new Date());
            productImgList.add(productImg);
        }
        //如果确实有图片添加，就执行批量添加操作
        if (productImgList.size() > 0) {
            try {
                int effectNum = productImgDao.batchInsetProductImg(productImgList);
                if (effectNum <= 0) {
                    throw new ProductOperationException("创建商品详情图片失败");
                }
            } catch (Exception e) {
                throw new ProductOperationException("创建商品详情图片失败" + e.getMessage());
            }
        }
    }


    /**
     * 添加缩略图
     *
     * @param product
     * @param thumbnail
     */
    private void addThumbnail(Product product, CommonsMultipartFile thumbnail) {
        //获取shop图片目录的相对值路径
        String dest = PathUtil.getShopImagePath(product.getShop().getShopId());
        String shopImgAddr = ImgUtil.generateThumbnail(thumbnail, dest);
        product.setImgAddr(shopImgAddr);
    }

    /**
     * 删除某个商品下的所有详情图
     *
     * @param productId
     */
    private void deleteProductImgList(long productId) {
        List<ProductImg> productImgList = productImgDao.queryProductImgList(productId);
        for (ProductImg productImg : productImgList) {
            ImgUtil.deleteFileOrPath(productImg.getImgAddr());
        }
        productImgDao.deleteProductImgByProductId(productId);
    }

}
