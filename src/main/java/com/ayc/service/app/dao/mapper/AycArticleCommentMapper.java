package com.ayc.service.app.dao.mapper;

import com.ayc.framework.dao.IBaseMapper;
import com.ayc.service.app.entity.AycArticleCommentEntity;
import com.ayc.service.app.vo.ArtcleCommentVo;

import java.util.List;

public interface AycArticleCommentMapper extends IBaseMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AycArticleCommentEntity record);

    int insertSelective(AycArticleCommentEntity record);

    AycArticleCommentEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AycArticleCommentEntity record);

    int updateByPrimaryKey(AycArticleCommentEntity record);

    List<AycArticleCommentEntity> selectAll(Integer artcleId);
}