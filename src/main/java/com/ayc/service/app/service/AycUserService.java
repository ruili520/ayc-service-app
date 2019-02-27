package com.ayc.service.app.service;

import com.ayc.service.uc.dto.AycUserLoginDto;
import com.ayc.service.uc.dto.UserBindPhoneDto;

import javax.servlet.http.HttpServletRequest;

/**
 * Author:  wwb
 * Date:  2019/2/18 14:43
 * Description:用户相关服务--app业务层
 */
public interface AycUserService {
    /**
     * 用户登录--手机密码
     * @param rCashId
     * @param aycUserLoginDto
     * @param request
     * @return
     */
    String loginUser(Integer rCashId, AycUserLoginDto aycUserLoginDto, HttpServletRequest request);

    /**
     * 用户登录--微信登录
     * @param rCashId
     * @param aycUserLoginDto
     * @param request
     * @return
     */
    String wxloginUser(Integer rCashId, AycUserLoginDto aycUserLoginDto, HttpServletRequest request);

    /**
     * 绑定手机号
     * @param rCashId
     * @param aycUserDetailDto
     * @return
     */
    void bindPhone(Integer rCashId, UserBindPhoneDto aycUserDetailDto);

    /**
     * 解绑微信号
     * @param rCashId
     * @param uid
     * @return
     */
    void unbindWx(Integer rCashId, Integer uid);

}
