package com.jspxcms.core.support;

import com.jspxcms.common.web.Servlets;
import com.jspxcms.core.service.OperationLogService;
import com.jspxcms.core.service.UserShiroService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by lidengqi on 2017/2/24.
 */
public class ThreeLoginUtil {
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
    private UserShiroService userShiroService;
    @Autowired
    private OperationLogService logService;
}
