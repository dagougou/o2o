package com.it.o2o.dao;

import com.it.o2o.entity.Shop;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wjh
 * @create 2019-05-30-20:41
 */
public interface ShopDao {
    /**
     * 获取店铺总数
     *
     * @param shopCondition
     * @return
     */
    int getShopsCount(@Param("shopCondition") Shop shopCondition);

    /**
     * 查询店家的店铺列表，可以模糊查询
     *
     * @param shopCondition
     * @param rowIndex      从第几行开始查询
     * @param pageSize      需要查询的行数
     * @return
     */
    List<Shop> getShops(@Param("shopCondition") Shop shopCondition,
                        @Param("rowIndex") int rowIndex,
                        @Param("pageSize") int pageSize);

    /**
     * 查询单个店铺
     *
     * @param shopId
     * @return
     */
    Shop getShopById(Long shopId);

    /**
     * 新增店铺
     *
     * @param shop
     * @return
     */
    int insertShop(Shop shop);

    /**
     * 更新店铺
     *
     * @param shop
     * @return
     */
    int updateShop(Shop shop);
}
