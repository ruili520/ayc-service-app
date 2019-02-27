package com.ayc.service.app.dao.mapper;

import com.ayc.framework.dao.IBaseMapper;
import com.ayc.service.app.dto.FinancialDto;
import com.ayc.service.app.entity.AycFinancialEntity;

import java.util.List;

public interface AycFinancialMapper extends IBaseMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AycFinancialEntity record);

    int insertSelective(AycFinancialEntity record);

    AycFinancialEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AycFinancialEntity record);

    int updateByPrimaryKey(AycFinancialEntity record);

    /**
     * @Title:
     * @Description: //TODO 查询所有的现金明细记录
     * @Author: 程亚磊
     * @Date: 2019/2/25 13:16
     */
    List<AycFinancialEntity> selectAll(FinancialDto financialDto);
}