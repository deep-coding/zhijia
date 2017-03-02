package weixin.util;


/**
 * Created by lidengqi on 2017/2/24.
 */
public class Oauth {
    public String authorize() {
        return WeixinConfig.getValue("oauth.weixin.authorize_url").trim()
                + "?appid=" + WeixinConfig.getValue("oauth.weixin.appid").trim()
                + "&redirect_uri=" + WeiXinUtil.UrlEncodeUTF8(WeixinConfig.getValue("oauth.weixin.redirect_uri")).trim()
                + "&response_type=" + WeixinConfig.getValue("oauth.weixin.response_type").trim()
                + "&scope=" + WeixinConfig.getValue("oauth.weixin.scope").trim()
                + "&state=123456#wechat_redirect";
    }

    public String accessTokenUrl(String code) {
        return WeixinConfig.getValue("oauth.weixin.accessToken_url").trim()
                + "?appid=" + WeixinConfig.getValue("oauth.weixin.appid").trim()
                + "&secret=" + WeixinConfig.getValue("oauth.weixin.secret").trim()
                + "&code=" + code
                + "&grant_type=" + WeixinConfig.getValue("oauth.weixin.grant_type").trim();
    }

    public String refreshTokenUrl(String refresh_token) {
        return WeixinConfig.getValue("oauth.weixin.refreshToken_ur").trim()
                + "?appid=" + WeixinConfig.getValue("oauth.weixin.appid").trim()
                + "&grant_type=refresh_token"
                + "&refresh_token=" + refresh_token;
    }

    public String userInfoUrl(String access_token, String openid) {
        return WeixinConfig.getValue("oauth.weixin.userinfo_url").trim()
                + "?access_token=" + access_token
                + "&openid=" + openid
                + "&lang=zh_CN";
    }
}
