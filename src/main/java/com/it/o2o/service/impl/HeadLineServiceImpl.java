package com.it.o2o.service.impl;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.it.o2o.cache.JedisUtil;
import com.it.o2o.dao.HeadLineDao;
import com.it.o2o.entity.HeadLine;
import com.it.o2o.service.HeadLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wjh
 * @create 2019-06-05-16:51
 */
@Service
public class HeadLineServiceImpl implements HeadLineService {
    @Autowired
    HeadLineDao headLineDao;
    @Autowired
    private JedisUtil.Strings jedisStrings;
    @Autowired
    private JedisUtil.Keys jedisKeys;
    @Override
    public List<HeadLine> getHeadLineList(HeadLine headLineCondition) throws IOException {
        List<HeadLine> headLineList = null;
        ObjectMapper mapper = new ObjectMapper();
        String key = HLLISTKEY;
        if (headLineCondition.getEnableStatus() != null) {
            key = key + "_" + headLineCondition.getEnableStatus();
        }
        if (!jedisKeys.exists(key)) {
            headLineList = headLineDao.getHeadLine(headLineCondition);
            String jsonString = mapper.writeValueAsString(headLineList);
            jedisStrings.set(key, jsonString);
        } else {
            String jsonString = jedisStrings.get(key);
            JavaType javaType = mapper.getTypeFactory()
                    .constructParametricType(ArrayList.class, HeadLine.class);
            headLineList = mapper.readValue(jsonString, javaType);
        }
        return headLineList;
    }
}
