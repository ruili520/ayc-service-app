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
import com.ayc.service.app.dto.UserDetailDto;
import com.ayc.service.app.security.AppSessionUser;
import com.ayc.service.app.service.UserDetailService;
import com.ayc.service.app.vo.OitTotalVo;
import com.ayc.service.app.vo.UserDetailVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * 用户详情
 */
@Controller
@RequestMapping("/userDetail")
public class UserDetailController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private AppConfigs appConfigs;
    @Autowired
    private UserDetailService userDetailService;

    /**
     * 用户详情信息
     */
    @ValidateGroup(fileds = {
            @ValidateFiled(index = 0, notNull = true, icode = BizCode.CLIENT_ERROR, message = "token"),
            @ValidateFiled(index = 1, notNull = true, icode = BizCode.CLIENT_ERROR, message = "表单"),
            @ValidateFiled(index = 1, filedName = "uid", notNull = false, icode = BizCode.CLIENT_ERROR, message = "用户id"),
    })
    @AppAuth(index = 0, clazz = AppSessionUser.class)
    @PostMapping("/detail")
    @ResponseBody
    public AjaxResult userDetail(@RequestHeader String token,@RequestBody UserDetailDto userDetailDto){
        long startTime = System.currentTimeMillis();
        logger.info("获取用户详情接口调用开始,参数:[dto:{}}]", JSON.toJSONString(userDetailDto));
        UserDetailVo userDetailVo = null;
        try {
            AppSessionUser appSessionUser = WebContextUtils.getCurrentSessionUser();
            if(appSessionUser == null){
                throw new BizException(AppBizCode.USER_TOKEN_ERROR);
            }
            if(userDetailDto.getUid() == null){
                userDetailDto.setUid(appSessionUser.getUserId());
            }
            userDetailVo = userDetailService.selectUserDetail(appConfigs.getBizdbIndex(),userDetailDto);
        }catch (BizException e){
            long endTime = System.currentTimeMillis();
            logger.error("获取用户详情接口调用异常!耗时:{},异常信息:{}", (endTime - startTime), e.getMessage(), e);
            return AjaxResult.failed();
        } catch (Exception e) {
            // 记录异常信息
            long endTime = System.currentTimeMillis();
            logger.error("获取用户详情接口调用异常!耗时:{},异常信息:{}", (endTime - startTime), e.getMessage(), e);
            return AjaxResult.failed();
        }
        long endTime = System.currentTimeMillis();
        logger.info("获取用户详情接口调用完成,耗时:{},返回结果:[UserDetailVo:{}]", (endTime - startTime), userDetailVo);
        return AjaxResult.success().putObj(userDetailVo);
    }

    @ValidateGroup(fileds = {
            @ValidateFiled(index = 0, notNull = true, icode = BizCode.CLIENT_ERROR, message = "token"),
            @ValidateFiled(index = 1, notNull = true, icode = BizCode.CLIENT_ERROR, message = "表单"),
            @ValidateFiled(index = 1, filedName = "uid", notNull = false, icode = BizCode.CLIENT_ERROR, message = "用户id"),
    })
    @AppAuth(index = 0, clazz = AppSessionUser.class)
    @PostMapping("/oitTotal")
    @ResponseBody
    public AjaxResult userOitTotal(@RequestHeader String token,@RequestBody UserDetailDto userDetailDto){
        long startTime = System.currentTimeMillis();
        logger.info("获取用户oit总额接口调用开始,参数:[dto:{}}]", JSON.toJSONString(userDetailDto));
        OitTotalVo oitTotalVo = null;
        try {
            AppSessionUser appSessionUser = WebContextUtils.getCurrentSessionUser();
            if(appSessionUser == null){
                throw new BizException(AppBizCode.USER_TOKEN_ERROR);
            }
            if(userDetailDto.getUid() == null){
                userDetailDto.setUid(appSessionUser.getUserId());
            }
            oitTotalVo = userDetailService.selectOit(appConfigs.getBizdbIndex(),userDetailDto.getUid());
        }catch (BizException e){
            long endTime = System.currentTimeMillis();
            logger.error("获取用户oit总额接口调用异常!耗时:{},异常信息:{}", (endTime - startTime), e.getMessage(), e);
            return AjaxResult.failed();
        } catch (Exception e) {
            // 记录异常信息
            long endTime = System.currentTimeMillis();
            logger.error("获取用户oit总额接口调用异常!耗时:{},异常信息:{}", (endTime - startTime), e.getMessage(), e);
            return AjaxResult.failed();
        }
        long endTime = System.currentTimeMillis();
        logger.info("获取用户oit总额接口调用完成,耗时:{},返回结果:[UserDetailVo:{}]", (endTime - startTime), oitTotalVo);
        return AjaxResult.success().putObj(oitTotalVo);
    }

}
