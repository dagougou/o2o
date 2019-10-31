package com.it.o2o.dao;

import com.it.o2o.entity.HeadLine;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wjh
 * @create 2019-06-05-16:37
 */
public interface HeadLineDao {

    /**
     * 根据传入的条件查询头条
     *
     * @param headLineCondition
     * @return
     */
    List<HeadLine> getHeadLine(@Param("headLineCondition") HeadLine headLineCondition);

}
