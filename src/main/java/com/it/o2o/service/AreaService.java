package com.it.o2o.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.it.o2o.entity.Area;

import java.io.IOException;
import java.util.List;

/**
 * @author wjh
 * @create 2019-05-30-17:36
 */
public interface AreaService {
    public static final String AREALISTKEY = "arealist";
    List<Area> getAreas() throws IOException;
}
