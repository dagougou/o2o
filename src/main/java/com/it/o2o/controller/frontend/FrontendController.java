package com.it.o2o.controller.frontend;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author wjh
 * @create 2019-06-05-17:34
 */
@Controller
@RequestMapping("/frontend")
public class FrontendController {
    /**
     * 前往前端主页面
     *
     * @return
     */
    @RequestMapping(value = "index", method = RequestMethod.GET)
    private String toIndexPage() {
        return "frontend/index";
    }

    /**
     * 前往前端主页面
     *
     * @return
     */
    @RequestMapping(value = "shoplist", method = RequestMethod.GET)
    private String toShopListPage() {
        return "frontend/shoplist";
    }

    /**
     * 前往店铺详情页面
     *
     * @return
     */
    @RequestMapping(value = "shopdetail", method = RequestMethod.GET)
    private String toShopDetailPage() {
        return "frontend/shopdetail";
    }

    /**
     * 前往商品详情页面
     * @return
     */
    @RequestMapping(value = "/productdetail", method = RequestMethod.GET)
    private String showProductDetail() {
        return "frontend/productdetail";
    }

}
