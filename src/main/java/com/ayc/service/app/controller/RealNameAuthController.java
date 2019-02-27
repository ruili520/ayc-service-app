package com.ayc.service.app.controller;

import com.alibaba.fastjson.JSON;
import com.ayc.framework.aspect.AppAuth;
import com.ayc.framework.aspect.ValidateFiled;
import com.ayc.framework.aspect.ValidateGroup;
import com.ayc.framework.common.AjaxResult;
import com.ayc.framework.common.BizCode;
import com.ayc.framework.common.BizException;
import com.ayc.framework.util.WebContextUtils;
import com.ayc.service.app.configs.AppBizCode;
import com.ayc.service.app.configs.AppConfigs;
import com.ayc.service.app.dto.RealnameAuthDto;
import com.ayc.service.app.security.AppSessionUser;
import com.ayc.service.app.service.RealnameAuthService;
import com.ayc.service.app.vo.RealnameAuthVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * 实名认证
 */
@Controller
@RequestMapping("/realNameAuth")
public class RealNameAuthController {
    private Logger logger = LoggerFactory.getLogger(AycOitLogController.class);

    @Autowired
    private AppConfigs appConfigs;
    @Autowired
    private RealnameAuthService realnameAuthService;
    /**
     * 实名认证（增加）
     */
    @ValidateGroup(fileds = {
            @ValidateFiled(index = 0, notNull = true, icode = BizCode.CLIENT_ERROR, message = "token"),
            @ValidateFiled(index = 1, notNull = true, icode = BizCode.CLIENT_ERROR, message = "表单"),
            @ValidateFiled(index = 1, filedName = "uid", notNull = false, icode = BizCode.CLIENT_ERROR, message = "认证用户id"),
            @ValidateFiled(index = 1, filedName = "realname", notNull = true, icode = BizCode.CLIENT_ERROR, message = "真实姓名"),
            @ValidateFiled(index = 1, filedName = "cardNumber", notNull = true, icode = BizCode.CLIENT_ERROR, message = "身份证号"),
    })
    @AppAuth(index = 0, clazz = AppSessionUser.class)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult add(@RequestHeader String token,@RequestBody RealnameAuthDto realnameAuthDto){
        long startTime = System.currentTimeMillis();
        logger.info("添加实名认证接口调用开始,参数:[dto:{}}]", JSON.toJSONString(realnameAuthDto));
        try {
            AppSessionUser appSessionUser = WebContextUtils.getCurrentSessionUser();
            if(appSessionUser == null){
                throw new BizException(AppBizCode.USER_TOKEN_ERROR);
            }
            if(realnameAuthDto.getUid() == null){
                realnameAuthDto.setUid(appSessionUser.getUserId());
            }
            realnameAuthService.insertRealname(appConfigs.getBizdbIndex(),realnameAuthDto);
        } catch (BizException e) {
            long endTime = System.currentTimeMillis();
            logger.error("添加实名认证接口调用异常!耗时:{},异常信息:{}", (endTime - startTime), e.getMessage(), e);
            return AjaxResult.failed();
        } catch (Exception e) {
            // 记录异常信息
            long endTime = System.currentTimeMillis();
            logger.error("添加实名认证接口调用异常!耗时:{},异常信息:{}", (endTime - startTime), e.getMessage(), e);
            return AjaxResult.failed();
        }
        long endTime = System.currentTimeMillis();
        logger.info("添加实名认证接口调用完成,耗时:{},返回结果:[AycArticleCommentEntity:{}]", (endTime - startTime));
        return AjaxResult.success();
    }

    /**
     * 认证状态
     */
    @ValidateGroup(fileds = {
            @ValidateFiled(index = 0, notNull = true, icode = BizCode.CLIENT_ERROR, message = "token"),
            @ValidateFiled(index = 1, notNull = true, icode = BizCode.CLIENT_ERROR, message = "表单"),
            @ValidateFiled(index = 1, filedName = "uid", notNull = false, icode = BizCode.CLIENT_ERROR, message = "认证用户id"),
    })
    @AppAuth(index = 0, clazz = AppSessionUser.class)
    @PostMapping("/status")
    @ResponseBody
    public AjaxResult findStatus(@RequestHeader String token,@RequestBody RealnameAuthDto realnameAuthDto){
        long startTime = System.currentTimeMillis();
        logger.info("添加认证状态接口调用开始,参数:[dto:{}}]", JSON.toJSONString(realnameAuthDto));
        RealnameAuthVo realnameAuth = null;
        try {
            AppSessionUser appSessionUser = WebContextUtils.getCurrentSessionUser();
            if(appSessionUser == null){
                throw new BizException(AppBizCode.USER_TOKEN_ERROR);
            }
            if(realnameAuthDto.getUid() == null){
                realnameAuthDto.setUid(appSessionUser.getUserId());
            }
            realnameAuth = realnameAuthService.getRealnameAuth(appConfigs.getBizdbIndex(), realnameAuthDto);
        } catch (BizException e) {
            long endTime = System.currentTimeMillis();
            logger.error("添加认证状态接口调用异常!耗时:{},异常信息:{}", (endTime - startTime), e.getMessage(), e);
            return AjaxResult.failed();
        } catch (Exception e) {
            // 记录异常信息
            long endTime = System.currentTimeMillis();
            logger.error("添加认证状态接口调用异常!耗时:{},异常信息:{}", (endTime - startTime), e.getMessage(), e);
            return AjaxResult.failed();
        }
        long endTime = System.currentTimeMillis();
        logger.info("添加认证状态接口调用完成,耗时:{},返回结果:[AycArticleCommentEntity:{}]", (endTime - startTime),realnameAuth );
        return AjaxResult.success().putObj(realnameAuth);
    }
}
