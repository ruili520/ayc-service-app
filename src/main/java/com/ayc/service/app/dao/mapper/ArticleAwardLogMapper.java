package com.ayc.service.app.dao.mapper;

import com.ayc.framework.dao.IBaseMapper;
import com.ayc.service.app.dto.ArticleAwardDto;
import com.ayc.service.app.entity.ArticleAwardLogEntity;

import java.util.List;

public interface ArticleAwardLogMapper extends IBaseMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ArticleAwardLogEntity record);

    int insertSelective(ArticleAwardLogEntity record);

    ArticleAwardLogEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ArticleAwardLogEntity record);

    int updateByPrimaryKey(ArticleAwardLogEntity record);

    /**
     * 获取打赏记录列表
     * @param articleAwardDto
     * @return
     */
    List<ArticleAwardLogEntity> listAwardLogs(ArticleAwardDto articleAwardDto);
}