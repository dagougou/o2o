package com.it.o2o.util;

/**
 * 处理分页
 *
 * @author wjh
 * @create 2019-06-02-11:16
 */
public class PageCalcuator {
    public static int calcuatorRowIndex(int pageIndex, int pageSize) {
        return (pageIndex > 0) ? (pageIndex - 1) * pageSize : 0;
    }
}
