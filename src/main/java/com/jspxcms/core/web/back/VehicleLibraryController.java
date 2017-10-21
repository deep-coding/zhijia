package com.jspxcms.core.web.back;

import static com.jspxcms.core.constant.Constants.CREATE;
import static com.jspxcms.core.constant.Constants.DELETE_SUCCESS;
import static com.jspxcms.core.constant.Constants.EDIT;
import static com.jspxcms.core.constant.Constants.MESSAGE;
import static com.jspxcms.core.constant.Constants.OPERATION_SUCCESS;
import static com.jspxcms.core.constant.Constants.OPRT;
import static com.jspxcms.core.constant.Constants.SAVE_SUCCESS;
import static com.jspxcms.core.constant.Constants.VIEW;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.token.TokenHolder;
import com.jspxcms.common.orm.RowSide;
import com.jspxcms.common.web.PathResolver;
import com.jspxcms.common.web.Servlets;
import com.jspxcms.core.commercial.Weixin;
import com.jspxcms.core.constant.Constants;
import com.jspxcms.core.domain.Attribute;
import com.jspxcms.core.domain.Info;
import com.jspxcms.core.domain.InfoDetail;
import com.jspxcms.core.domain.InfoFile;
import com.jspxcms.core.domain.InfoImage;
import com.jspxcms.core.domain.MemberGroup;
import com.jspxcms.core.domain.Model;
import com.jspxcms.core.domain.ModelField;
import com.jspxcms.core.domain.Node;
import com.jspxcms.core.domain.Site;
import com.jspxcms.core.domain.User;
import com.jspxcms.core.html.HtmlService;
import com.jspxcms.core.service.AttributeService;
import com.jspxcms.core.service.InfoQueryService;
import com.jspxcms.core.service.InfoService;
import com.jspxcms.core.service.MemberGroupService;
import com.jspxcms.core.service.NodeQueryService;
import com.jspxcms.core.service.OperationLogService;
import com.jspxcms.core.support.Backends;
import com.jspxcms.core.support.CmsException;
import com.jspxcms.core.support.Context;

import freemarker.template.TemplateException;

/**
 * InfoController
 * 
 * @author liufang
 * 
 */
@Controller
@RequestMapping("core/model_base")
public class ModelLibraryController {
	private static final Logger logger = LoggerFactory
			.getLogger(ModelLibraryController.class);

	/*@Autowired
	private */
	
	
	@RequiresPermissions("core:model_edit:model_edit")
	@RequestMapping("model_edit.do")
	public String modelEdit(HttpServletRequest request,
			org.springframework.ui.Model modelMap) {
		Site site = Context.getCurrentSite();
		/*Model model = modelService.get(modelId);
		Backends.validateDataInSite(model, site.getId());
		modelMap.addAttribute("model", model);
		List<ModelField> list = service.findList(modelId);
		modelMap.addAttribute("list", list);

		List<String> types = modelTypeHolder.getTypes();
		modelMap.addAttribute("types", types);
		modelMap.addAttribute("queryType", model.getType());*/
		return "core/model_base/model_edit";
	}
}
