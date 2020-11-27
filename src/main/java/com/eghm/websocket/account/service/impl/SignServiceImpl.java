package com.eghm.websocket.account.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.eghm.websocket.account.enums.BusinessType;
import com.eghm.websocket.account.service.CipherService;
import com.eghm.websocket.account.service.SignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author 殿小二
 * @date 2020/11/27
 */
@Service("signService")
public class SignServiceImpl implements SignService {

    @Autowired
    private CipherService cipherService;

    @Override
    public String sign(BusinessType type, Object object) {
        Map<String, Object> objectMap = BeanUtil.beanToMap(object);
        String[] signField = type.getSignField();
        StringBuilder builder = new StringBuilder();
        for (String key: signField) {
            builder.append(objectMap.get(key)).append("|");
        }
        String substring = builder.substring(0, builder.length() - 2);
        return cipherService.sign(substring);
    }

}
