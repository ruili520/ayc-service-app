package com.ayc.service.app.controller;

import com.ayc.framework.aspect.AppAuth;
import com.ayc.framework.aspect.ValidateFiled;
import com.ayc.framework.aspect.ValidateGroup;
import com.ayc.framework.common.AjaxResult;
import com.ayc.framework.common.BizCode;
import com.ayc.framework.common.BizException;
import com.ayc.service.api.dto.SmsDto;
import com.ayc.service.app.security.AppSessionUser;
import com.ayc.service.app.service.CodeSaveRedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Author:  wwb
 * Date:  2019/2/2017:17
 * Description:短信控制器
 */
@Controller
@RequestMapping("/sms")
public class SmsController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private CodeSaveRedisService codeSaveRedisService;

    @ValidateGroup(fileds = {
            @ValidateFiled(index = 0, notNull = false, icode = BizCode.CLIENT_ERROR, message = "版本号"),
    })
    @PostMapping("/sendcode")
    @ResponseBody
    public AjaxResult SendSmscode(@RequestBody SmsDto smsDto) {
        long startTime = System.currentTimeMillis();
        logger.info("短信发送接口调用开始,参数:[dto:{}}]");
        try {
            codeSaveRedisService.saveSmsCode(smsDto);
        } catch (BizException e) {
            long endTime = System.currentTimeMillis();
            logger.error("短信发送接口调用异常!耗时:{},异常信息:{}", (endTime - startTime), e.getMessage(), e);
            return AjaxResult.failed(e.getCode());
        } catch (Exception e) {
            // 记录异常信息
            long endTime = System.currentTimeMillis();
            logger.error("短信发送接口调用异常!耗时:{},异常信息:{}", (endTime - startTime), e.getMessage(), e);
            return AjaxResult.failed();
        }
        long endTime = System.currentTimeMillis();
        logger.info("短信发送接口调用完成,耗时:{},返回结果:[testEntity:{}]", (endTime - startTime));
        return AjaxResult.success();
    }
}
