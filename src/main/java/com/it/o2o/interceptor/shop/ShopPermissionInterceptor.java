package com.it.o2o.interceptor.shop;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.it.o2o.entity.Shop;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;


public class ShopPermissionInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
        @SuppressWarnings("unchecked")
        List<Shop> shopList = (List<Shop>) request.getSession().getAttribute("shoplist");
        if (currentShop != null && shopList != null) {
            for (Shop shop : shopList) {
                if (shop.getShopId() == currentShop.getShopId()) {
                    return true;
                }
            }
        }
        return false;
    }

}
