package com.jspxcms.core.web.fore;

import com.jspxcms.common.web.Servlets;
import com.jspxcms.core.constant.Constants;
import com.jspxcms.core.domain.GlobalRegister;
import com.jspxcms.core.domain.Site;
import com.jspxcms.core.security.CmsAuthenticationFilter;
import com.jspxcms.core.service.OperationLogService;
import com.jspxcms.core.service.UserService;
import com.jspxcms.core.service.UserShiroService;
import com.jspxcms.core.support.Context;
import com.jspxcms.core.support.ForeContext;
import org.apache.http.util.TextUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import weixin.entity.SNSUserInfo;
import weixin.entity.Token;
import weixin.util.Oauth;
import weixin.util.WeiXinUtil;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * Created by lidengqi on 2017/2/24.
 */
@Controller
public class WeixinController {
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    // 微信登录获取code
    @RequestMapping(value = {"/oauth/login/weixin.jspx",
            Constants.SITE_PREFIX_PATH + "/oauth/login/weixin.jspx"})
    public void weixinLogin(String fallbackUrl, HttpServletRequest request, HttpServletResponse response, Model modelMap)
            throws IOException {
        Map<String, Object> data = modelMap.asMap();
        ForeContext.setData(data, request);
        modelMap.addAttribute(CmsAuthenticationFilter.FALLBACK_URL_PARAM, fallbackUrl);

        Oauth oauth = new Oauth();
        String oauthUrl = oauth.authorize();
        logger.info(oauthUrl);
        response.sendRedirect(oauthUrl);
    }

    // 微信授权后回调，通过code获取accessToken，openId
    @RequestMapping(value = {"/oauth/authc/weixin.jspx",
            Constants.SITE_PREFIX_PATH + "/oauth/authc/weixin.jspx"})
    public void weixinAuthc(String fallbackUrl, final HttpServletRequest request, final HttpServletResponse response, Model modelMap) {
        Site site = Context.getCurrentSite();
        Map<String, Object> data = modelMap.asMap();
        ForeContext.setData(data, request);
        modelMap.addAttribute(CmsAuthenticationFilter.FALLBACK_URL_PARAM, fallbackUrl);

        Token token = null;
        String accessToken = "";
        String openId = "";
        String userName = "";
        String code = request.getParameter("code");
        try {
            if (TextUtils.isEmpty(code)) {
                WebUtils.redirectToSavedRequest(request, response, "/");
            } else {
                token = WeiXinUtil.getOauth2AccessToken(code);
                accessToken = token.getAccessToken();
                openId = token.getOpenId();
                SNSUserInfo userInfo = WeiXinUtil.getSNSUserInfo(accessToken, openId);
                userName = userInfo.getNickname();

                //查询数据库中openid是否存在，如果存在则自动登录绑定账户，不存在则直接用微信账号登录并在数据库中插入一条新用户数据
                com.jspxcms.core.domain.User user = userShiroService.findByWeixinOpenid(openId);
                if (null == user || null == user.getId()) {
                    GlobalRegister reg = site.getGlobal().getRegister();
                    String ip = Servlets.getRemoteAddr(request);
                    int groupId = reg.getGroupId();
                    int orgId = reg.getOrgId();
                    int status = com.jspxcms.core.domain.User.NORMAL;
                    String gender = null;
                    if (userInfo.getSex() == 1) {
                        gender = "M";
                    } else if (userInfo.getSex() == 2) {
                        gender = "F";
                    }
                    user = userService.register(ip, groupId, orgId, status, userName,
                            null, null, null, null, openId, gender,
                            null, null, null, null, null, null);
                }
                request.setAttribute(CmsAuthenticationFilter.DEFAULT_USERNAME_PARAM, user.getUsername());
                request.setAttribute(CmsAuthenticationFilter.DEFAULT_PASSWORD_PARAM, user.getPassword());
                Context.setCurrentUser(user);
                Context.setCurrentGroup(request, user.getGroup());
                Context.setCurrentGroups(request, user.getGroups());
                Context.setCurrentOrg(request, user.getOrg());
                Context.setCurrentOrgs(request, user.getOrgs());
                executeLogin(request, response, user);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public AuthenticationToken createToken(ServletRequest request, ServletResponse response, com.jspxcms.core.domain.User user) {
        String userName = user.getUsername();
        String password = user.getPassword();
        return new UsernamePasswordToken(userName, password, false, request.getRemoteHost());
    }

    public void executeLogin(HttpServletRequest request, HttpServletResponse response, com.jspxcms.core.domain.User user) {
        try {
            AuthenticationToken token = createToken(request, response, user);
            if (token == null) {
                String msg = "createToken method implementation returned null. A valid non-null AuthenticationToken "
                        + "must be created in order to execute a login attempt.";
                throw new IllegalStateException(msg);
            }
            String ip = Servlets.getRemoteAddr(request);
            // 登录时，session会失效，先将SavedRequest取出
            SavedRequest savedRequest = (SavedRequest) request.getSession()
                    .getAttribute(WebUtils.SAVED_REQUEST_KEY);
            Subject subject = SecurityUtils.getSubject();;
            // 防止session fixation attack(会话固定攻击)，让旧session失效
            if (subject.getSession(false) != null) {
                subject.logout();
            }
            subject.login(token);
            // 将SavedRequest放回session
            request.getSession().setAttribute(WebUtils.SAVED_REQUEST_KEY,
                    savedRequest);
            logService.loginSuccess(ip, user.getId());
            userShiroService.updateLoginSuccess(user.getId(), ip);
            WebUtils.redirectToSavedRequest(request, response, "/");
        } catch (IOException e) {
            logService.loginFailure(user.getUsername() + ":" + user.getPassword(), Servlets.getRemoteAddr(request));
            e.printStackTrace();
        }
    }

    @Autowired
    private UserService userService;
    @Autowired
    private UserShiroService userShiroService;
    @Autowired
    private OperationLogService logService;
}
