package com.it.o2o.controller.wechat;


import com.it.o2o.dto.UserAccessToken;
import com.it.o2o.dto.WechatAuthExecution;
import com.it.o2o.dto.WechatUser;
import com.it.o2o.entity.UserInfo;
import com.it.o2o.entity.WechatAuth;
import com.it.o2o.enums.WechatAuthStateEnum;
import com.it.o2o.service.UserInfoService;
import com.it.o2o.service.WechatAuthService;
import com.it.o2o.util.wechat.WechatUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Controller
@RequestMapping("wechatlogin")
/**
 * 从微信菜单点击后调用的接口，可以在url里增加参数（role_type）来表明是从商家还是从玩家按钮进来的，依次区分登陆后跳转不同的页面
 * 玩家会跳转到index.html页面
 * 商家如果没有注册，会跳转到注册页面，否则跳转到任务管理页面
 * 如果是商家的授权用户登陆，会跳到授权店铺的任务管理页面
 * https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx559b35d2c3852065&redirect_uri=http://www.yt526.top/o2o/wechatlogin/logincheck&role_type=1&response_type=code&scope=snsapi_userinfo&state=1#wechat_redirect
 * @author wjh
 *
 */
public class WeiXinLoginController {
    private static Logger log = LoggerFactory.getLogger(WeiXinLoginController.class);
    //    @Resource
//    private PersonInfoService personInfoService;
//    @Resource
//    private WechatAuthService WechatAuthService;
//
//    @Resource
//    private ShopService shopService;
//
//    @Resource
//    private ShopAuthMapService shopAuthMapService;
    @Autowired
    UserInfoService userInfoService;
    @Autowired
    WechatAuthService wechatAuthService;

    //表示前端展示系统
    private static final String FRONTEND = "1";
    //表示店家管理系统
    private static final String SHOPEND = "2";

    /**
     * 微信回调到此方法
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/logincheck", method = {RequestMethod.GET})
    public String doGet(HttpServletRequest request, HttpServletResponse response) {
        log.debug("weixin login get...");
        //获取code，用来获取access_token
        String code = request.getParameter("code");
        //
        String roleType = request.getParameter("state");
        log.debug("weixin login code:" + code);
        WechatUser user = null;
        String openId = null;
        WechatAuth auth = null;
        if (code != null) {
            UserAccessToken token;
            try {
                //通过code 获取access_token
                token = WechatUtil.getUserAccessToken(code);
                log.debug("weixin login token:" + token);
                //通过token拿到accessToken
                String accessToken = token.getAccessToken();
                openId = token.getOpenId();
                user = WechatUtil.getUserInfo(accessToken, openId);
                log.debug("weixin login user:" + user.toString());
                request.getSession().setAttribute("openId", openId);
                //查看后台有没有这个微信账号
                auth = wechatAuthService.getWechatAuthByOpenId(openId);
            } catch (Exception e) {
                log.error(e.toString());
                e.printStackTrace();
            }
        }
        if (auth == null) {
            UserInfo userInfo = WechatUtil.getUserInfoFromRequset(user);
            auth = new WechatAuth();
            auth.setOpenId(openId);
            if (FRONTEND.equals(roleType)) {
                userInfo.setUserType(1);
            } else {
                userInfo.setUserType(2);
            }
            auth.setUserInfo(userInfo);
            WechatAuthExecution we = wechatAuthService.register(auth);
            if (we.getState() != WechatAuthStateEnum.SUCCESS.getState()) {
                return null;
            } else {
                userInfo = userInfoService.getUserinfoById(auth.getUserInfo().getUserId());
                request.getSession().setAttribute("user", userInfo);
            }
        }
        //判断是店家还是用户(公众号里的2个按钮)
        if (FRONTEND.equals(roleType)) {
            return "frontend/index";
        } else {
            return "shopadmin/shoplist";
        }
    }
//		log.debug("weixin login get...");
//		String code = request.getParameter("code");
//		String roleType = request.getParameter("state");
//		log.debug("weixin login code:" + code);
//		WechatAuth auth = null;
//		WeiXinUser user = null;
//		String openId = null;
//		if (null != code) {
//			UserAccessToken token;
//			try {
//				token = WeiXinUserUtil.getUserAccessToken(code);
//				log.debug("weixin login token:" + token.toString());
//				String accessToken = token.getAccessToken();
//				openId = token.getOpenId();
//				user = WeiXinUserUtil.getUserInfo(accessToken, openId);
//				log.debug("weixin login user:" + user.toString());
//				request.getSession().setAttribute("openId", openId);
//				auth = WechatAuthService.getWechatAuthByOpenId(openId);
//			} catch (IOException e) {
//				log.error("error in getUserAccessToken or getUserInfo or findByOpenId: "
//						+ e.toString());
//				e.printStackTrace();
//			}
//		}
//		log.debug("weixin login success.");
//		log.debug("login role_type:" + roleType);
//		if (FRONTEND.equals(roleType)) {
//			PersonInfo personInfo = WeiXinUserUtil
//					.getPersonInfoFromRequest(user);
//			if (auth == null) {
//				personInfo.setCustomerFlag(1);
//				auth = new WechatAuth();
//				auth.setOpenId(openId);
//				auth.setPersonInfo(personInfo);
//				WechatAuthExecution we = WechatAuthService.register(auth, null);
//				if (we.getState() != WechatAuthStateEnum.SUCCESS.getState()) {
//					return null;
//				}
//			}
//			personInfo = personInfoService.getPersonInfoById(auth.getUserId());
//			request.getSession().setAttribute("user", personInfo);
//			return "frontend/index";
//		}
//		if (SHOPEND.equals(roleType)) {
//			PersonInfo personInfo = null;
//			WechatAuthExecution we = null;
//			if (auth == null) {
//				auth = new WechatAuth();
//				auth.setOpenId(openId);
//				personInfo = WeiXinUserUtil.getPersonInfoFromRequest(user);
//				personInfo.setShopOwnerFlag(1);
//				auth.setPersonInfo(personInfo);
//				we = WechatAuthService.register(auth, null);
//				if (we.getState() != WechatAuthStateEnum.SUCCESS.getState()) {
//					return null;
//				}
//			}
//			personInfo = personInfoService.getPersonInfoById(auth.getUserId());
//			request.getSession().setAttribute("user", personInfo);
//			ShopExecution se = shopService.getByEmployeeId(personInfo
//					.getUserId());
//			request.getSession().setAttribute("user", personInfo);
//			if (se.getShopList() == null || se.getShopList().size() <= 0) {
//				return "shop/registershop";
//			} else {
//				request.getSession().setAttribute("shopList", se.getShopList());
//				return "shop/shoplist";
//			}
//		}
//		return null;
//	}
}
