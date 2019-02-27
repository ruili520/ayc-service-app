package com.ayc.service.app.dao.mapper;

import com.ayc.service.app.dto.ArticleDto;
import com.ayc.service.app.dto.ArticleSearchDto;
import com.ayc.service.app.entity.ArticleEntity;
import com.ayc.framework.dao.IBaseMapper;

import java.util.List;

public interface ArticleMapper extends IBaseMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ArticleEntity record);

    int insertSelective(ArticleEntity record);

    List<ArticleEntity> queryList(ArticleDto articleDto);

    ArticleEntity getArtcledesc(Integer id);

    ArticleEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ArticleEntity record);

    int updateByPrimaryKeyWithBLOBs(ArticleEntity record);

    int updateByPrimaryKey(ArticleEntity record);

    /**
     * 获取我的收藏列表
     * @param articleSearchDto
     * @return
     */
    List<ArticleEntity> listMyCollects(ArticleSearchDto articleSearchDto);
}