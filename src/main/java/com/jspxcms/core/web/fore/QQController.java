package com.jspxcms.core.web.fore;

import com.jspxcms.common.web.Servlets;
import com.jspxcms.core.constant.Constants;
import com.jspxcms.core.domain.GlobalRegister;
import com.jspxcms.core.domain.Site;
import com.jspxcms.core.security.CmsAuthenticationFilter;
import com.jspxcms.core.service.UserService;
import com.jspxcms.core.service.UserShiroService;
import com.jspxcms.core.support.Context;
import com.jspxcms.core.support.ForeContext;
import com.jspxcms.core.support.ThreeLoginUtil;
import com.qq.connect.QQConnectException;
import com.qq.connect.api.OpenID;
import com.qq.connect.api.qzone.UserInfo;
import com.qq.connect.javabeans.AccessToken;
import com.qq.connect.javabeans.qzone.UserInfoBean;
import com.qq.connect.oauth.Oauth;
import org.apache.http.util.TextUtils;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * Created by lidengqi on 2017/2/24.
 */
@Controller
public class QQController {
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    // QQ登录获取code
    @RequestMapping(value = {"/oauth/login/qq.jspx",
            Constants.SITE_PREFIX_PATH + "/oauth/login/qq.jspx"})
    public String weixinLogin(String fallbackUrl, HttpServletRequest request, Model modelMap){
        Map<String, Object> data = modelMap.asMap();
        ForeContext.setData(data, request);
        modelMap.addAttribute(CmsAuthenticationFilter.FALLBACK_URL_PARAM, fallbackUrl);

        Oauth oauth = new Oauth();
        String oauthUrl = null;
        try {
            oauthUrl = oauth.getAuthorizeURL(request);
        } catch (QQConnectException e) {
            e.printStackTrace();
        }
        logger.info(oauthUrl);
        return "redirect:" + oauthUrl;
    }

    // QQ授权后回调，通过code获取accessToken，openId
    @RequestMapping(value = {"/oauth/authc/qq.jspx",
            Constants.SITE_PREFIX_PATH + "/oauth/authc/qq.jspx"})
    public void weiboAuthc(String fallbackUrl, final HttpServletRequest request, final HttpServletResponse response, Model modelMap) {
        Site site = Context.getCurrentSite();
        Map<String, Object> data = modelMap.asMap();
        ForeContext.setData(data, request);
        modelMap.addAttribute(CmsAuthenticationFilter.FALLBACK_URL_PARAM, fallbackUrl);


        String accessToken = "";
        long tokenExpireIn = 0L;
        String openID = "";
        String userName = "";
        try {
            AccessToken accessTokenObj = (new Oauth()).getAccessTokenByRequest(request);
            accessToken = accessTokenObj.getAccessToken();
            if (TextUtils.isEmpty(accessToken)) {
                WebUtils.redirectToSavedRequest(request, response, "/");
                System.out.print("没有获取到响应参数");
            } else {
                tokenExpireIn = accessTokenObj.getExpireIn();
                request.getSession().setAttribute("demo_access_token", accessToken);
                request.getSession().setAttribute("demo_token_expirein", String.valueOf(tokenExpireIn));

                // 利用获取到的accessToken 去获取当前用的openid -------- start
                OpenID openIDObj =  new OpenID(accessToken);
                openID = openIDObj.getUserOpenID();

                request.getSession().setAttribute("demo_openid", openID);
                // 利用获取到的accessToken 去获取当前用户的openid --------- end

                UserInfo qzoneUserInfo = new UserInfo(accessToken, openID);
                UserInfoBean userInfoBean = qzoneUserInfo.getUserInfo();
                if (userInfoBean.getRet() == 0) {
                    //查询数据库中openid是否存在，如果存在则自动登录绑定账户，不存在则直接用微信账号登录并在数据库中插入一条新用户数据
                    com.jspxcms.core.domain.User user = userShiroService.findByWeixinOpenid(openID);
                    if (null == user || null == user.getId()) {
                        GlobalRegister reg = site.getGlobal().getRegister();
                        String ip = Servlets.getRemoteAddr(request);
                        int groupId = reg.getGroupId();
                        int orgId = reg.getOrgId();
                        int status = com.jspxcms.core.domain.User.NORMAL;
                        userName = userInfoBean.getNickname();
                        String gender = null;
                        if ("男".equals(userInfoBean.getGender())) {
                            gender = "M";
                        } else if ("女".equals(userInfoBean.getGender())) {
                            gender = "N";
                        }
                        user = userService.register(ip, groupId, orgId, status, userName,
                                null, null, null, null, openID, gender,
                                null, null, null, null, null, null);
                    }
                    request.setAttribute(CmsAuthenticationFilter.DEFAULT_USERNAME_PARAM, user.getUsername());
                    request.setAttribute(CmsAuthenticationFilter.DEFAULT_PASSWORD_PARAM, user.getPassword());
                    Context.setCurrentUser(user);
                    Context.setCurrentGroup(request, user.getGroup());
                    Context.setCurrentGroups(request, user.getGroups());
                    Context.setCurrentOrg(request, user.getOrg());
                    Context.setCurrentOrgs(request, user.getOrgs());
                    ThreeLoginUtil threeLoginUtil = new ThreeLoginUtil();
                    threeLoginUtil.executeLogin(request, response, user);
                } else {
                    WebUtils.redirectToSavedRequest(request, response, "/");
                }
            }
        } catch (QQConnectException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Autowired
    private UserService userService;
    @Autowired
    private UserShiroService userShiroService;
}
