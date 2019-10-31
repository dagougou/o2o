package com.it.o2o.util.wechat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.it.o2o.dto.UserAccessToken;
import com.it.o2o.dto.WechatUser;
import com.it.o2o.entity.UserInfo;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.security.SecureRandom;

/**
 * @author wjh
 * @create 2019-06-07-12:54
 */
public class WechatUtil {
    private static Logger logger = LoggerFactory.getLogger(WechatUtil.class);


    /**
     * 获取UserAccessToken实体类
     *
     * @param code
     * @return
     * @throws IOException
     */
    public static UserAccessToken getUserAccessToken(String code) throws IOException {
        //测试账号里的appID
        String appId = "wx559b35d2c3852065";
        logger.debug("appID:" + appId);
        //测试账号里的appsecret
        String appsecret = "d51a456b356230c25168f18761df81a4";
        logger.debug("appsecret:" + appsecret);
        //根据传入的code，拼接处访问微信定义好的接口的url
        // String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + appId + "&secret=" + appsecret;
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + appId + "&secret=" + appsecret + "&code=" + code + "&grant_type=authorization_code";
        //向url发送请求获取token_json字符串
        String tokenStr = httpsRequest(url, "GET", null);
        logger.debug("userAccessToken:" + tokenStr);
        UserAccessToken token = new UserAccessToken();
        ObjectMapper mapper = new ObjectMapper();
        try {
            //将json转为对象
            token = mapper.readValue(tokenStr, UserAccessToken.class);
        } catch (Exception e) {
            logger.error("获取用户accessToken失败：" + e.getMessage());
            e.printStackTrace();
        }
        if (token == null) {
            logger.error("获取用户accessToken失败");
            return null;
        }
        return token;
    }

    /**
     * 发起https请求，并获取结果
     *
     * @return
     */
    private static String httpsRequest(String requestUrl, String requestMethod, String outputStr) {
        StringBuffer buffer = new StringBuffer();
        try {
            // 创建SSLContext对象，并使用我们指定的信任管理器初始化
            TrustManager[] tm = {new MyX509TrustManager()};
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            // 从上述SSLContext对象中得到SSLSocketFactory对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();

            URL url = new URL(requestUrl);
            HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
            httpUrlConn.setSSLSocketFactory(ssf);

            httpUrlConn.setDoOutput(true);
            httpUrlConn.setDoInput(true);
            httpUrlConn.setUseCaches(false);
            // 设置请求方式（GET/POST）
            httpUrlConn.setRequestMethod(requestMethod);

            if ("GET".equalsIgnoreCase(requestMethod))
                httpUrlConn.connect();

            // 当有数据需要提交时
            if (null != outputStr) {
                OutputStream outputStream = httpUrlConn.getOutputStream();
                // 注意编码格式，防止中文乱码
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }

            // 将返回的输入流转换成字符串
            InputStream inputStream = httpUrlConn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            bufferedReader.close();
            inputStreamReader.close();
            // 释放资源
            inputStream.close();
            inputStream = null;
            httpUrlConn.disconnect();
            logger.debug("https buffer:" + buffer.toString());
        } catch (Exception e) {
            logger.error("weix server error");
        }
        return buffer.toString();
    }


    public static WechatUser getUserInfo(String accessToken, String openId) {
        String url = "https://api.weixin.qq.com/sns/userinfo?access_token="
                + accessToken + "&openid=" + openId + "&lang=zh_CN";
        String jsonStr = WechatUtil.httpsRequest(url, "GET", null);
        JSONObject jsonObject = JSONObject.fromObject(jsonStr);
        WechatUser user = new WechatUser();
        String openid = jsonObject.getString("openid");
        if (openid == null) {
            logger.debug("获取用户信息失败。");
            return null;
        }
        user.setOpenId(openid);
        user.setNickName(jsonObject.getString("nickname"));
        user.setSex(jsonObject.getInt("sex"));
        user.setProvince(jsonObject.getString("province"));
        user.setCity(jsonObject.getString("city"));
        user.setCountry(jsonObject.getString("country"));
        user.setHeadimgurl(jsonObject.getString("headimgurl"));
        user.setPrivilege(null);
        // user.setUnionid(jsonObject.getString("unionid"));
        return user;
    }

    public static UserInfo getUserInfoFromRequset(WechatUser wechatUser) {
        UserInfo userInfo = new UserInfo();
        userInfo.setName(wechatUser.getNickName());
        userInfo.setGender(wechatUser.getSex() + "");
        userInfo.setProfileImg(wechatUser.getHeadimgurl());
        userInfo.setEnableStatus(1);
        return userInfo;
    }
}
