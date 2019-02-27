package com.ayc.service.app.dao.mapper;

import com.ayc.framework.dao.IBaseMapper;
import com.ayc.service.app.entity.AycOitRateEntity;

import java.util.List;

public interface AycOitRateEntityMapper extends IBaseMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AycOitRateEntity record);

    int insertSelective(AycOitRateEntity record);

    AycOitRateEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AycOitRateEntity record);

    int updateByPrimaryKey(AycOitRateEntity record);

    List<AycOitRateEntity> selectAll();

    AycOitRateEntity selectByNear();
}