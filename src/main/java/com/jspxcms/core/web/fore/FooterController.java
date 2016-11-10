package com.jspxcms.core.web.fore;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jspxcms.core.constant.Constants;
import com.jspxcms.core.domain.Site;
import com.jspxcms.core.support.Context;
import com.jspxcms.core.support.ForeContext;
import com.jspxcms.core.support.SiteResolver;

/**
 * FooterController
 * 
 * @author lidengqi
 *
 */
@Controller
public class FooterController {

    public static final String FOOTER_TEMPLATE = "zj_about.html";
    public static final String TAB_INDEX = "tabIndex";
    

    @Autowired
    private SiteResolver siteResolver;

    @RequestMapping("/zj_about.jspx")
    public String index (HttpServletRequest request, Integer tabIndex, Model modelMap) {
        return index(null, request, tabIndex, modelMap);
    }

    @RequestMapping(Constants.SITE_PREFIX_PATH + "/zj_about.jspx")
    public String index (@PathVariable String siteNumber, HttpServletRequest request,
            Integer tabIndex, Model modelMap) {
        siteResolver.resolveSite(siteNumber);
        Site site = Context.getCurrentSite();
        Map<String, Object> data = modelMap.asMap();
        ForeContext.setData(data, request);
        data.put(TAB_INDEX, tabIndex);
        return site.getTemplate(FOOTER_TEMPLATE);
    }
}
