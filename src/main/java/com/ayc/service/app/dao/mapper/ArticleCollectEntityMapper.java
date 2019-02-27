package com.ayc.service.app.dao.mapper;

import com.ayc.service.app.entity.ArticleCollectEntity;
import com.ayc.framework.dao.IBaseMapper;
import org.apache.ibatis.annotations.Param;

public interface ArticleCollectEntityMapper extends IBaseMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ArticleCollectEntity record);

    int insertSelective(ArticleCollectEntity record);

    ArticleCollectEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ArticleCollectEntity record);

    int updateByPrimaryKey(ArticleCollectEntity record);

    /**
     * 通过文章id， 用户id获取用户收藏信息
     * @param articleId
     * @param uid
     * @return
     */
    ArticleCollectEntity selectByArticleAndUser(@Param("articleId") Integer articleId, @Param("uid") Integer uid);
}