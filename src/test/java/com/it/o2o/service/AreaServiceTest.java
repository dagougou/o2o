package com.it.o2o.service;

import com.it.o2o.BaseTest;
import com.it.o2o.entity.Area;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;

/**
 * @author wjh
 * @create 2019-05-30-17:41
 */
public class AreaServiceTest extends BaseTest {
    @Autowired
    private AreaService areaService;

    @Autowired
    private CacheService cacheService;

    @Test
    public void testGetAreas() throws IOException {
        testReomve();
        List<Area> areas = areaService.getAreas();
        for (Area area : areas) {
            System.out.println(area.getClass());
        }
    }

    public void testReomve() {
        cacheService.removeFromCache(areaService.AREALISTKEY);
    }
}
