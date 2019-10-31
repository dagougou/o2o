package com.it.o2o.controller.ShopAdmin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.it.o2o.dto.ShopExecution;
import com.it.o2o.entity.Area;
import com.it.o2o.entity.Shop;
import com.it.o2o.entity.ShopCategory;
import com.it.o2o.entity.UserInfo;
import com.it.o2o.enums.ShopStateEnum;
import com.it.o2o.service.AreaService;
import com.it.o2o.service.ShopCategoryService;
import com.it.o2o.service.ShopService;
import com.it.o2o.util.CodeUtil;
import com.it.o2o.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wjh
 * @create 2019-05-31-17:29
 */
@Controller
@RequestMapping("/shopadmin")
public class ShopManagementController {
    @Autowired
    private ShopService shopService;
    @Autowired
    private ShopCategoryService shopCategoryService;
    @Autowired
    private AreaService areaService;

    @RequestMapping(value = "/getshopmanagementinfo", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> getShopManagementInfo(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        //从前端获取店铺id
        long shopId = HttpServletRequestUtil.getLong(request, "shopId");
        if (shopId <= 0) {
            //如果前端没有，尝试从session中获取店铺信息
            Object currentShopObj = request.getSession().getAttribute("currentShop");
            //还是没有,重定向到店铺列表页面
            if (currentShopObj == null) {
                modelMap.put("redirect", true);
                modelMap.put("url", "/o2o/shopadmin/shoplist");
            } else {
                //如果有
                Shop currentShop = (Shop) currentShopObj;
                modelMap.put("redirect", false);
                modelMap.put("shopId", currentShop.getShopId());
            }
        } else {
            //前端能获取到shopId
            Shop currentShop = new Shop();
            currentShop.setShopId(shopId);
            //将设置好的currentShop放到session中
            request.getSession().setAttribute("currentShop", currentShop);
            modelMap.put("redirect", false);
        }
        return modelMap;
    }

    /**
     * 获取登陆用户的所有店铺信息
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/getshops", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> getShops(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        UserInfo user = new UserInfo();
        user = (UserInfo) request.getSession().getAttribute("user");
        try {
            Shop shopCondition = new Shop();
            shopCondition.setOwner(user);
            ShopExecution se = shopService.getShops(shopCondition, 0, 100);
            modelMap.put("shopList", se.getShops());
            request.getSession().setAttribute("shoplist", se.getShops());
            modelMap.put("user", user);
            modelMap.put("success", true);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
        }
        return modelMap;
    }


    @RequestMapping(value = "/updateshop", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> updateShopInfo(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        if (!CodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "验证码输入错误");
            return modelMap;
        }
        //1.接受并转化相应的参数，包括店铺信息以及图片信息
        String shopStr = HttpServletRequestUtil.getString(request, "shopStr");
        ObjectMapper mapper = new ObjectMapper();
        Shop shop = null;
        try {
            //从请求参数中封装Shop对象
            shop = mapper.readValue(shopStr, Shop.class);
        } catch (Exception e) {
            //返回错误信息
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }
        //获取文件流
        CommonsMultipartFile shopImg = null;
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        if (commonsMultipartResolver.isMultipart(request)) {
            //将request装换成MultipartHttpServletRequest，可以从中获取文件流
            MultipartHttpServletRequest servletRequest = (MultipartHttpServletRequest) request;
            //shopImg的获取
            shopImg = (CommonsMultipartFile) servletRequest.getFile("shopImg");
        }
        //2.更新店铺信息
        if (shop != null && shop.getShopId() != null) {
            ShopExecution se;
            if (shopImg == null) {
                se = shopService.updateShop(shop, null);
            } else {
                se = shopService.updateShop(shop, shopImg);
            }

            if (se.getState() == ShopStateEnum.SUCCESS.getState()) {
                modelMap.put("success", true);
            } else {
                modelMap.put("success", false);
                modelMap.put("errMsg", se.getStateInfo());
            }
            return modelMap;
        } else {
            //返回错误信息
            modelMap.put("success", false);
            modelMap.put("errMsg", "请输入店铺ID");
            return modelMap;
        }
    }

    /**
     * 根据id，查询出对应的店铺信息
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/getshopbyid", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> getShopById(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        long shopId = HttpServletRequestUtil.getLong(request, "shopId");
        if (shopId > -1) {
            try {
                Shop shop = shopService.getShopById(shopId);
                List<Area> areaList = areaService.getAreas();
                modelMap.put("shop", shop);
                modelMap.put("areaList", areaList);
                modelMap.put("success", true);
            } catch (Exception e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.toString());
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "初始化店铺信息失败");
        }
        return modelMap;
    }

    /**
     * 处理注册店铺的请求
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/registershop", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> registerShop(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        if (!CodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "验证码输入错误");
            return modelMap;
        }
        //1.接受并转化相应的参数，包括店铺信息以及图片信息
        String shopStr = HttpServletRequestUtil.getString(request, "shopStr");
        ObjectMapper mapper = new ObjectMapper();
        Shop shop = null;
        try {
            //从请求参数中封装Shop对象
            shop = mapper.readValue(shopStr, Shop.class);
        } catch (Exception e) {
            //返回错误信息
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }
        //获取文件流
        CommonsMultipartFile shopImg = null;
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        if (commonsMultipartResolver.isMultipart(request)) {
            //将request装换成MultipartHttpServletRequest，可以从中获取文件流
            MultipartHttpServletRequest servletRequest = (MultipartHttpServletRequest) request;
            //shopImg的获取
            shopImg = (CommonsMultipartFile) servletRequest.getFile("shopImg");
        } else {
            //返回错误信息
            modelMap.put("success", false);
            modelMap.put("errMsg", "上传文件不能为空");
            return modelMap;
        }

        //2.注册店铺
        if (shop != null && shopImg != null) {
            UserInfo owner = (UserInfo) request.getSession().getAttribute("user");
            shop.setOwner(owner);
            ShopExecution se = shopService.addShop(shop, shopImg);
            if (se.getState() == ShopStateEnum.CHECK.getState()) {
                modelMap.put("success", true);
                //该用户可以操作的店铺列表
                @SuppressWarnings("unchecked")
                List<Shop> shopList = (List<Shop>) request.getSession().getAttribute("shoplist");
                //如果是第一个店铺
                if (shopList == null || shopList.size() == 0) {
                    shopList = new ArrayList<>();
                }
                shopList.add(se.getShop());
                request.getSession().setAttribute("shoplist", shopList);
            } else {
                modelMap.put("success", false);
                modelMap.put("errMsg", se.getStateInfo());
            }
            return modelMap;
        } else {
            //返回错误信息
            modelMap.put("success", false);
            modelMap.put("errMsg", "请输入店铺信息");
            return modelMap;
        }
    }

    /**
     * 处理获取区域信息和店铺类别信息的请求
     *
     * @return
     */
    @RequestMapping(value = "/getshopinitinfo", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> getShopInitInfo() throws IOException {
        Map<String, Object> modelMap = new HashMap<>();
        List<ShopCategory> shopCategoryList = shopCategoryService.getShopCategory(new ShopCategory());
        List<Area> areaList = areaService.getAreas();
        try {
            modelMap.put("shopCategoryList", shopCategoryList);
            modelMap.put("areaList", areaList);
            modelMap.put("success", true);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
        }
        return modelMap;
    }

}
