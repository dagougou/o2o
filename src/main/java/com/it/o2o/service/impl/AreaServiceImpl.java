package com.it.o2o.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.it.o2o.cache.JedisUtil;
import com.it.o2o.dao.AreaDao;
import com.it.o2o.entity.Area;
import com.it.o2o.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wjh
 * @create 2019-05-30-17:37
 */
@Service
public class AreaServiceImpl implements AreaService {
    @Autowired
    private AreaDao areaDao;
    @Autowired
    private JedisUtil.Keys keys;
    @Autowired
    private JedisUtil.Strings strings;




    @Override
    @Transactional
    public List<Area> getAreas() {
        String key = AREALISTKEY;
        List<Area> areaList = null;
        ObjectMapper mapper = new ObjectMapper();
        //如果缓存里没有数据，添加缓存
        try {
            if (!keys.exists(key)) {
                areaList = areaDao.getAreas();
                String jsonString = null;
                jsonString = mapper.writeValueAsString(areaList);
                //添加缓存
                strings.set(key, jsonString);
            } else {
                String jsonString = strings.get(key);
                JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, Area.class);
                areaList = mapper.readValue(jsonString, javaType);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.toString());
        }
        return areaList;
    }
}
