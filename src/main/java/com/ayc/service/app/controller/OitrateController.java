package com.ayc.service.app.controller;

import com.ayc.framework.aspect.AppAuth;
import com.ayc.framework.aspect.ValidateFiled;
import com.ayc.framework.aspect.ValidateGroup;
import com.ayc.framework.common.AjaxResult;
import com.ayc.framework.common.BizCode;
import com.ayc.framework.common.BizException;
import com.ayc.framework.util.WebContextUtils;
import com.ayc.service.app.configs.AppBizCode;
import com.ayc.service.app.configs.AppConfigs;
import com.ayc.service.app.security.AppSessionUser;
import com.ayc.service.app.service.OitRateService;
import com.ayc.service.app.vo.OitRateVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 *  汇率记录
 */
@Controller
@RequestMapping("/oitRate")
public class OitrateController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private AppConfigs appConfigs;
    @Autowired
    private OitRateService oitRateService;
    /**
     * 汇率列表展示
     */
    @ValidateGroup(fileds = {
            @ValidateFiled(index = 0, notNull = true, icode = BizCode.CLIENT_ERROR, message = "token"),
    })
    @AppAuth(index = 0, clazz = AppSessionUser.class)
    @PostMapping("/list")
    @ResponseBody
    public AjaxResult queryList(@RequestHeader String token){
        long startTime = System.currentTimeMillis();
        logger.info("获取汇率列表接口调用开始,参数:[dto:{}}]");
        List<OitRateVo> list = null;
        try {
            AppSessionUser appSessionUser = WebContextUtils.getCurrentSessionUser();
            if(appSessionUser == null){
                throw new BizException(AppBizCode.USER_TOKEN_ERROR);
            }
            list = oitRateService.queryList(appConfigs.getBizdbIndex());
        }catch (BizException e){
            long endTime = System.currentTimeMillis();
            logger.error("获取汇率列表接口调用异常!耗时:{},异常信息:{}", (endTime - startTime), e.getMessage(), e);
            return AjaxResult.failed();
        } catch (Exception e) {
            // 记录异常信息
            long endTime = System.currentTimeMillis();
            logger.error("获取汇率列表接口调用异常!耗时:{},异常信息:{}", (endTime - startTime), e.getMessage(), e);
            return AjaxResult.failed();
        }
        long endTime = System.currentTimeMillis();
        logger.info("获取汇率列表接口调用完成,耗时:{},返回结果:[List<OitRateVo>:{}]", (endTime - startTime), list);
        return AjaxResult.success().putObj(list);
    }

    /**
     * 最近一次汇率展示
     */
    @ValidateGroup(fileds = {
            @ValidateFiled(index = 0, notNull = true, icode = BizCode.CLIENT_ERROR, message = "token"),
    })
    @AppAuth(index = 0, clazz = AppSessionUser.class)
    @PostMapping("/near")
    @ResponseBody
    public AjaxResult queryRate(@RequestHeader String token){
        long startTime = System.currentTimeMillis();
        logger.info("获取汇率接口调用开始,参数:[dto:{}}]");
        OitRateVo oitRateVo = null;
        try {
            AppSessionUser appSessionUser = WebContextUtils.getCurrentSessionUser();
            if(appSessionUser == null){
                throw new BizException(AppBizCode.USER_TOKEN_ERROR);
            }
            oitRateVo = oitRateService.queryOitRate(appConfigs.getBizdbIndex());
        }catch (BizException e){
            long endTime = System.currentTimeMillis();
            logger.error("获取汇率接口调用异常!耗时:{},异常信息:{}", (endTime - startTime), e.getMessage(), e);
            return AjaxResult.failed();
        } catch (Exception e) {
            // 记录异常信息
            long endTime = System.currentTimeMillis();
            logger.error("获取汇率接口调用异常!耗时:{},异常信息:{}", (endTime - startTime), e.getMessage(), e);
            return AjaxResult.failed();
        }
        long endTime = System.currentTimeMillis();
        logger.info("获取汇率接口调用完成,耗时:{},返回结果:[List<OitRateVo>:{}]", (endTime - startTime), oitRateVo);
        return AjaxResult.success().putObj(oitRateVo);
    }
}
