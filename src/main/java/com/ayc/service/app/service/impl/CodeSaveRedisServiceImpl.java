package com.ayc.service.app.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ayc.framework.common.BizException;
import com.ayc.framework.redis.RedisUtil;
import com.ayc.framework.util.DozerUtil;
import com.ayc.framework.util.RandomUntil;
import com.ayc.service.api.configs.AliyunBizCode;
import com.ayc.service.api.service.AliyunSmsService;
import com.ayc.service.api.enums.TemplateEnum;
import com.ayc.service.uc.configs.UserBizCode;
import com.ayc.service.api.dto.SmsDto;
import com.ayc.service.app.service.CodeSaveRedisService;
import com.github.pagehelper.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Author:  wwb
 * Date:  2019/2/2015:29
 * Description:
 */
@Service("codeSaveRedisService")
public class CodeSaveRedisServiceImpl implements CodeSaveRedisService {

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private AliyunSmsService aliyunSmsService;

    @Override
    public void saveSmsCode(SmsDto smsDto) {
        String key = TemplateEnum.getKeyByType(smsDto.getType())+smsDto.getMobile();
        String smsCode = RandomUntil.smsCode();
        // 获取redis中存储的
        if (redisUtil.get(key) == null){
            smsDto.setTimes(1);
            smsDto.setSmsCode(smsCode);
        } else {
            String text = redisUtil.get(key);
            Object object = JSON.parse(text);
            DozerUtil.map(object, smsDto);
            // 验证码获取次数加一
            smsDto.setTimes(smsDto.getTimes() + 1);
            if (smsDto.getTimes() >= 3){
                throw new BizException(AliyunBizCode.SMSMORE_ERROR);
            }
            smsDto.setSmsCode(smsCode);
        }
        // 短信发送验证码
        // TODO 真实发送验证码
//        try {
//            aliyunSmsService.sendSms(smsDto);
//        } catch (ClientException e) {
//            e.printStackTrace();
//            throw new BizException(AliyunBizCode.ALISMS_ERROR);
//        }
        redisUtil.set(key, JSON.toJSONString(smsDto), 3 * 60);
    }

    @Override
    public void getSmsCode(SmsDto smsDto) {
        String key = TemplateEnum.getKeyByType(smsDto.getType())+smsDto.getMobile();
        String text = redisUtil.get(key);
        if (text == null || text.equals("")){
            throw new BizException(AliyunBizCode.SMSLOSE_ERROR);
        }
        String sendCode= JSONObject.parseObject(text, SmsDto.class).getSmsCode();
        if(sendCode == null || !sendCode.equals(smsDto.getSmsCode())){
            throw new BizException(AliyunBizCode.SMSCODE_ERROR);
        }
    }


}
