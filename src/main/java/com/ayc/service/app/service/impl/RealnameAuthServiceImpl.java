package com.ayc.service.app.service.impl;

import com.ayc.framework.datasource.annotation.DataSource;
import com.ayc.framework.util.DozerUtil;
import com.ayc.service.app.dao.mapper.AycRealnameAuthMapper;
import com.ayc.service.app.dto.RealnameAuthDto;
import com.ayc.service.app.entity.AycRealnameAuthEntity;
import com.ayc.service.app.service.RealnameAuthService;
import com.ayc.service.app.vo.RealnameAuthVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service("realnameAuthService")
public class RealnameAuthServiceImpl implements RealnameAuthService {
    @Autowired
    private AycRealnameAuthMapper realnameAuthEntityMapper;
    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public int insertRealname(@DataSource(field = "rCashId") Integer rCashId, RealnameAuthDto realnameAuthDto) {
        AycRealnameAuthEntity realnameAuthEntity = new AycRealnameAuthEntity();
        DozerUtil.map(realnameAuthDto,realnameAuthEntity);
        realnameAuthEntity.setStatus(Byte.valueOf("0"));
        realnameAuthEntity.setCreatedAt(new Date());
        realnameAuthEntity.setUpdatedAt(new Date());
        return realnameAuthEntityMapper.insert(realnameAuthEntity);
    }

    @Override
    public RealnameAuthVo getRealnameAuth(@DataSource(field = "rCashId") Integer rCashId, RealnameAuthDto realnameAuthDto) {
        RealnameAuthVo realnameAuthVo = new RealnameAuthVo();
        AycRealnameAuthEntity realnameAuthEntity = realnameAuthEntityMapper.selectByUid(realnameAuthDto.getUid());
        DozerUtil.map(realnameAuthEntity,realnameAuthVo);
        return realnameAuthVo;
    }
}
