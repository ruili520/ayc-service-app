package com.ayc.service.app.service;

import com.ayc.service.uc.entity.AycUserEntity;

/**
 * Author:  wwb
 * Date:  2019/2/2116:07
 * Description:生成Token值并且保存到redis
 */
public interface TokenSaveRedisService {

    String getToken(AycUserEntity aycUserEntity);
}
