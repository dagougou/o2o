package com.it.o2o.util;

import javax.servlet.http.HttpServletRequest;

/**
 * 获取请求参数的工具类
 *
 * @author wjh
 * @create 2019-05-31-17:38
 */
public class HttpServletRequestUtil {

    /**
     * 将key转化为整形
     *
     * @param request
     * @param key
     * @return
     */
    public static int getInt(HttpServletRequest request, String key) {
        try {
            return Integer.decode(request.getParameter(key));
        } catch (Exception e) {
            return -1;
        }
    }

    /**
     * 将key转化为浮点型
     *
     * @param request
     * @param key
     * @return
     */
    public static long getLong(HttpServletRequest request, String key) {
        try {
            return Long.valueOf(request.getParameter(key));
        } catch (Exception e) {
            return -1L;
        }
    }

    /**
     * 将key转化为布尔
     *
     * @param request
     * @param key
     * @return
     */
    public static boolean getBoolean(HttpServletRequest request, String key) {
        try {
            return Boolean.valueOf(request.getParameter(key));
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 将key转化为double
     *
     * @param request
     * @param key
     * @return
     */
    public static double getDouble(HttpServletRequest request, String key) {
        try {
            return Double.valueOf(request.getParameter(key));
        } catch (Exception e) {
            return -1d;
        }
    }

    /**
     * 将key转化为String
     * @param request
     * @param key
     * @return
     */
    public static String getString(HttpServletRequest request, String key) {
        try {
            String result = request.getParameter(key);
            if(result != null){
                result = result.trim();
            }

            if("".equals(result)){
                result = null;
            }

            return result;
        } catch (Exception e) {
            return null;
        }
    }

}
