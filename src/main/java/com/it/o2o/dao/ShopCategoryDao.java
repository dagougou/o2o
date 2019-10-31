package com.it.o2o.dao;

import com.it.o2o.entity.ShopCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wjh
 * @create 2019-05-31-22:02
 */
public interface ShopCategoryDao {
    public List<ShopCategory> getShopCategory(@Param("shopCategoryCondition") ShopCategory shopCategoryCondition);
}
