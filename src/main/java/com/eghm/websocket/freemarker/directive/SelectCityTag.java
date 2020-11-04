package com.eghm.websocket.freemarker.directive;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateModel;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;
import java.util.Set;


/**
 * @author 二哥很猛
 */
public class SelectCityTag implements TemplateDirectiveModel {


    @Override
    public void execute(Environment env, Map params, TemplateModel[] loopVars,
                        TemplateDirectiveBody body) throws IOException {
        Set<String> keys = params.keySet();
        StringBuffer attr = new StringBuffer();
        for (String key : keys) {
            String value = params.get(key).toString();
            attr.append(key).append("=").append(value);
        }

        Writer out = env.getOut();
        out.write(attr.toString());
    }

}
