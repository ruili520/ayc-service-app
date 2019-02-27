package com.ayc.service.app.dao.mapper;

import com.ayc.framework.dao.IBaseMapper;
import com.ayc.service.app.dto.AycOitLogDto;
import com.ayc.service.app.entity.AycOitLogEntity;

import java.util.List;

public interface AycOitLogEntityMapper  extends IBaseMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AycOitLogEntity record);

    int insertSelective(AycOitLogEntity record);

    AycOitLogEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AycOitLogEntity record);

    int updateByPrimaryKey(AycOitLogEntity record);
    /**
     * @Title:
     * @Description: //TODO 根据用户id 查询oit列表
     * @Author: 程亚磊
     * @Date: 2019/2/25 15:02
     */
    List<AycOitLogEntity> selectAll(AycOitLogDto aycOitLogDto);
}