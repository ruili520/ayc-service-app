package com.ayc.service.app.controller;

import com.ayc.framework.aspect.AppAuth;
import com.ayc.framework.aspect.ValidateFiled;
import com.ayc.framework.aspect.ValidateGroup;
import com.ayc.framework.common.AjaxResult;
import com.ayc.framework.common.BizCode;
import com.ayc.framework.common.BizException;
import com.ayc.framework.util.DozerUtil;
import com.ayc.framework.util.HttpClientUtils;
import com.ayc.framework.util.WebContextUtils;
import com.ayc.service.api.dto.SmsDto;
import com.ayc.service.app.configs.AppBizCode;
import com.ayc.service.app.configs.AppConfigs;
import com.ayc.service.app.security.AppSessionUser;
import com.ayc.service.app.service.AycUserService;
import com.ayc.service.app.service.CodeSaveRedisService;
import com.ayc.service.app.service.TokenSaveRedisService;
import com.ayc.service.uc.dto.UserBindPhoneDto;
import com.ayc.service.uc.dto.AycUserLoginDto;
import com.ayc.service.uc.dto.AycUserRegisterDto;
import com.ayc.service.uc.entity.AycUserEntity;
import com.ayc.service.uc.service.UcAycUserDetailService;
import com.ayc.service.uc.service.UcAycUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用户控制器
 *
 * @author wwq
 * @version 1.0
 * @createDate 2019/2/19 11:04
 */
@Controller
@RequestMapping("/user")
public class AycUserController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private AppConfigs appConfigs;
    @Autowired
    private UcAycUserService ucAycUserService;
    @Autowired
    private AycUserService aycUserService;
    @Autowired
    private UcAycUserDetailService aycUserDetailService;
    @Autowired
    private TokenSaveRedisService tokenSaveRedisService;
    @Autowired
    private CodeSaveRedisService codeSaveRedisService;

    /**
     * 注册用户
     * @param aycUserRegisterDto
     * @return
     */
    @ValidateGroup(fileds = {
            @ValidateFiled(index = 0, notNull = false, icode = BizCode.CLIENT_ERROR, message = "版本号"),
            @ValidateFiled(index = 1, notNull = false, icode = BizCode.CLIENT_ERROR, message = "用户id"),
    })
    @PostMapping("/register")
    @ResponseBody
    public AjaxResult registerUser(@RequestBody AycUserRegisterDto aycUserRegisterDto) {
        long startTime = System.currentTimeMillis();
        logger.info("注册用户信息接口调用开始,参数:[uid:{}}]");
        SmsDto smsDto = new SmsDto();
        DozerUtil.map(aycUserRegisterDto,smsDto);
        smsDto.setType(1);
        try {
            codeSaveRedisService.getSmsCode(smsDto);
            ucAycUserService.registerUser(appConfigs.getBizdbIndex(),aycUserRegisterDto);
            AycUserEntity aycUserEntity = ucAycUserService.selectUserByMobile(appConfigs.getBizdbIndex(),aycUserRegisterDto.getMobile());
            aycUserRegisterDto.setUid(aycUserEntity.getId());
            aycUserDetailService.registerUser(appConfigs.getBizdbIndex(),aycUserRegisterDto);
        } catch (BizException e) {
            long endTime = System.currentTimeMillis();
            logger.error("注册用户接口调用异常!耗时:{},异常信息:{}", (endTime - startTime), e.getMessage(), e);
            return AjaxResult.failed(e.getCode());
        } catch (Exception e) {
            // 记录异常信息
            long endTime = System.currentTimeMillis();
            logger.error("注册用户接口调用异常!耗时:{},异常信息:{}", (endTime - startTime), e.getMessage(), e);
            return AjaxResult.failed();
        }
        long endTime = System.currentTimeMillis();
        logger.info("获取用户作者信息接口调用完成,耗时:{},返回结果:[testEntity:{}]", (endTime - startTime));
        return AjaxResult.success().putObj(aycUserRegisterDto);
    }

    @PostMapping("/login")
    @ResponseBody
    public AjaxResult loginUser(@RequestBody AycUserLoginDto aycUserLoginDto,HttpServletRequest request) {
        long startTime = System.currentTimeMillis();
        logger.info("用户登录接口调用开始,参数:[uid:{}}]",aycUserLoginDto);
        String token = null;
        try {
            token = aycUserService.loginUser(appConfigs.getBizdbIndex(),aycUserLoginDto,request);
        } catch (BizException e) {
            long endTime = System.currentTimeMillis();
            logger.error("用户登录接口调用异常!耗时:{},异常信息:{}", (endTime - startTime), e.getMessage(), e);
            return AjaxResult.failed(e.getCode());
        } catch (Exception e) {
            // 记录异常信息
            long endTime = System.currentTimeMillis();
            logger.error("用户登录接口调用异常!耗时:{},异常信息:{}", (endTime - startTime), e.getMessage(), e);
            return AjaxResult.failed();
        }
        long endTime = System.currentTimeMillis();
        logger.info("用户登录接口调用完成,耗时:{},返回结果:[testEntity:{}]", (endTime - startTime));
        return AjaxResult.success().putObj(token);
    }

    @PostMapping("/wxlogin")
    @ResponseBody
    public AjaxResult wxloginUser(@RequestBody AycUserLoginDto aycUserLoginDto,HttpServletRequest request) {
        long startTime = System.currentTimeMillis();
        logger.info("用户登录接口调用开始,参数:[uid:{}}]",aycUserLoginDto);
        String token = null;
        try {
            token = aycUserService.wxloginUser(appConfigs.getBizdbIndex(),aycUserLoginDto,request);
        } catch (BizException e) {
            long endTime = System.currentTimeMillis();
            logger.error("用户登录接口调用异常!耗时:{},异常信息:{}", (endTime - startTime), e.getMessage(), e);
            return AjaxResult.failed(e.getCode());
        } catch (Exception e) {
            // 记录异常信息
            long endTime = System.currentTimeMillis();
            logger.error("用户登录接口调用异常!耗时:{},异常信息:{}", (endTime - startTime), e.getMessage(), e);
            return AjaxResult.failed();
        }
        long endTime = System.currentTimeMillis();
        logger.info("用户登录接口调用完成,耗时:{},返回结果:[testEntity:{}]", (endTime - startTime));
        return AjaxResult.success().putObj(token);
    }

    @GetMapping("/wxlogin")
    public String wxLogin(String openId , HttpServletRequest request, HttpServletResponse response){
        return null;
    }

    /**
     * 获取用户作者信息
     * @param
     * @return
     */
//    @ValidateGroup(fileds = {
//            @ValidateFiled(index = 0, notNull = true, icode = BizCode.CLIENT_ERROR, message = "版本号"),
//            @ValidateFiled(index = 1, notNull = true, icode = BizCode.CLIENT_ERROR, message = "用户id"),
//    })
    @AppAuth(index = 0, clazz = AppSessionUser.class)
    @PostMapping("/isAuthor")
    @ResponseBody
    public AjaxResult getUserIsAuthor(@RequestHeader String token) {
        long startTime = System.currentTimeMillis();
        logger.info("获取用户作者信息接口调用开始,参数:[uid:{}}]", token);
        boolean isAuthor = false;
        try {
            AppSessionUser appSessionUser = WebContextUtils.getCurrentSessionUser();
            if(appSessionUser==null){
                throw new BizException(AppBizCode.USER_TOKEN_ERROR);
            }
            isAuthor = aycUserDetailService.getUserIsAuthor(appConfigs.getBizdbIndex(), appSessionUser.getUserId());
        } catch (BizException e) {
            long endTime = System.currentTimeMillis();
            logger.error("获取用户作者信息接口调用异常!耗时:{},异常信息:{}", (endTime - startTime), e.getMessage(), e);
            return AjaxResult.failed(e.getCode());
        } catch (Exception e) {
            // 记录异常信息
            long endTime = System.currentTimeMillis();
            logger.error("获取用户作者信息接口调用异常!耗时:{},异常信息:{}", (endTime - startTime), e.getMessage(), e);
            return AjaxResult.failed();
        }
        long endTime = System.currentTimeMillis();
        logger.info("获取用户作者信息接口调用完成,耗时:{},返回结果:[testEntity:{}]", (endTime - startTime));
        return AjaxResult.success().putObj(isAuthor);
    }

    /**
     * 绑定手机号
     * @author wwq
     * @param
     */
    @ValidateGroup(fileds = {
            @ValidateFiled(index = 0, notNull = true, icode = BizCode.CLIENT_ERROR, message = "token"),
            @ValidateFiled(index = 1, filedName = "uid", notNull = true, icode = BizCode.CLIENT_ERROR, message = "用户id"),
            @ValidateFiled(index = 1, filedName = "mobile", notNull = true, icode = BizCode.CLIENT_ERROR, message = "手机号"),
            @ValidateFiled(index = 1, filedName = "smsCode", notNull = true, icode = BizCode.CLIENT_ERROR, message = "验证码"),
            @ValidateFiled(index = 1, filedName = "password", notNull = true, icode = BizCode.CLIENT_ERROR, message = "密码"),
    })
    @AppAuth(index = 0, clazz = AppSessionUser.class)
    @PostMapping("/bindPhone")
    @ResponseBody
    public AjaxResult bindPhone(@RequestHeader String token, @RequestBody UserBindPhoneDto userBindPhoneDto) {
        long startTime = System.currentTimeMillis();
        logger.info("绑定手机号接口调用开始,参数:[dto:{}}]");
        try {
            AppSessionUser appSessionUser = WebContextUtils.getCurrentSessionUser();
            if(appSessionUser == null){
                throw new BizException(AppBizCode.USER_TOKEN_ERROR);
            }
            if(userBindPhoneDto.getUid() == null){
                userBindPhoneDto.setUid(appSessionUser.getUserId());
            }
            aycUserService.bindPhone(appConfigs.getBizdbIndex(), userBindPhoneDto);
        } catch (BizException e) {
            long endTime = System.currentTimeMillis();
            logger.error("绑定手机号接口调用异常!耗时:{},异常信息:{}", (endTime - startTime), e.getMessage(), e);
            return AjaxResult.failed(e.getCode());
        } catch (Exception e) {
            // 记录异常信息
            long endTime = System.currentTimeMillis();
            logger.error("绑定手机号接口调用异常!耗时:{},异常信息:{}", (endTime - startTime), e.getMessage(), e);
            return AjaxResult.failed();
        }
        long endTime = System.currentTimeMillis();
        logger.info("绑定手机号接口调用完成,耗时:{},返回结果:[testEntity:{}]", (endTime - startTime));
        return AjaxResult.success();
    }

    /**
     * 解绑微信号
     * @author wwq
     * @param
     */
    @ValidateGroup(fileds = {
            @ValidateFiled(index = 0, notNull = true, icode = BizCode.CLIENT_ERROR, message = "token"),
            @ValidateFiled(index = 1, notNull = true, icode = BizCode.CLIENT_ERROR, message = "用户id"),
    })
    @AppAuth(index = 0, clazz = AppSessionUser.class)
    @PostMapping("/unbindWx")
    @ResponseBody
    public AjaxResult unbindWx(@RequestHeader String token, Integer uid) {
        long startTime = System.currentTimeMillis();
        logger.info("解绑微信接口调用开始,参数:[dto:{}}]");
        try {
            AppSessionUser appSessionUser = WebContextUtils.getCurrentSessionUser();
            if(appSessionUser == null){
                throw new BizException(AppBizCode.USER_TOKEN_ERROR);
            }
            aycUserService.unbindWx(appConfigs.getBizdbIndex(), uid);
        } catch (BizException e) {
            long endTime = System.currentTimeMillis();
            logger.error("解绑微信接口调用异常!耗时:{},异常信息:{}", (endTime - startTime), e.getMessage(), e);
            return AjaxResult.failed(e.getCode());
        } catch (Exception e) {
            // 记录异常信息
            long endTime = System.currentTimeMillis();
            logger.error("解绑微信接口调用异常!耗时:{},异常信息:{}", (endTime - startTime), e.getMessage(), e);
            return AjaxResult.failed();
        }
        long endTime = System.currentTimeMillis();
        logger.info("解绑微信接口调用完成,耗时:{},返回结果:[{}]", (endTime - startTime));
        return AjaxResult.success();
    }



}
