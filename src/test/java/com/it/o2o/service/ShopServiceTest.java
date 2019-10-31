package com.it.o2o.service;

import com.it.o2o.BaseTest;
import com.it.o2o.dto.ShopExecution;
import com.it.o2o.entity.Area;
import com.it.o2o.entity.Shop;
import com.it.o2o.entity.ShopCategory;
import com.it.o2o.entity.UserInfo;
import com.it.o2o.enums.ShopStateEnum;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.util.Date;

/**
 * @author wjh
 * @create 2019-05-31-17:00
 */
public class ShopServiceTest extends BaseTest {
//    @Autowired
//    private ShopService shopService;
//
//    @Test
//    public void testShopAdd() {
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
//        shop.setPriority(1);
//        shop.setShopAddr("test1");
//        shop.setShopName("测试店铺1");
//        shop.setShopDesc("test1");
//        shop.setPhone("test1");
//        shop.setCreateTime(new Date());
//        shop.setLastEditTime(new Date());
//        shop.setEnableStatus(ShopStateEnum.CHECK.getState());
//        shop.setAdvice("审核中");
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
//        ShopExecution shops = shopService.getShops(shop, 1, 3);
//
//        System.out.println(shops.getCount());
//        System.out.println(shops.getShops().size());
//        for (Shop shopsShop : shops.getShops()) {
//            System.out.println(shopsShop.getShopName());
//        }
//
//    }
}
