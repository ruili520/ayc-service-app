package com.ayc.service.app.service;

import com.ayc.service.api.dto.SmsDto;

/**
 * Author:  wwb
 * Date:  2019/2/2015:28
 * Description:
 */
public interface CodeSaveRedisService {

    void saveSmsCode(SmsDto smsDto);

    void getSmsCode(SmsDto smsDto);
}
