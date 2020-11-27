package com.eghm.websocket.account.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.eghm.websocket.account.enums.BusinessType;
import com.eghm.websocket.account.service.CipherService;
import com.eghm.websocket.account.service.SignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author 殿小二
 * @date 2020/11/25
 */
@Service("signService")
public class SignServiceImpl implements SignService {

    @Autowired
    private CipherService cipherService;

    @Override
    public String sign(BusinessType type, Object object) {
        return this.sign(type.getSignField(), object);
    }

    @Override
    public String sign(String[] signField, Object object) {
        Map<String, Object> objectMap = BeanUtil.beanToMap(object, false, true);
        StringBuilder builder = new StringBuilder();
        for (String key: signField) {
            Object value = objectMap.get(key);
            // 宝付文档写的要传null,但他妈竟然不吃null
            if (ObjectUtil.isNotNull(value)) {
                builder.append(value);
            }
            builder.append("|");
        }
        String substring = builder.substring(0, builder.length() - 1);
        return cipherService.sign(substring);
    }

}
