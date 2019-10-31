package com.it.o2o.controller.local;

import com.it.o2o.dto.LocalAuthExecution;
import com.it.o2o.entity.LocalAuth;
import com.it.o2o.entity.UserInfo;
import com.it.o2o.enums.LocalAuthStateEnum;
import com.it.o2o.service.LocalAuthService;
import com.it.o2o.util.CodeUtil;
import com.it.o2o.util.HttpServletRequestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wjh
 * @create 2019-06-08-17:23
 */
@Controller
@RequestMapping(value = "/local", method = {RequestMethod.GET, RequestMethod.POST})
public class LocalAuthController {
    Logger log = LoggerFactory.getLogger(LocalAuthController.class);
    @Autowired
    private LocalAuthService localAuthService;

    /**
     * 将用户信息与平台账号绑定
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/bindlocalauth", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> bindLocalAuth(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        //验证码效验
        if (!CodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "验证码输入错误");
            return modelMap;
        }
        //获取输入的账号
        String userName = HttpServletRequestUtil.getString(request, "userName");
        //获取输入的密码
        String password = HttpServletRequestUtil.getString(request, "password");
        //从session中获取当前用户信息(用户一但通过微信登陆，就能获取到用户信息)
        UserInfo user = (UserInfo) request.getSession().getAttribute("user");
        //非空判断,要求账号密码和当前用户信息session非空
        if (user != null && userName != null && password != null && user.getUserId() != null) {
            //创建LocalAuth对象并赋值
            LocalAuth localAuth = new LocalAuth();
            localAuth.setUserName(userName);
            localAuth.setPassword(password);
            localAuth.setUserInfo(user);
            //进行账号绑定
            LocalAuthExecution le = localAuthService.bindLocalAuth(localAuth);
            if (le.getState() == LocalAuthStateEnum.SUCCESS.getState()) {
                modelMap.put("success", true);
            } else {
                modelMap.put("success", false);
                modelMap.put("errMsg", le.getStateInfo());
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "用户名密码不能为空");
        }
        return modelMap;
    }

    /**
     * 修改密码
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/changelocalpwd", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> changeLocalPwd(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        //验证码效验
        if (!CodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "验证码输入错误");
            return modelMap;
        }
        //获取账号
        String userName = HttpServletRequestUtil.getString(request, "userName");
        //获取原密码
        String password = HttpServletRequestUtil.getString(request, "password");
        //获取新密码
        String newPassword = HttpServletRequestUtil.getString(request, "newPassword");
        //从session中获取当前用户信息
        UserInfo user = (UserInfo) request.getSession().getAttribute("user");
        if (userName != null && password != null && newPassword != null && user != null && user.getUserId() != null
                && !password.equals(newPassword)) {
            try {
                //查看原来账号，判断是否输入一致
                LocalAuth localAuth = localAuthService.getLocalAuthByUserId(user.getUserId());
                if (localAuth == null || !localAuth.getUserName().equals(userName)) {
                    //输入不一致，直接返回
                    modelMap.put("success", false);
                    modelMap.put("errMsg", "输入账号非本次登陆账号");
                    return modelMap;
                }
                //修改平台账号用户密码
                LocalAuthExecution le = localAuthService.modifyLocalAuth(user.getUserId(), userName, password, newPassword);
                if (le.getState() == LocalAuthStateEnum.SUCCESS.getState()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", le.getStateInfo());
                }
            } catch (Exception e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.toString());
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请输入正确的账号信息");
        }
        return modelMap;
    }

    /**
     * 用户登陆效验
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "logincheck", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> loginCheck(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        //获取是否需要进行验证码效验的标识符
        boolean needVerify = HttpServletRequestUtil.getBoolean(request, "needVerify");
        if (needVerify && !CodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "验证码输入错误");
            return modelMap;
        }
        //获取输入的账号
        String userName = HttpServletRequestUtil.getString(request, "userName");
        //获取输入的密码
        String password = HttpServletRequestUtil.getString(request, "password");
        //非空效验
        if (userName != null && password != null) {
            //传入账号和密码去获取平台账号信息
            LocalAuth localAuth = localAuthService.getLocalAuthByUserNameAndPwd(userName, password);
            if (localAuth != null) {
                //若能获取到账号信息则登陆成功
                modelMap.put("success", true);
                //在session中设置用户信息
                request.getSession().setAttribute("user", localAuth.getUserInfo());
            } else {
                modelMap.put("success", false);
                modelMap.put("errMsg", "用户名或密码错误！");
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "输入账号或密码为空");
        }
        return modelMap;
    }

    /**
     * 登出系统,用户点击登出后将session销毁
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> logout(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        log.debug("用户退出系统");
        request.getSession().setAttribute("user", null);
        modelMap.put("success", true);
        return modelMap;
    }
}