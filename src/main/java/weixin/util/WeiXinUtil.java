package weixin.util;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import weixin.entity.SNSUserInfo;
import weixin.entity.Token;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;

public class WeiXinUtil {

    protected static Logger log = LoggerFactory.getLogger(WeiXinUtil.class);

    /**
     * 获取网页授权凭证
     * @param code code作为换取access_token的票据，每次用户授权带上的code将不一样，code只能使用一次，5分钟未被使用自动过期
     * @return WeixinOauth2Token
     */
    public static Token getOauth2AccessToken(String code) {
        Token wat = null;
        // 拼接请求地址
        Oauth oauth = new Oauth();
        String requestUrl = oauth.accessTokenUrl(code);
        // 获取网页授权凭证
        JSONObject jsonObject = HttpClientUtil.httpsRequest1(requestUrl, "GET", null);
        if (null != jsonObject) {
            try {
                wat = new Token();
                wat.setAccessToken(jsonObject.getString("access_token"));
                wat.setExpiresIn(jsonObject.getIntValue("expires_in"));
                wat.setRefreshToken(jsonObject.getString("refresh_token"));
                wat.setOpenId(jsonObject.getString("openid"));
                wat.setScope(jsonObject.getString("scope"));
            } catch (Exception e) {
                wat = null;
                int errorCode = jsonObject.getIntValue("errcode");
                String errorMsg = jsonObject.getString("errmsg");
                log.error("获取网页授权凭证失败  errcode:" + errorCode + "errmsg:" + errorMsg);
            }
        }
        return wat;
    }

    /**
     * 刷新网页授权凭证
     * @param refresh_token 用于刷新凭证
     * @return WeixinOauth2Token
     */
    public static Token refreshToken(String refresh_token) {
        Token wat = null;
        // 拼接请求地址
        Oauth oauth = new Oauth();
        String requestUrl = oauth.refreshTokenUrl(refresh_token);
        // 获取网页授权凭证
        JSONObject jsonObject = HttpClientUtil.httpsRequest1(requestUrl, "GET", null);
        if (null != jsonObject) {
            try {
                wat = new Token();
                wat.setAccessToken(jsonObject.getString("access_token"));
                wat.setExpiresIn(jsonObject.getIntValue("expires_in"));
                wat.setRefreshToken(jsonObject.getString("refresh_token"));
                wat.setOpenId(jsonObject.getString("openid"));
                wat.setScope(jsonObject.getString("scope"));
            } catch (Exception e) {
                wat = null;
                int errorCode = jsonObject.getIntValue("errcode");
                String errorMsg = jsonObject.getString("errmsg");
                log.error("刷新网页授权凭证失败  errcode:" + errorCode + "errmsg:" + errorMsg);
            }
        }
        return wat;
    }

    /**
     * 通过网页授权获取用户信息
     * @param accessToken 网页授权接口调用凭证
     * @param openId 用户标识
     * @return SNSUserInfo
     */
    public static SNSUserInfo getSNSUserInfo(String accessToken, String openId) {
        SNSUserInfo snsUserInfo = null;
        // 拼接请求地址
        Oauth oauth = new Oauth();
        String requestUrl = oauth.userInfoUrl(accessToken, openId);
        requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openId);
        // 通过网页授权获取用户信息
        JSONObject jsonObject = HttpClientUtil.httpsRequest1(requestUrl, "GET", null);

        if (null != jsonObject) {
            try {
                snsUserInfo = new SNSUserInfo();
                // 用户的标识
                snsUserInfo.setOpenId(jsonObject.getString("openid"));
                // 昵称
                snsUserInfo.setNickname(jsonObject.getString("nickname"));
                // 性别（1是男性，2是女性，0是未知）
                snsUserInfo.setSex(jsonObject.getIntValue("sex"));
                // 用户所在国家
                snsUserInfo.setCountry(jsonObject.getString("country"));
                // 用户所在省份
                snsUserInfo.setProvince(jsonObject.getString("province"));
                // 用户所在城市
                snsUserInfo.setCity(jsonObject.getString("city"));
                // 用户头像
                snsUserInfo.setHeadImgUrl(jsonObject.getString("headimgurl"));
            } catch (Exception e) {
                snsUserInfo = null;
                int errorCode = jsonObject.getIntValue("errcode");
                String errorMsg = jsonObject.getString("errmsg");
                log.error("获取用户信息失败  errcode:" + errorCode + "errmsg:" + errorMsg);
            }
        }
        return snsUserInfo;
    }

    /**
     * URL编码（utf-8）
     * @param source
     * @return
     */
    public static String UrlEncodeUTF8(String source) {
        String result = source;
        try {
            result = URLEncoder.encode(source, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 微信公众号验证消息来自微信服务器
     * @param token
     * @param timestamp 时间戳
     * @param nonce 随机数
     * @param signature 微信加密签名
     * @return
     */
    public static boolean checkToken(String token, String timestamp, String nonce, String signature) {
        String[] arr = {token, timestamp, nonce};
        Arrays.sort(arr);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            sb.append(arr[i]);
        }
        String sha1Str = Sha1Util.getSha1(sb.toString());
        if (sha1Str.equals(signature)) {
            return true;
        } else {
            return false;
        }
    }
}
