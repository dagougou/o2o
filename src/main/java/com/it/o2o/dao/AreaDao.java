package com.it.o2o.dao;

import com.it.o2o.entity.Area;

import java.util.List;

/**
 * AreaDao
 * @author wjh
 * @create 2019-05-30-16:58
 */
public interface AreaDao {
    /**
     * 列出区域列表
     * @return List<Area>
     */
    public List<Area> getAreas();
}
