package com.it.o2o.controller.ShopAdmin;

import com.it.o2o.dto.ProductCategoryExecution;
import com.it.o2o.dto.Result;
import com.it.o2o.entity.ProductCategory;
import com.it.o2o.entity.Shop;
import com.it.o2o.enums.ProductCategoryStateEnum;
import com.it.o2o.exceptions.ProductCategoryOperationException;
import com.it.o2o.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wjh
 * @create 2019-06-02-17:58
 */
@Controller
@RequestMapping("/shopadmin")
public class ProductCategoryManagemntController {
    @Autowired
    ProductCategoryService productCategoryService;

    @RequestMapping(value = "/getproductcategorylist", method = RequestMethod.GET)
    @ResponseBody
    private Result<List<ProductCategory>> getProductCategoryList(HttpServletRequest request) {
        Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
        List<ProductCategory> list = null;
        if (currentShop != null && currentShop.getShopId() > 0) {
            list = productCategoryService.getProductCategoryList(currentShop.getShopId());
            return new Result<List<ProductCategory>>(true, list);
        } else {
            ProductCategoryStateEnum ps = ProductCategoryStateEnum.NOT_HAVE_PRIVILEGE;
            return new Result<List<ProductCategory>>(false, ps.getStateInfo(), ps.getState());
        }
    }

    /**
     * 处理批量添加的请求
     *
     * @param productCategoryList
     * @param request
     * @return
     */
    @RequestMapping(value = "/addproductcategorys", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> addProductCateorys(@RequestBody List<ProductCategory> productCategoryList, HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
        if (productCategoryList != null && productCategoryList.size() > 0) {
            //给所有的待添加的商品类别加上当前店铺的id
            for (ProductCategory pc : productCategoryList) {
                pc.setShopId(currentShop.getShopId());
                pc.setCreateTime(new Date());
            }
            try {
                ProductCategoryExecution pe = productCategoryService.batchAddProductCategory(productCategoryList);
                if (pe.getState() == ProductCategoryStateEnum.SUCCESS.getState()) {
                    //操作成功
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", pe.getStateInfo());
                }
            } catch (ProductCategoryOperationException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.getMessage());
                return modelMap;
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请至少输入一个商品类别");
        }
        return modelMap;
    }

    @RequestMapping(value = "/removeproductcateory", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> removeProductCateory(Long productCategoryId, HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        if (productCategoryId != null && productCategoryId > 0) {
            try {
                Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
                ProductCategoryExecution pe = productCategoryService.deleteProductCategory(productCategoryId, currentShop.getShopId());
                if (pe.getState() == ProductCategoryStateEnum.SUCCESS.getState()) {
                    //操作成功
                    modelMap.put("success", true);
                } else {
                    //操作失败
                    modelMap.put("success", false);
                    modelMap.put("errMsg", pe.getStateInfo());
                }
            } catch (RuntimeException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.getMessage());
                return modelMap;
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请至少选择一个商品类别");
        }
        return modelMap;
    }
}
