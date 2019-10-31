package com.it.o2o.controller.ShopAdmin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 转发html
 *
 * @author wjh
 * @create 2019-05-31-20:48
 */
@Controller
@RequestMapping(value = "/shopadmin", method = RequestMethod.GET)
public class ShopAdminController {

    /**
     * 前往shop_operation.html
     */
    @RequestMapping("/shopoperation")
    public String toShopOperationPage() {
        return "/shop/shop_operation";
    }


    /**
     * 前往shop_operation.html
     */
    @RequestMapping("/shoplist")
    public String toShopListPage() {
        return "/shop/shop_list";
    }

    /**
     * 前往shop_manage.html
     *
     * @return
     */
    @RequestMapping("/shopmanagement")
    public String toShopManagementPage() {
        return "/shop/shop_manage";
    }

    /**
     * 前往productcategory_manage.html
     *
     * @return
     */
    @RequestMapping("/productcategorymanagement")
    public String toProductCategoryManagementPage() {
        return "/shop/productcategory_manage";
    }

    @RequestMapping("/productoperation")
    public String toProductOperationPage() {
        return "/shop/product_edit";
    }

    @RequestMapping("/productlist")
    public String toProductListPage() {
        return "/shop/product_list";
    }
}
