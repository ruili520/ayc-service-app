package com.ayc.service.app.service.impl;

import com.ayc.framework.common.BizCode;
import com.ayc.framework.common.BizException;
import com.ayc.framework.datasource.annotation.DataSource;
import com.ayc.framework.util.DozerUtil;
import com.ayc.service.app.dao.mapper.AycOitRateEntityMapper;
import com.ayc.service.app.dto.UserDetailDto;
import com.ayc.service.app.entity.AycOitRateEntity;
import com.ayc.service.app.service.UserDetailService;
import com.ayc.service.app.vo.OitTotalVo;
import com.ayc.service.app.vo.UserDetailVo;
import com.ayc.service.uc.dao.mapper.AycUserDetailMapper;
import com.ayc.service.uc.entity.AycUserDetailEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service("userDetailService")
public class UserDetailServiceImpl implements UserDetailService {
    @Autowired
    private AycUserDetailMapper userDetailMapper;
    @Autowired
    private AycOitRateEntityMapper oitRateEntityMapper;
    @Override
    public UserDetailVo selectUserDetail(@DataSource(field = "rCashId") Integer rCashId, UserDetailDto userDetailDto) {
        UserDetailVo userDetailVo = new UserDetailVo();
        AycUserDetailEntity userDetail = new AycUserDetailEntity();
        userDetail.setUid(userDetailDto.getUid());
        userDetail = userDetailMapper.selectByEntity(userDetail);
        if(userDetail == null){
            throw new BizException(BizCode.SERVER_ERROR);
        }
        AycOitRateEntity aycOitRateEntity = oitRateEntityMapper.selectByNear();
        double cash = Double.parseDouble(aycOitRateEntity.getCash());
        double num = Double.parseDouble(aycOitRateEntity.getNum());
        double huiLv = 0.0;
        if(num != 0){
            huiLv = cash/num;
        }
        BigDecimal add = userDetail.getOitNum().add(userDetail.getUseOitNum());
        Double chy = add.multiply(BigDecimal.valueOf(huiLv)).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
        userDetailVo.setChy(chy);
        DozerUtil.map(userDetail,userDetailVo);
        return userDetailVo;
    }

    @Override
    public OitTotalVo selectOit(@DataSource(field = "rCashId") Integer rCashId, Integer uid) {
        AycUserDetailEntity userDetail = new AycUserDetailEntity();
        userDetail.setUid(uid);
        userDetail = userDetailMapper.selectByEntity(userDetail);
        if(userDetail == null){
            throw new BizException(BizCode.SERVER_ERROR);
        }
        AycOitRateEntity aycOitRateEntity = oitRateEntityMapper.selectByNear();
        double cash = Double.parseDouble(aycOitRateEntity.getCash());
        double num = Double.parseDouble(aycOitRateEntity.getNum());
        double huiLv = 0.0;
        if(num != 0){
            huiLv = cash/num;
        }
        OitTotalVo oitTotalVo =new OitTotalVo();
        oitTotalVo.setBalance(userDetail.getBalance());
        oitTotalVo.setOitNum(userDetail.getOitNum());
        oitTotalVo.setUseOitNum(userDetail.getUseOitNum());
        BigDecimal oitTotal = userDetail.getOitNum().add(userDetail.getUseOitNum());
        double oitChy = oitTotal.multiply(BigDecimal.valueOf(huiLv)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        oitTotalVo.setOitTotal(oitTotal);
        oitTotalVo.setOitChy(oitChy);
        double useOitChy = userDetail.getUseOitNum().multiply(BigDecimal.valueOf(huiLv)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        oitTotalVo.setUseOitChy(useOitChy);
        return oitTotalVo;
    }
}
