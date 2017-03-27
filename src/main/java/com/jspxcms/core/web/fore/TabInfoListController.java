package com.jspxcms.core.web.fore;

import com.jspxcms.core.constant.Constants;
import com.jspxcms.core.domain.Site;
import com.jspxcms.core.support.Context;
import com.jspxcms.core.support.ForeContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by lidengqi on 2017/1/10.
 */
@Controller
public class TabInfoListController {
    public static final String ZUIXIN_INFOLIST_TEMPLATE = "list_news_zuixin.html";
    public static final String ZUIXIN_LOAD_INFOLIST_TEMPLATE = "list_news_zuixin_load.html";
    public static final String TAB_INFOLIST_TEMPLATE = "list_news_tab.html";

    @RequestMapping(value = {"/zuixin/list_news.jspx", Constants.SITE_PREFIX_PATH + "/zuixin/list_news.jspx"})
    public String zuixinInfoList(HttpServletRequest request, HttpServletResponse response, Model modelMap) {
        Site site = Context.getCurrentSite();
        Map<String, Object> data = modelMap.asMap();
        ForeContext.setData(data, request);
        return site.getTemplate(ZUIXIN_INFOLIST_TEMPLATE);
    }

    @RequestMapping(value = {"/zuixin/load_more/list_news.jspx", Constants.SITE_PREFIX_PATH + "/zuixin/load_more/list_news.jspx"})
    public String zuixinLoadInfoList(HttpServletRequest request, HttpServletResponse response, Model modelMap) {
        Site site = Context.getCurrentSite();
        Map<String, Object> data = modelMap.asMap();
        ForeContext.setData(data, request);
        return site.getTemplate(ZUIXIN_LOAD_INFOLIST_TEMPLATE);
    }

    @RequestMapping(value = {"/tab/list_news.jspx", Constants.SITE_PREFIX_PATH + "/tab/list_news.jspx"})
    public String tabInfoList(HttpServletRequest request, HttpServletResponse response, Model modelMap) {
        Site site = Context.getCurrentSite();
        Map<String, Object> data = modelMap.asMap();
        ForeContext.setData(data, request);
        return site.getTemplate(TAB_INFOLIST_TEMPLATE);
    }
}
