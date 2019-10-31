package com.it.o2o.dao;

import com.it.o2o.BaseTest;
import com.it.o2o.entity.Area;
import com.it.o2o.entity.Shop;
import com.it.o2o.entity.ShopCategory;
import com.it.o2o.entity.UserInfo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * @author wjh
 * @create 2019-05-30-20:57
 */
public class ShopDaoTest extends BaseTest {
//    @Autowired
//    private ShopDao shopDao;
//
//    @Test
//    public void testGetShopById() {
//        Long shopId = 35l;
//        Shop shopById = shopDao.getShopById(shopId);
//        System.out.println(shopById.getClass());
//    }
//
//
//    @Test
//    public void testInsertShop() {
//        Shop shop = new Shop();
//        UserInfo userInfo = new UserInfo();
//        ShopCategory shopCategory = new ShopCategory();
//        Area area = new Area();
//
//        userInfo.setUserId(1L);
//        area.setAreaId(7);
//        shopCategory.setShopCategoryId(1L);
//
//        shop.setOwner(userInfo);
//        shop.setShopCategory(shopCategory);
//        shop.setArea(area);
//
//        shop.setShopImg("test");
//        shop.setPriority(1);
//        shop.setShopAddr("test");
//        shop.setShopName("测试店铺");
//        shop.setShopDesc("test");
//        shop.setPhone("test");
//        shop.setCreateTime(new Date());
//        shop.setLastEditTime(new Date());
//        shop.setEnableStatus(1);
//        shop.setAdvice("审核中");
//
//        int i = shopDao.insertShop(shop);
//        System.out.println(i);
//    }
//
//    @Test
//    public void testUpdateShop() {
//        Shop shop = new Shop();
//        shop.setShopId(1L);
//        UserInfo userInfo = new UserInfo();
//        ShopCategory shopCategory = new ShopCategory();
//        Area area = new Area();
//
//        userInfo.setUserId(1L);
//        area.setAreaId(7);
//        shopCategory.setShopCategoryId(1L);
//
//        shop.setOwner(userInfo);
//        shop.setShopCategory(shopCategory);
//        shop.setArea(area);
//
//        shop.setShopImg("测试");
//        shop.setPriority(1);
//        shop.setShopAddr("测试");
//        shop.setShopName("测试店铺");
//        shop.setShopDesc("测试");
//        shop.setPhone("测试");
//        shop.setCreateTime(new Date());
//        shop.setLastEditTime(new Date());
//        shop.setEnableStatus(1);
//        shop.setAdvice("审核中");
//
//        int i = shopDao.updateShop(shop);
//        System.out.println(i);
//    }
//
//    @Test
//    public void testGetShops() {
//        Shop shop = new Shop();
//        UserInfo userInfo = new UserInfo();
//        userInfo.setUserId(1L);
//
//        shop.setOwner(userInfo);
//        shop.setShopName("s");
//
//        List<Shop> shops = shopDao.getShops(shop, 1, 5);
//        int shopsCount = shopDao.getShopsCount(shop);
//        System.out.println(shops.size());
//        System.out.println(shopsCount);
//
//    }
}
