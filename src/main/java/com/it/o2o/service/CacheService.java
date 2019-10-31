package com.it.o2o.service;

/**
 * @author wjh
 * @create 2019-06-07-23:57
 */
public interface CacheService {
    /**
     * 根据key前缀删除匹配该模式下的所有key—value
     * @param keyPrefix
     */
    void removeFromCache(String keyPrefix);
}
