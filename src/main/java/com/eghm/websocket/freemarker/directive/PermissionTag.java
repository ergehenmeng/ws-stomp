package com.eghm.websocket.freemarker.directive;

import freemarker.core.Environment;
import freemarker.template.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;



public class PermissionTag implements TemplateDirectiveModel {


    @Override
    public void execute(Environment env, Map params, TemplateModel[] loopVars,
                        TemplateDirectiveBody body) throws TemplateException, IOException {
        if (params.isEmpty()) {
            throw new TemplateModelException("该标签参数不允许为空");
        }

    }


}
