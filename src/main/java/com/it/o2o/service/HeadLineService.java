package com.it.o2o.service;

import com.it.o2o.entity.HeadLine;

import java.io.IOException;
import java.util.List;

/**
 * @author wjh
 * @create 2019-06-05-16:49
 */
public interface HeadLineService {

    public static final String HLLISTKEY = "headlinelist";

    /**
     * 根据传入的条件返回指定的头条列表
     *
     * @param headLineCondition
     * @return
     */
    List<HeadLine> getHeadLineList(HeadLine headLineCondition) throws IOException;
}
