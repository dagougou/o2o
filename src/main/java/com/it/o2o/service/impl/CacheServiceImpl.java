package com.it.o2o.service.impl;

import com.it.o2o.cache.JedisUtil;
import com.it.o2o.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * @author wjh
 * @create 2019-06-07-23:58
 */
@Service
public class CacheServiceImpl implements CacheService {
    @Autowired
    private JedisUtil.Keys keys;

    @Override
    public void removeFromCache(String keyPrefix) {
        Set<String> keySet = this.keys.keys(keyPrefix + "*");
        for (String key : keySet) {
            keys.del(key);
        }
    }
}
