package com.jspxcms.core.web.fore;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jspxcms.common.web.Servlets;
import com.jspxcms.core.constant.Constants;
import com.jspxcms.core.domain.Site;
import com.jspxcms.core.service.SiteService;
import com.jspxcms.core.support.Context;
import com.jspxcms.core.support.ForeContext;
import com.jspxcms.core.support.Response;
import com.jspxcms.core.support.SiteResolver;

/**
 * AppController
 * 
 * @author liufang
 * 
 */
@Controller
public class AppController {

	private static String TEMPLATE_SECOND_NODE = "app_second_list.html";
	private static String TEMPLATE_ZHUANLAN_NODE = "zhuanlan.html";

	@RequestMapping("/app.jspx")
	private String app(Integer page, HttpServletRequest request,
			HttpServletResponse response, org.springframework.ui.Model modelMap) {
		return app(null, page, request, response, modelMap);
	}

	@RequestMapping(Constants.SITE_PREFIX_PATH + "/app.jspx")
	private String app(@PathVariable String siteNumber, Integer page,
			HttpServletRequest request, HttpServletResponse response,
			org.springframework.ui.Model modelMap) {
		siteResolver.resolveSite(siteNumber);
		Site site = Context.getCurrentSite();
		Response resp = new Response(request, response, modelMap);
		String template = Servlets.getParam(request, "template");
		if (StringUtils.isBlank(template)) {
			return resp.badRequest("parameter 'template' is required.");
		}
		template = "app_" + template + ".html";

		Map<String, Object> data = modelMap.asMap();
		ForeContext.setData(data, request);
		ForeContext.setPage(data, page);
		return site.getTemplate(template);
	}

	@RequestMapping("/second_node/app.jspx")
	private String secondNode(Integer page, HttpServletRequest request,
					   HttpServletResponse response, org.springframework.ui.Model modelMap) {
		return sencondNode(null, page, request, response, modelMap);
	}

	@RequestMapping(Constants.SITE_PREFIX_PATH + "/second_node/app.jspx")
	private String sencondNode(@PathVariable String siteNumber, Integer page,
					   HttpServletRequest request, HttpServletResponse response,
					   org.springframework.ui.Model modelMap) {
		siteResolver.resolveSite(siteNumber);
		Site site = Context.getCurrentSite();
		Response resp = new Response(request, response, modelMap);

		Map<String, Object> data = modelMap.asMap();
		ForeContext.setData(data, request);
		ForeContext.setPage(data, page);
		return site.getTemplate(TEMPLATE_SECOND_NODE);
	}

	@RequestMapping("/zhuanlan/app.jspx")
	private String zhuanLan(Integer page, HttpServletRequest request,
							  HttpServletResponse response, org.springframework.ui.Model modelMap) {
		return zhuanLan(null, page, request, response, modelMap);
	}

	@RequestMapping(Constants.SITE_PREFIX_PATH + "/zhuanlan/app.jspx")
	private String zhuanLan(@PathVariable String siteNumber, Integer page,
							   HttpServletRequest request, HttpServletResponse response,
							   org.springframework.ui.Model modelMap) {
		siteResolver.resolveSite(siteNumber);
		Site site = Context.getCurrentSite();
		Response resp = new Response(request, response, modelMap);

		Map<String, Object> data = modelMap.asMap();
		ForeContext.setData(data, request);
		ForeContext.setPage(data, page);
		return site.getTemplate(TEMPLATE_ZHUANLAN_NODE);
	}

	@Autowired
	private SiteResolver siteResolver;
	@Autowired
	private SiteService siteService;
}
