package com.jspxcms.core.web.fore;

import com.jspxcms.core.constant.Constants;
import com.jspxcms.core.domain.Site;
import com.jspxcms.core.support.Context;
import com.jspxcms.core.support.ForeContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

import static com.jspxcms.core.security.CmsAuthenticationFilter.FALLBACK_URL_PARAM;

/**
 * Created by lidengqi on 2017/1/6.
 */
public class WeiboController {
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping(value = {"/oauth/login/weibo.jspx",
            Constants.SITE_PREFIX_PATH + "/oauth/login/weibo.jspx"})
    public void login(String fallbackUrl, HttpServletRequest request, Model modelMap)
            throws WeiboException, IOException {
//        Site site = Context.getCurrentSite();
        Map<String, Object> data = modelMap.asMap();
        ForeContext.setData(data, request);
        modelMap.addAttribute(FALLBACK_URL_PARAM, fallbackUrl);


        Oauth oauth = new Oauth();
        BareBonesBrowserLaunch.openURL(oauth.authorize("code"));
        logger.info(oauth.authorize("code"));
//        System.out.print("Hit enter when it's done.[Enter]:");
//        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//        String code = br.readLine();
//        logger.info(code);

//        return site.getTemplate("");
    }

    @RequestMapping(value = {"/oauth/authc/weibo.jspx",
            Constants.SITE_PREFIX_PATH + "/oauth/authc/weibo.jspx"})
    public String authc(String fallbackUrl, HttpServletRequest request, Model modelMap) {
        Site site = Context.getCurrentSite();
        Map<String, Object> data = modelMap.asMap();
        ForeContext.setData(data, request);
        modelMap.addAttribute(FALLBACK_URL_PARAM, fallbackUrl);

        AccessToken accessTokenObj = null;
        String accessToken = "";
        String uid = "";
        String screenName = "";
        String userName = "";
        Oauth oauth = new Oauth();
        String code = request.getParameter("code");
        try{
            accessTokenObj= oauth.getAccessTokenByCode(code);
            accessToken = accessTokenObj.getAccessToken();

            Account account = new Account(accessToken);
            JSONObject uidJson = account.getUid();
            uid = uidJson.getString("uid");
            Users users = new Users(accessToken);
            User weiboUser = users.showUserById(uid);
            userName = weiboUser.getName();
            screenName = weiboUser.getScreenName();
        } catch (WeiboException e) {
            if(401 == e.getStatusCode()){
                logger.error("Unable to get the access token.");
            }else{
                e.printStackTrace();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }
}
