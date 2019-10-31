package com.it.o2o.cache;

import org.junit.Test;
import redis.clients.jedis.Jedis;

/**
 * @author wjh
 * @create 2019-06-07-20:18
 */
public class TestRedis {
    @Test
    public void testRedis(){
        Jedis jedis = new Jedis("127.0.0.1",6379);
        String ping = jedis.ping();
        System.out.println(ping);
    }
}
