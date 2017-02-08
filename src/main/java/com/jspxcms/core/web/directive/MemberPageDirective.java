package com.jspxcms.core.web.directive;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

import java.io.IOException;
import java.util.Map;

/**
 * Created by lidengqi on 2017/2/8.
 */
public class MemberPageDirective extends AbstractMemberListPageDirective implements TemplateDirectiveModel {
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void execute(Environment env, Map params, TemplateModel[] loopVars,
            TemplateDirectiveBody body) throws TemplateException, IOException {
        super.doExecute(env, params, loopVars, body, true);
    }
}
