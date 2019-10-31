package com.it.o2o.interceptor.shop;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.it.o2o.entity.UserInfo;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * 店家登陆拦截器
 */
public class ShopLoginInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Object userObj = request.getSession().getAttribute("user");
        if (userObj != null) {
            UserInfo user = (UserInfo) userObj;
            if (user != null && user.getUserId() != null
                    && user.getUserId() > 0 && user.getEnableStatus() == 1
                    && user.getEnableStatus() == 1)
                return true;
        }
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<script>");
        out.println("window.open ('" + request.getContextPath()
                + "/local/login?usertype=2','_self')");
        out.println("</script>");
        out.println("</html>");
        return false;
    }
}
