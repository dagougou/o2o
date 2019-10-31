package com.it.o2o.util;


/**
 * 路径工具类
 *
 * @author wjh
 * @create 2019-05-30-21:52
 */
public class PathUtil {
    private static String separator = System.getProperty("file.separator");

    /**
     * 返回项目的根路径
     *
     * @return String
     */
    public static String getImgBasePath() {
        String os = System.getProperty("os.name");
        String basePath = "";
        if (os.contains("Win")) {
            //windows路径
            basePath = "F:/o2o/projectDev/images/";
        } else {
            //其他操作系统路径
            basePath = "o2o/home/images/";
        }
        basePath = basePath.replace("/", separator);
        return basePath;
    }

    /**
     * 返回店铺图片的字路径
     *
     * @param shopId 店铺id
     * @return String
     */
    public static String getShopImagePath(long shopId) {
        String imagePath = "/upload/item/shop/" + shopId + "/";
        return imagePath.replace("/", separator);
    }
}
