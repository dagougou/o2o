package com.it.o2o.service;

import com.it.o2o.dto.ShopExecution;
import com.it.o2o.entity.Shop;
import org.springframework.web.multipart.commons.CommonsMultipartFile;


/**
 * @author wjh
 * @create 2019-05-31-16:15
 */
public interface ShopService {
    /**
     *  查询符合条件的店铺
     */
    public ShopExecution getShops(Shop shopCondition, int pageIndex, int pageSize);

    /**
     * 根据店铺id查询店铺，并返回
     *
     * @param shopId
     * @return
     */
    public Shop getShopById(Long shopId);

    /**
     * 更新店铺信息
     *
     * @param shop
     * @param shopImg
     * @return
     */
    public ShopExecution updateShop(Shop shop, CommonsMultipartFile shopImg);

    /**
     * 实现店铺的注册，包括图片的上传
     *
     * @param shop
     * @param shopImg
     * @return
     */
    public ShopExecution addShop(Shop shop, CommonsMultipartFile shopImg);

}
