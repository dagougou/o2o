package com.it.o2o.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.it.o2o.cache.JedisUtil;
import com.it.o2o.dao.ShopCategoryDao;
import com.it.o2o.dao.ShopDao;
import com.it.o2o.entity.ShopCategory;
import com.it.o2o.service.ShopCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wjh
 * @create 2019-05-31-22:26
 */
@Service
public class ShopCategoryServiceImpl implements ShopCategoryService {
    @Autowired
    private ShopCategoryDao shopCategoryDao;
    @Autowired
    private JedisUtil.Strings jedisStrings;
    @Autowired
    private JedisUtil.Keys jedisKeys;


    @Override
    public List<ShopCategory> getShopCategory(ShopCategory shopCategoryCondition) throws IOException {
        String key = SCLISTKEY;
        List<ShopCategory> shopCategoryList = null;
        ObjectMapper mapper = new ObjectMapper();
        if (shopCategoryCondition == null) {
            key = key + "_allfirstlevel";
        } else if (shopCategoryCondition != null && shopCategoryCondition.getParent() != null && shopCategoryCondition.getParent().getShopCategoryId() != null) {
            key = key + "_parent" + shopCategoryCondition.getParent().getShopCategoryId();
        } else if (shopCategoryCondition != null) {
            key = key + "_allsecondlevel";
        }
        //判断key是否存在
        if (!jedisKeys.exists(key)) {
            shopCategoryList = shopCategoryDao.getShopCategory(shopCategoryCondition);
            String jsonString = mapper.writeValueAsString(shopCategoryList);
            jedisStrings.set(key, jsonString);
        } else {
            String jsonString = jedisStrings.get(key);
            JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, ShopCategory.class);
            shopCategoryList = mapper.readValue(jsonString, javaType);
        }
        return shopCategoryList;
    }
}
