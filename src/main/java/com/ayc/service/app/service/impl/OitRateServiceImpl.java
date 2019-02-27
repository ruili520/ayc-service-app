package com.ayc.service.app.service.impl;

import com.ayc.framework.datasource.annotation.DataSource;
import com.ayc.framework.util.DozerUtil;
import com.ayc.service.app.dao.mapper.AycOitRateEntityMapper;
import com.ayc.service.app.entity.AycOitRateEntity;
import com.ayc.service.app.service.OitRateService;
import com.ayc.service.app.vo.OitRateVo;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service("oitRateService")
public class OitRateServiceImpl implements OitRateService {
    @Autowired
    private AycOitRateEntityMapper aycOitRateEntityMapper;
    @Override
    public List<OitRateVo> queryList(@DataSource(field = "rCashId") Integer rCashId) {
        List<OitRateVo> olist = null;
        OitRateVo oitRateVo = null;
        List<AycOitRateEntity> list = aycOitRateEntityMapper.selectAll();
        if(list != null && list.size() > 0){
            olist = Lists.newArrayList();
        for (AycOitRateEntity aycOitRateEntity : list) {
            oitRateVo = new OitRateVo();
            double cash = Double.parseDouble(aycOitRateEntity.getCash());
            double num = Double.parseDouble(aycOitRateEntity.getNum());
            double huiLv = 0.0;
            if(num != 0){
                huiLv = cash/num;
            }
            huiLv = Double.parseDouble(String.format("%.4f", huiLv));
            oitRateVo.setHuiLv(huiLv);
            DozerUtil.map(aycOitRateEntity,oitRateVo);
            olist.add(oitRateVo);
        }
        }
        return olist;
    }

    @Override
    public OitRateVo queryOitRate(@DataSource(field = "rCashId") Integer rCashId) {
        AycOitRateEntity aycOitRateEntity = aycOitRateEntityMapper.selectByNear();
        double cash = Double.parseDouble(aycOitRateEntity.getCash());
        double num = Double.parseDouble(aycOitRateEntity.getNum());
        double huiLv = 0.0;
        if(num != 0){
            huiLv = cash/num;
        }
        huiLv = Double.parseDouble(String.format("%.4f", huiLv));
        OitRateVo oitRateVo = new OitRateVo();
        DozerUtil.map(aycOitRateEntity,oitRateVo);
        oitRateVo.setHuiLv(huiLv);
        return oitRateVo;
    }
}
