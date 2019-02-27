package com.ayc.service.app.controller;

import com.ayc.framework.aspect.ValidateFiled;
import com.ayc.framework.aspect.ValidateGroup;
import com.ayc.framework.common.AjaxResult;
import com.ayc.framework.common.BizCode;
import com.ayc.framework.common.BizException;
import com.ayc.service.app.configs.AppConfigs;
import com.ayc.service.uc.dto.SelectUserDto;
import com.ayc.service.uc.service.CheckUserInfoService;
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
 * Date:  2019/2/2215:45
 * Description:校验用户相关资料控制层
 */
@Controller
@RequestMapping("check")
public class CheckUserController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private AppConfigs appConfigs;
    @Autowired
    private CheckUserInfoService checkUserInfoService;

    @ValidateGroup(fileds =
            { @ValidateFiled(index = 0, filedName = "mobile",notNull = true, icode = BizCode.CLIENT_ERROR, message = "手机号"),})
    @PostMapping("/bymobile")
    @ResponseBody
    public AjaxResult SelectByMobile(@RequestBody SelectUserDto checkUserDto) {
        long startTime = System.currentTimeMillis();
        logger.info("校验手机号接口调用开始,参数:[uid:{}}]",checkUserDto);
        try {
            checkUserInfoService.SelectByMoible(appConfigs.getBizdbIndex(),checkUserDto);
        } catch (BizException e) {
            long endTime = System.currentTimeMillis();
            logger.error("校验手机号接口调用异常!耗时:{},异常信息:{}", (endTime - startTime), e.getMessage(), e);
            return AjaxResult.failed(e.getCode());
        } catch (Exception e) {
            // 记录异常信息
            long endTime = System.currentTimeMillis();
            logger.error("校验手机号接口调用异常!耗时:{},异常信息:{}", (endTime - startTime), e.getMessage(), e);
            return AjaxResult.failed();
        }
        long endTime = System.currentTimeMillis();
        logger.info("校验手机号信息接口调用完成,耗时:{},返回结果:[testEntity:{}]", (endTime - startTime));
        return AjaxResult.success();
    }

    @ValidateGroup(fileds =
            { @ValidateFiled(index = 0, filedName = "userName",notNull = true, icode = BizCode.CLIENT_ERROR, message = "用户名"),})
    @PostMapping("/byname")
    @ResponseBody
    public AjaxResult SelectByUserName(@RequestBody SelectUserDto checkUserDto) {
        long startTime = System.currentTimeMillis();
        logger.info("校验用户名接口调用开始,参数:[uid:{}}]",checkUserDto);
        try {
            checkUserInfoService.SelectByUserName(appConfigs.getBizdbIndex(),checkUserDto);
        } catch (BizException e) {
            long endTime = System.currentTimeMillis();
            logger.error("校验用户名接口调用异常!耗时:{},异常信息:{}", (endTime - startTime), e.getMessage(), e);
            return AjaxResult.failed(e.getCode());
        } catch (Exception e) {
            // 记录异常信息
            long endTime = System.currentTimeMillis();
            logger.error("校验用户名接口调用异常!耗时:{},异常信息:{}", (endTime - startTime), e.getMessage(), e);
            return AjaxResult.failed();
        }
        long endTime = System.currentTimeMillis();
        logger.info("校验用户名接口调用完成,耗时:{},返回结果:[testEntity:{}]", (endTime - startTime));
        return AjaxResult.success();
    }
}
