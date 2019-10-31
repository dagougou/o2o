package com.it.o2o.util;

import net.coobird.thumbnailator.Thumbnails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * 图片工具
 *
 * @author wjh
 * @create 2019-05-30-21:42
 */
public class ImgUtil {
    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyyMMddHHmmss");
    private static final Random RANDOM = new Random();
    private static Logger logger = LoggerFactory.getLogger(ImgUtil.class);

    /**
     * 处理缩略图，返回新生成的图片地址
     *
     * @param thumbnail  处理图片
     * @param targetAttr 文件存放路径
     * @return String
     */
    public static String generateThumbnail(CommonsMultipartFile thumbnail, String targetAttr) {
        //随机生成不重复的名字
        String realFileName = getRandomFileName();
        //获取文件扩展名
        String extension = getFileExtension(thumbnail);
        //创建路径
        makeDirPath(targetAttr);
        //得到新的文件名字
        String relativeAddr = targetAttr + realFileName + extension;
        //与项目根路径组成存放用户上传图片的位置
        File dest = new File(PathUtil.getImgBasePath() + relativeAddr);
        logger.info("图片存放位置：" + PathUtil.getImgBasePath() + relativeAddr);
        try {
            //创建缩略图
            Thumbnails.of(thumbnail.getInputStream()).size(200, 200).outputQuality(0.8f).toFile(dest);
        } catch (Exception e) {
            logger.error(e.toString());
            e.printStackTrace();
        }
        return relativeAddr;
    }


    /**
     * 处理详情图，返回新生成的图片地址
     *
     * @param thumbnail  处理图片
     * @param targetAttr 文件存放路径
     * @return String
     */
    public static String generateNormalImg(CommonsMultipartFile thumbnail, String targetAttr) {
        //随机生成不重复的名字
        String realFileName = getRandomFileName();
        //获取文件扩展名
        String extension = getFileExtension(thumbnail);
        //创建路径
        makeDirPath(targetAttr);
        //得到新的文件名字
        String relativeAddr = targetAttr + realFileName + extension;
        //与项目根路径组成存放用户上传图片的位置
        File dest = new File(PathUtil.getImgBasePath() + relativeAddr);
        logger.info("图片存放位置：" + PathUtil.getImgBasePath() + relativeAddr);
        try {
            //创建缩略图
            Thumbnails.of(thumbnail.getInputStream()).size(337, 640).outputQuality(0.9f).toFile(dest);
        } catch (Exception e) {
            logger.error(e.toString());
            e.printStackTrace();
        }
        return relativeAddr;
    }
    /**
     * 获取输入文件流的扩展名 jpg，png....
     *
     * @param thumbnail
     * @return
     */
    private static String getFileExtension(CommonsMultipartFile thumbnail) {
        String orignalFileName = thumbnail.getOriginalFilename();
        return orignalFileName.substring(orignalFileName.lastIndexOf("."));
    }

    /**
     * 生成随机文件名：当前年月日时分秒+五位随机数（为了在实际项目中防止文件同名而进行的处理）
     *
     * @return String
     */
    private static String getRandomFileName() {
        //获取随机5位数
        int rannum = RANDOM.nextInt(89999) + 10000;
        String nowTime = SIMPLE_DATE_FORMAT.format(new Date());
        return nowTime + rannum;
    }

    /**
     * 创建目标路径所涉及到的目录
     *
     * @param targetAttr
     */
    private static void makeDirPath(String targetAttr) {
        String realFileParentPath = PathUtil.getImgBasePath() + targetAttr;
        File dirPath = new File(realFileParentPath);
        if (!dirPath.exists()) {
            dirPath.mkdirs();
        }
    }

    /**
     * 删除图片或路径
     * @param storePath
     */
    public static void deleteFileOrPath(String storePath) {
        File fileOrPath = new File(PathUtil.getImgBasePath() + storePath);
        if (fileOrPath.exists()) {
            if (fileOrPath.isDirectory()) {
                File[] files = fileOrPath.listFiles();
                for (int i = 0; i < files.length; i++) {
                    files[i].delete();
                }
            }
            fileOrPath.delete();
        }
    }
}
