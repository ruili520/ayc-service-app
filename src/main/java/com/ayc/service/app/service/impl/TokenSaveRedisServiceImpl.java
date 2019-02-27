package com.ayc.service.app.service.impl;

import com.alibaba.fastjson.JSON;
import com.ayc.framework.redis.RedisUtil;
import com.ayc.framework.util.TokenGenerator;
import com.ayc.service.app.security.AppSessionUser;
import com.ayc.service.app.service.TokenSaveRedisService;
import com.ayc.service.uc.entity.AycUserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Author:  wwb
 * Date:  2019/2/2116:08
 * Description:
 */
@Service("tokenSaveRedisService")
public class TokenSaveRedisServiceImpl implements TokenSaveRedisService {

    private final static String GENERATOR_KEY = "APP_KEY_COUNTER";

    private final static String APPID = "APP";

    @Autowired
    private TokenGenerator tokenGenerator;
    @Autowired
    private RedisUtil redisUtil;
    @Override
    public String getToken(AycUserEntity aycUserEntity) {
        String token = tokenGenerator.generator(GENERATOR_KEY, APPID);
        AppSessionUser appSessionUser = new AppSessionUser();
        appSessionUser.setToken(token);
        appSessionUser.setName(aycUserEntity.getName());
        appSessionUser.setUserId(aycUserEntity.getId());
        appSessionUser.setMobile(aycUserEntity.getMobile());
        appSessionUser.setRoleCode(1);
        redisUtil.set(token, JSON.toJSONString(appSessionUser),60*60);
        return token;
    }
}
