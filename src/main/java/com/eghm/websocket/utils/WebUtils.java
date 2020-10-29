package com.eghm.websocket.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;


import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;
import com.eghm.websocket.model.User;
import org.springframework.util.StringUtils;

@Slf4j
public class WebUtils {


    public static User getUser(HttpServletRequest request) {
        Object obj = request.getSession().getAttribute(Constants.SESSION_USER);
        if (obj != null) {
            return (User) obj;
        }
        return null;
    }


    /**
     * 获取session中的数据
     *
     * @param request
     * @param key
     * @return
     */
    public static String getAttribute(HttpServletRequest request, String key) {
        Object obj = request.getSession().getAttribute(key);
        if (obj != null) {
            return obj.toString();
        }
        return null;
    }

    /**
     * 获取session中的数据
     *
     * @param request
     * @param key
     * @return
     */
    public static Integer getSessionInteger(HttpServletRequest request, String key) {
        String value = getAttribute(request, key);
        if (value != null) {
            return Integer.parseInt(value);
        }
        return null;
    }


    /**
     * 获取IP地址
     *
     * @param
     * @return String
     */
    public static String getIPAddress(HttpServletRequest request) {
        String IPAddress = request.getHeader("x-forwarded-for");
        if (IPAddress != null && IPAddress.length() != 0 && IPAddress.equals("unknown")) {
            IPAddress = IPAddress.split(",")[0];
        }
        if (IPAddress == null || IPAddress.length() == 0) {
            IPAddress = request.getHeader("Proxy-Clint-IP");
        }
        if (IPAddress == null || IPAddress.length() == 0) {
            IPAddress = request.getHeader("WL-Proxy-Clint-IP");
        }
        if (IPAddress == null || IPAddress.length() == 0) {
            IPAddress = request.getRemoteAddr();
        }
        return IPAddress;
    }

    /**
     * 获取前台传递的参数,如果有相同参数名的取最后一个
     *
     * @param request
     * @param param
     * @return
     */
    public static String getParameter(HttpServletRequest request, String param) {
        if (StringUtils.isEmpty(param)) {
            return null;
        }
        if (Constants.POST.equalsIgnoreCase(request.getMethod())) {
            return request.getParameter(param);
        }
        String params = request.getQueryString();
        if (StringUtils.isEmpty(params)) {
            return null;
        }
        try {
            params = URLDecoder.decode(params, Constants.UTF8);
        } catch (UnsupportedEncodingException e) {
            log.error("不支持UTF-8转换", e);
        }
        String[] values = parseParameter(params).get(param);
        if (values != null && values.length > 0) {
            return values[values.length - 1];
        }
        return null;
    }

    /**
     * 将get请求的url参数解析
     *
     * @return
     */
    public static Map<String, String[]> parseParameter(String params) {
        if (StringUtils.isEmpty(params)) {
            throw new IllegalArgumentException();
        }
        String[] array = null;
        Map<String, String[]> map = new HashMap<String, String[]>();
        String[] st = params.split("&");
        for (int i = 0; i < st.length; i++) {
            int pos = st[i].indexOf("=");
            if (pos == -1) {
                continue;
            }
            String key = st[i].substring(0, pos);
            String value = st[i].substring(pos + 1, st[i].length());
            if (map.containsKey(key)) {//参数的key在map中已经包含
                String[] old = map.get(key);
                array = new String[old.length + 1];
                for (int j = 0; j < old.length; j++) {
                    array[j] = old[j];
                }
                array[old.length] = value;
            } else {
                array = new String[1];
                array[1] = value;
            }
            map.put(key, array);
        }

        return map;
    }
}
