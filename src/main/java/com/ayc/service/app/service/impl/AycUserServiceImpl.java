package com.ayc.service.app.service.impl;

import com.ayc.framework.common.BizCode;
import com.ayc.framework.common.BizException;
import com.ayc.framework.datasource.annotation.DataSource;
import com.ayc.framework.util.DozerUtil;
import com.ayc.framework.util.IPUtil;
import com.ayc.framework.util.MD5Utils;
import com.ayc.framework.util.RandomUntil;
import com.ayc.service.api.dto.SmsDto;
import com.ayc.service.api.enums.TemplateEnum;
import com.ayc.service.app.service.AycUserService;
import com.ayc.service.app.service.CodeSaveRedisService;
import com.ayc.service.app.service.TokenSaveRedisService;
import com.ayc.service.uc.common.enums.AycUserEnums;
import com.ayc.service.uc.configs.UserBizCode;
import com.ayc.service.uc.dao.mapper.AycUserDetailMapper;
import com.ayc.service.uc.dao.mapper.AycUserMapper;
import com.ayc.service.uc.dto.AycUserLoginDto;
import com.ayc.service.uc.dto.UserBindPhoneDto;
import com.ayc.service.uc.entity.AycUserDetailEntity;
import com.ayc.service.uc.entity.AycUserEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * Author:  wwb
 * Date:  2019/2/1814:44
 * Description:用户相关服务--app业务层
 */
@Service("aycUserService")
public class AycUserServiceImpl implements AycUserService {

    @Autowired
    private AycUserMapper aycUserMapper;
    @Autowired
    private AycUserDetailMapper aycUserDetailMapper;
    @Autowired
    private CodeSaveRedisService codeSaveRedisService;
    @Autowired
    private TokenSaveRedisService tokenSaveRedisService;

    @Override
    public String loginUser(Integer rCashId, AycUserLoginDto aycUserLoginDto, HttpServletRequest request) {
        AycUserEntity aycUserEntity = new AycUserEntity();
        aycUserEntity = aycUserMapper.selectUserByMobile(aycUserLoginDto.getMobile());
        if (aycUserEntity==null){
            throw new BizException(UserBizCode.USER_IS_NOT_EXIST);
        }
        if (StringUtils.isNotEmpty(aycUserLoginDto.getSmscode())){
            SmsDto smsDto = new SmsDto();
            DozerUtil.map(aycUserLoginDto,smsDto);
            smsDto.setType(1);
            codeSaveRedisService.getSmsCode(smsDto);
        }else {
            String password = MD5Utils.MD5(MD5Utils.MD5(aycUserLoginDto.getPassword()+aycUserEntity.getSalt()));
            if (!password.equals(aycUserEntity.getPassword())){
                throw new BizException(UserBizCode.USER_PASS_ERROR);
            }
        }
        //判断用户存不存在IP信息，若不存在则为首次登录
        if (aycUserEntity.getIp()==null||aycUserEntity.getIp().equals("")){
            aycUserEntity.setIp(IPUtil.getIP(request));
            aycUserMapper.updateIp(aycUserEntity);
        }
        String token = tokenSaveRedisService.getToken(aycUserEntity);
        return token;
    }

    @Override
    public String wxloginUser(Integer rCashId, AycUserLoginDto aycUserLoginDto, HttpServletRequest request) {
        AycUserEntity aycUserEntity = new AycUserEntity();
        aycUserEntity = aycUserMapper.selectUserByOpenid(aycUserLoginDto.getOpenid());
        if (aycUserEntity==null){
            //获取微信信息保存到数据库
        }
        //判断用户存不存在IP信息，若不存在则为首次登录
        if (aycUserEntity.getIp()==null||aycUserEntity.getIp().equals("")){
            aycUserEntity.setIp(IPUtil.getIP(request));
            aycUserMapper.updateIp(aycUserEntity);
        }
        String token = tokenSaveRedisService.getToken(aycUserEntity);
        return token;
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    @Override
    public void bindPhone(@DataSource(field = "rCashId") Integer rCashId, UserBindPhoneDto aycUserDto) {
        // 比较验证码
        SmsDto smsDto =new SmsDto();
        DozerUtil.map(aycUserDto,smsDto);
        smsDto.setType(TemplateEnum.BIND_MOBILE.getType());
        codeSaveRedisService.getSmsCode(smsDto);

        // 新绑定的手机号是否存在
        AycUserEntity mobile = aycUserMapper.selectUserByMobile(aycUserDto.getMobile());
        if (mobile != null){
            throw new BizException(UserBizCode.MOBILE_IS_NOEXIST);
        }

        // 获取当前用户
        AycUserEntity user = aycUserMapper.getUserDetail(aycUserDto.getUid());
        if (user == null){
            throw new BizException(UserBizCode.USER_IS_NOT_EXIST);
        }

        // 盐值
        String salt = RandomUntil.saltCode();

        // 修改用户资料   手机号，密码，盐值；用户类型改为作者
        user.setMobile(aycUserDto.getMobile());
        user.setSalt(salt);
        user.setPassword(MD5Utils.MD5(MD5Utils.MD5(aycUserDto.getPassword() + salt)));
        aycUserMapper.updateByPrimaryKeySelective(user);

        AycUserDetailEntity userDetailEntity = user.getAycUserDetailEntity();
        userDetailEntity.setType(AycUserEnums.Types.AUTHOR.getCode());
        userDetailEntity.setUpdatedAt(new Date());
        aycUserDetailMapper.updateByPrimaryKeySelective(userDetailEntity);
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    @Override
    public void unbindWx(@DataSource(field = "rCashId") Integer rCashId, Integer uid) {
        // 获取用户
        AycUserEntity user = aycUserMapper.selectByPrimaryKey(uid);
        if (user == null){
            throw new BizException(UserBizCode.USER_IS_NOT_EXIST);
        }

        user.setWechat("");
        user.setOpenid("");
        int updateCode = aycUserMapper.updateEmptyByPrimaryKeySelective(user);
        if (updateCode == 0){
            throw new BizException(BizCode.BUSI_ERROR);
        }
    }
}
