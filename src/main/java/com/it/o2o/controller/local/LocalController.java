package com.it.o2o.controller.local;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author wjh
 * @create 2019-06-08-21:05
 */
@Controller
@RequestMapping(value = "/local")
public class LocalController {

    /**
     * 前往绑定账号页面
     *
     * @return
     */
    @RequestMapping(value = "/accountbing", method = RequestMethod.GET)
    private String toAccountbindPage() {
        return "/local/customerbind";
    }

    /**
     * 前往登陆页面
     *
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    private String toLoginPage() {
        return "/local/login";
    }


    /**
     * 前往修改密码页面
     *
     * @return
     */
    @RequestMapping(value = "/changepsw", method = RequestMethod.GET)
    private String toChangepwsPage() {
        return "/local/changepsw";
    }
}
