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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import weibo4j.Account;
import weibo4j.Oauth;
import weibo4j.Users;
import weibo4j.http.AccessToken;
import weibo4j.model.User;
import weibo4j.model.WeiboException;
import weibo4j.org.json.JSONException;
import weibo4j.org.json.JSONObject;
import weibo4j.util.BareBonesBrowserLaunch;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

/**
 * Created by lidengqi on 2017/1/6.
 */
@Controller
public class WeiboController {
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    // 微博登录获取code
    @RequestMapping(value = {"/oauth/login/weibo.jspx",
            Constants.SITE_PREFIX_PATH + "/oauth/login/weibo.jspx"})
    public String weiboLogin(String fallbackUrl, HttpServletRequest request, Model modelMap)
            throws WeiboException, IOException {
        Map<String, Object> data = modelMap.asMap();
        ForeContext.setData(data, request);
        modelMap.addAttribute(CmsAuthenticationFilter.FALLBACK_URL_PARAM, fallbackUrl);

        Oauth oauth = new Oauth();
        String oauthUrl = oauth.authorize("code");
        logger.info(oauthUrl);
//        BareBonesBrowserLaunch.openURL(oauthUrl);

//        System.out.print("Hit enter when it's done.[Enter]:");
//        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//        String code = br.readLine();
//        logger.info(code);

        return "redirect:" + oauthUrl;
    }

    // 微博授权后回调，通过code获取accessToken，uid
    @RequestMapping(value = {"/oauth/authc/weibo.jspx",
            Constants.SITE_PREFIX_PATH + "/oauth/authc/weibo.jspx"})
    public void weiboAuthc(String fallbackUrl, final HttpServletRequest request, final HttpServletResponse response, Model modelMap) {
        Site site = Context.getCurrentSite();
        Map<String, Object> data = modelMap.asMap();
        ForeContext.setData(data, request);
        modelMap.addAttribute(CmsAuthenticationFilter.FALLBACK_URL_PARAM, fallbackUrl);

        AccessToken accessTokenObj = null;
        String accessToken = "";
        String uid = "";
        String weiHao = "";
        String screenName = "";
        String userName = "";
        Oauth oauth = new Oauth();
        String code = request.getParameter("code");
        String url = "";
        try{
            accessTokenObj= oauth.getAccessTokenByCode(code);
            accessToken = accessTokenObj.getAccessToken();

            Account account = new Account(accessToken);
            JSONObject uidJson = account.getUid();
            uid = uidJson.getString("uid");
            Users users = new Users(accessToken);
            User weiboUser = users.showUserById(uid);
            weiHao = weiboUser.getWeihao();
            userName = weiboUser.getName();
            screenName = weiboUser.getScreenName();

            //查询数据库中uid是否存在，如果存在则自动登录绑定账户，不存在则直接用微博账号登录并在数据库中插入一条新用户数据
            com.jspxcms.core.domain.User user = userShiroService.findByWeiboUid(uid);
            if (null == user || null == user.getId()) {
                GlobalRegister reg = site.getGlobal().getRegister();
                String ip = Servlets.getRemoteAddr(request);
                int groupId = reg.getGroupId();
                int orgId = reg.getOrgId();
                int status = com.jspxcms.core.domain.User.NORMAL;
                String gender = null;
                if (weiboUser.getGender() != null && "n" != weiboUser.getGender() && "N" != weiboUser.getGender()) {
                    gender = weiboUser.getGender().toUpperCase();
                }
                user = userService.register(ip, groupId, orgId, status, userName,
                        null, null, null, uid, null, gender,
                        null, null, null, null, weiHao, null);
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
        } catch (WeiboException e) {
            if(401 == e.getStatusCode()){
                logger.error("Unable to get the access token.");
            }else{
                e.printStackTrace();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Autowired
    private UserService userService;
    @Autowired
    private UserShiroService userShiroService;
}
