package com.it.o2o.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.it.o2o.entity.ShopCategory;
import org.apache.ibatis.annotations.Param;

import java.io.IOException;
import java.util.List;

/**
 * @author wjh
 * @create 2019-05-31-22:24
 */
public interface ShopCategoryService {
    public static final String SCLISTKEY = "shopcategorylist";

    /**
     * 根据查询条件获取ShopCategory列表
     *
     * @param shopCategoryCondition
     * @return
     */
    public List<ShopCategory> getShopCategory(ShopCategory shopCategoryCondition) throws IOException;
}
