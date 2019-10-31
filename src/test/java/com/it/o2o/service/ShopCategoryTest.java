package com.it.o2o.service;

import com.it.o2o.BaseTest;
import com.it.o2o.entity.ShopCategory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;

/**
 * @author wjh
 * @create 2019-06-07-21:46
 */
public class ShopCategoryTest extends BaseTest {
    @Autowired
    ShopCategoryService shopCategoryService;

    @Test
    public void test() throws IOException {
        List<ShopCategory> shopCategory = shopCategoryService.getShopCategory(null);
        for (ShopCategory category : shopCategory) {
            System.out.println(category);
        }
    }
}
