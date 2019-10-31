package com.it.o2o.util;

import com.google.code.kaptcha.Constants;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wjh
 * @create 2019-06-01-12:35
 */
public class CodeUtil {
    public static boolean checkVerifyCode(HttpServletRequest request) {
        //生成的验证码值
        String verifyCodeExpercted = (String) request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
        //前台输入的验证码值
        String verifyCodeActual = HttpServletRequestUtil.getString(request, "verifyCodeActual");
        //不区分大小写
        if(verifyCodeActual == null || !verifyCodeActual.equalsIgnoreCase(verifyCodeExpercted)){
            return false;
        }else {
            return true;
        }
    }
}
