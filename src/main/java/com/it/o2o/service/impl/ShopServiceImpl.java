package com.it.o2o.service.impl;

import com.it.o2o.dao.ShopDao;
import com.it.o2o.dto.ShopExecution;
import com.it.o2o.entity.Shop;
import com.it.o2o.enums.ShopStateEnum;
import com.it.o2o.exceptions.ShopOperationException;
import com.it.o2o.service.ShopService;
import com.it.o2o.util.ImgUtil;
import com.it.o2o.util.PageCalcuator;
import com.it.o2o.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.util.Date;
import java.util.List;

/**
 * @author wjh
 * @create 2019-05-31-16:16
 */
@Service
public class ShopServiceImpl implements ShopService {
    @Autowired
    private ShopDao shopDao;

    /**
     * 按要求查询出所有满足条件的店铺信息
     * @param shopCondition 带条件的shop对象
     * @param pageIndex 前端传来的页码
     * @param pageSize 每一页的数据条数
     * @return ShopExecution
     */
    @Override
    public ShopExecution getShops(Shop shopCondition, int pageIndex, int pageSize) {
        int rowIndex = PageCalcuator.calcuatorRowIndex(pageIndex, pageSize);
        List<Shop> shopList = shopDao.getShops(shopCondition, rowIndex, pageSize);
        int count = shopDao.getShopsCount(shopCondition);
        ShopExecution se = new ShopExecution();
        if (shopList != null) {
            se.setShops(shopList);
            se.setCount(count);
        } else {
            se.setState(ShopStateEnum.INNER_ERROR.getState());
        }
        return se;
    }

    /**
     * 根据id获取店铺信息
     *
     * @param shopId
     * @return
     */
    @Override
    public Shop getShopById(Long shopId) {
        return shopDao.getShopById(shopId);
    }

    /**
     * 更新店铺
     *
     * @param shop
     * @param shopImg
     * @return
     */
    @Override
    public ShopExecution updateShop(Shop shop, CommonsMultipartFile shopImg) {
        if (shop == null || shop.getShopId() == null) {
            return new ShopExecution(ShopStateEnum.NULL_SHOP);
        } else {
            //判断是否需要处理图片
            try {
                if (shopImg != null) {
                    //先删除以前的图片
                    Shop tempShop = shopDao.getShopById(shop.getShopId());
                    String imgPath = tempShop.getShopImg();
                    if (imgPath != null) {
                        ImgUtil.deleteFileOrPath(imgPath);
                    }
                    //处理新传入的图片
                    addShopImg(shop, shopImg);
                }
                //更新店铺信息
                shop.setLastEditTime(new Date());
                int effectedNum = shopDao.updateShop(shop);
                if (effectedNum <= 0) {
                    return new ShopExecution(ShopStateEnum.INNER_ERROR);
                } else {
                    shop = shopDao.getShopById(shop.getShopId());
                    return new ShopExecution(ShopStateEnum.SUCCESS, shop);

                }
            } catch (Exception e) {
                throw new ShopOperationException("更新店铺信息失败");
            }
        }
    }

    /**
     * 注册店铺
     *
     * @param shop
     * @param shopImg
     * @return
     */
    @Override
    @Transactional
    public ShopExecution addShop(Shop shop, CommonsMultipartFile shopImg) {
        //店铺非空验证
        if (shop == null) {
            //返回店铺信息为空的错误
            return new ShopExecution(ShopStateEnum.NULL_SHOP);
        }
        //注册店铺信息
        try {
            //设置店铺状态码为0
            shop.setEnableStatus(ShopStateEnum.CHECK.getState());
            //设置店铺状态信息为审核中
            shop.setAdvice(ShopStateEnum.CHECK.getStateInfo());
            //设置创建时间
            shop.setCreateTime(new Date());
            //设置最新的更新时间
            shop.setLastEditTime(new Date());
            //插入店铺信息,返回值为影响行数
            int effectedNum = shopDao.insertShop(shop);

            if (effectedNum <= 0) {
                //插入失败，抛出异常，使事务回滚
                throw new ShopOperationException("店铺创建失败");
            } else {
                if (shopImg != null) {
                    //存储图片
                    try {
                        addShopImg(shop, shopImg);
                    } catch (Exception e) {
                        throw new ShopOperationException("add_shopImg_error:" + e.getMessage());
                    }
                    //更新店铺的图片地址
                    effectedNum = shopDao.updateShop(shop);
                    if (effectedNum <= 0) {
                        //更新图片失败，抛出异常，使事务回滚
                        throw new ShopOperationException("更新图片地址失败");
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("add_shop_error:" + e.getMessage());
        }
        return new ShopExecution(ShopStateEnum.CHECK, shop);
    }

    private void addShopImg(Shop shop, CommonsMultipartFile shopImg) {
        //获取shop图片目录的相对值路径
        String dest = PathUtil.getShopImagePath(shop.getShopId());
        String shopImgAddr = ImgUtil.generateThumbnail(shopImg, dest);
        shop.setShopImg(shopImgAddr);
    }
}
