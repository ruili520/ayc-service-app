package com.ayc.service.app.dao.mapper;

import com.ayc.service.app.dto.ArticleTimesDto;
import com.ayc.service.app.entity.ArticleTimesEntity;
import com.ayc.framework.dao.IBaseMapper;

public interface ArticleTimesMapper extends IBaseMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ArticleTimesEntity record);

    int insertSelective(ArticleTimesEntity record);

    ArticleTimesEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ArticleTimesEntity record);

    int updateByPrimaryKey(ArticleTimesEntity record);

    ArticleTimesEntity selectByArticleTimeDto(ArticleTimesDto timesDto);
}