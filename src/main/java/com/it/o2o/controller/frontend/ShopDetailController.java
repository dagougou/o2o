package com.it.o2o.controller.frontend;

import com.it.o2o.dto.ProductExecution;
import com.it.o2o.entity.Product;
import com.it.o2o.entity.ProductCategory;
import com.it.o2o.entity.Shop;
import com.it.o2o.service.ProductCategoryService;
import com.it.o2o.service.ProductService;
import com.it.o2o.service.ShopService;
import com.it.o2o.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 店铺详情
 *
 * @author wjh
 * @create 2019-06-06-17:27
 */
@Controller
@RequestMapping("/frontend")
public class ShopDetailController {
    @Autowired
    private ShopService shopService;
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductCategoryService productCategoryService;

    /**
     * 获取店铺信息以及店铺下面的商品类别
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/listshopdetailpageinfo", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> listShopDetailPageInfo(
            HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        long shopId = HttpServletRequestUtil.getLong(request, "shopId");
        Shop shop = null;
        List<ProductCategory> productCategoryList = null;
        if (shopId != -1) {
            shop = shopService.getShopById(shopId);
            productCategoryList = productCategoryService.getByShopId(shopId);
            modelMap.put("shop", shop);
            modelMap.put("productCategoryList", productCategoryList);
            modelMap.put("success", true);
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty shopId");
        }
        return modelMap;
    }

    /**
     * 依据查询条件获取商品分页信息
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/listproductsbyshop", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> listProductsByShop(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        //获取页码
        int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
        //获取每一页显示的条数
        int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
        //获取店铺id
        long shopId = HttpServletRequestUtil.getLong(request, "shopId");
        //空值判断
        if ((pageIndex > -1) && (pageSize > -1) && (shopId > -1)) {
            //尝试获取商品类别id
            long productCategoryId = HttpServletRequestUtil.getLong(request, "productCategoryId");
            //尝试获取模糊查询的商品名字
            String productName = HttpServletRequestUtil.getString(request, "productName");
            //组合查询条件
            Product productCondition = compactProductCondition4Search(shopId, productCategoryId, productName);
            //根据查询条件获取相关商品列表信息
            ProductExecution pe = productService.getProductList(productCondition, pageIndex, pageSize);
            modelMap.put("productList", pe.getProductList());
            modelMap.put("count", pe.getCount());
            modelMap.put("success", true);
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty pageSize or pageIndex or shopId");
        }
        return modelMap;
    }

    /**
     * 组合查询条件
     *
     * @param shopId
     * @param productCategoryId
     * @param productName
     * @return
     */
    private Product compactProductCondition4Search(long shopId, long productCategoryId, String productName) {
        Product productCondition = new Product();
        Shop shop = new Shop();
        shop.setShopId(shopId);
        productCondition.setShop(shop);
        if (productCategoryId != -1L) {
            ProductCategory productCategory = new ProductCategory();
            productCategory.setProductCategoryId(productCategoryId);
            productCondition.setProductCategory(productCategory);
        }
        if (productName != null) {
            productCondition.setProductName(productName);
        }
        productCondition.setEnableStatus(1);
        return productCondition;
    }
}
