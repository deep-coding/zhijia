package com.jspxcms.core.web.directive;

import com.jspxcms.common.freemarker.Freemarkers;
import com.jspxcms.common.orm.Limitable;
import com.jspxcms.core.domain.User;
import com.jspxcms.core.service.UserService;
import com.jspxcms.core.support.ForeContext;
import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by lidengqi on 2017/1/20.
 */
public class AbstractMemberListPageDirective {
    // 组织ID
//    public static final String ORG_ID = "orgId";
    public static final String ORG_TREE_NUMBER = "orgTreeNumber";
    // 会员组ID
    public static final String MEMBERGROUP_ID = "memberGroupId";
    // 类型（0：会员，1：管理员）
    public static final String TYPE = "type";
    // 状态（0：正常，1：锁定，2：待验证）
    public static final String STATUS = "status";

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void doExecute(Environment env, Map params,
            TemplateModel[] loopVars, TemplateDirectiveBody body, boolean isPage)
            throws TemplateException, IOException {

        if (loopVars.length < 1) {
            throw new TemplateModelException("Loop variable is required.");
        }
        if (body == null) {
            throw new RuntimeException("missing body");
        }
//        Integer orgId = Freemarkers.getInteger(params, ORG_ID);
        String orgTreeNumber = Freemarkers.getString(params, ORG_TREE_NUMBER);
        Integer memberGroupId = Freemarkers.getInteger(params, MEMBERGROUP_ID);
        Integer type = Freemarkers.getInteger(params, TYPE);
        Integer status = Freemarkers.getInteger(params, STATUS);

        Sort defSort = new Sort(Sort.Direction.DESC, "id");
        if (isPage) {
            Pageable pageable = Freemarkers.getPageable(params, env, defSort);
            Page<User> pagedList = userService.findPage(memberGroupId, orgTreeNumber, type, status, null, pageable);
            ForeContext.setTotalPages(pagedList.getTotalPages());
            loopVars[0] = env.getObjectWrapper().wrap(pagedList);
        } else {
            Limitable limitable = Freemarkers.getLimitable(params, defSort);
            List<User> list = userService.findList(memberGroupId, orgTreeNumber, type, status, null, limitable);
            loopVars[0] = env.getObjectWrapper().wrap(list);
        }
        body.render(env.getOut());
    }

    @Autowired
    private UserService userService;
}
