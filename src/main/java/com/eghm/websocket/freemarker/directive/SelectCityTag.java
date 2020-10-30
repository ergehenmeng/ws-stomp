package com.eghm.websocket.freemarker.directive;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;
import java.util.Set;

import com.eghm.websocket.utils.StringUtil;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * @author
 */
public class SelectCityTag implements TemplateDirectiveModel{
	
	
	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars,
			TemplateDirectiveBody body) throws TemplateException, IOException {
		Set<String> keys = params.keySet();
		StringBuffer attr = new StringBuffer();
		for(String key : keys){
			String value = params.get(key).toString();
			attr.append(StringUtil.getDashlineCase(key)).append("=").append(value);
		}
		
		Writer out = env.getOut();
		out.write(attr.toString());
	}

}
