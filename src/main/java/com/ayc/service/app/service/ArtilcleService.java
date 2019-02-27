package com.ayc.service.app.service;


import com.ayc.service.app.dto.ArticleDto;
import com.ayc.service.app.dto.ArticleSearchDto;
import com.ayc.service.app.dto.ArticleTimesDto;
import com.ayc.service.app.entity.ArticleEntity;
import com.ayc.service.app.vo.ArticleListVo;
import com.ayc.service.app.vo.SearchResultVo;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * Author:  wwb
 * Date:  2019/2/1114:02
 * Description:
 */
public interface ArtilcleService {

    int deleteByPrimaryKey(Integer rCashId,Integer id);

    PageInfo<ArticleEntity> queryList(Integer rCashId, ArticleDto articleDto);

    ArticleEntity getArtcledesc(Integer rCashId, ArticleDto articleDto);

    List<String> searchAdvice(Integer rCashId, ArticleSearchDto searchDto);

    SearchResultVo searchByText(Integer rCashId, ArticleSearchDto searchDto);

    /**
     * 文章点赞
     * @param rCashId
     * @param timesDto
     * @return
     */
    int articleLike(Integer rCashId, ArticleTimesDto timesDto);

    /**
     * 文章收藏
     * @param rCashId
     * @param timesDto
     * @return
     */
    int articleStar(Integer rCashId, ArticleTimesDto timesDto);

    /**
     * 获取我的收藏文章列表
     * @param rCashId
     * @param articleSearchDto
     * @return
     */
    PageInfo<ArticleListVo> listMyCollects(Integer rCashId, ArticleSearchDto articleSearchDto);
}
