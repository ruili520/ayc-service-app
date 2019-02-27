package com.ayc.service.app.service.impl;

import com.ayc.framework.common.BizException;
import com.ayc.framework.datasource.annotation.DataSource;
import com.ayc.framework.util.DozerUtil;
import com.ayc.service.app.common.constants.SystemConst;
import com.ayc.service.app.common.enums.ArticleEnums;
import com.ayc.service.app.common.enums.ArticleTimesEnum;
import com.ayc.service.app.common.enums.SystemEnums;
import com.ayc.service.app.common.utils.Html2Text;
import com.ayc.service.app.configs.AppBizCode;
import com.ayc.service.app.dao.mapper.ArticleCollectEntityMapper;
import com.ayc.service.app.dao.mapper.ArticleMapper;
import com.ayc.service.app.dao.mapper.ArticleTimesMapper;
import com.ayc.service.app.dto.ArticleDto;
import com.ayc.service.app.dto.ArticleSearchDto;
import com.ayc.service.app.dto.ArticleTimesDto;
import com.ayc.service.app.entity.ArticleCollectEntity;
import com.ayc.service.app.entity.ArticleEntity;
import com.ayc.service.app.entity.ArticleTimesEntity;
import com.ayc.service.app.service.ArtilcleService;
import com.ayc.service.app.vo.ArticleListVo;
import com.ayc.service.app.vo.SearchResultVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Author:  wwb
 * Date:  2019/2/1114:04
 * Description:
 */
@Service("articleService")
public class ArticleServiceImpl implements ArtilcleService {
    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private ArticleTimesMapper articleTimesMapper;
    @Autowired
    private ArticleCollectEntityMapper articleCollectEntityMapper;


    @Override
    public int deleteByPrimaryKey(Integer rCashId, Integer id) {
        return 0;
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    @Override
    public PageInfo<ArticleEntity> queryList(@DataSource(field = "rCashId") Integer rCashId, ArticleDto articleDto) {
        if (StringUtils.isNotEmpty(articleDto.getOrderBy())){
            String order = StringUtils.defaultIfEmpty(articleDto.getOrder(), SystemEnums.Order.DESC.getOrder());
            articleDto.setOrderBy(ArticleEnums.OrderBy.getColumnByCode(articleDto.getOrderBy()) + " " + order);
        }

        PageHelper.startPage(articleDto.getPageNum(), articleDto.getPageSize());
        List<ArticleEntity> list = articleMapper.queryList(articleDto);
        PageInfo<ArticleEntity> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Override
    public ArticleEntity getArtcledesc(@DataSource(field = "rCashId") Integer rCashId, ArticleDto articleDto) {
        ArticleEntity articleEntity = articleMapper.getArtcledesc(articleDto.getId());
        return articleEntity;
    }

    @Override
    public List<String> searchAdvice(@DataSource(field = "rCashId") Integer rCashId, ArticleSearchDto searchDto) {
        String[] arr = {"赵四", "赵百灵", "赵于谦", "赵于小谦", "赵本山", "赵本金", "赵本木", "赵本水", "赵本火", "赵本土"};

        List<String> result = Lists.newArrayList();
        for (int i = 0; i < searchDto.getAdviceNum(); i++){
            if (i >= arr.length){
                break;
            }
            result.add(arr[i]);
        }
        return result;
    }

    @Override
    public SearchResultVo searchByText(@DataSource(field = "rCashId") Integer rCashId, ArticleSearchDto searchDto) {
        SearchResultVo resultVo = new SearchResultVo();

        // 搜索用户
//        List<ArticleEntity> articleList = articleMapper.listArticles();


        // 搜索文章


        return resultVo;
    }


    @Transactional(readOnly = false, rollbackFor = Exception.class)
    @Override
    public int articleLike(@DataSource(field = "rCashId") Integer rCashId, ArticleTimesDto timesDto) {
        int likeStatus = SystemConst.NO;

        timesDto.setType(ArticleTimesEnum.Types.LIKE.getCode());
        // 获取原有点赞数据
        ArticleTimesEntity articleTimesEntity = articleTimesMapper.selectByArticleTimeDto(timesDto);
        // 获取文章
        ArticleEntity articleEntity = articleMapper.selectByPrimaryKey(timesDto.getArticleId());

        if (articleEntity == null || articleEntity.getStatus() != ArticleEnums.Status.PUBLISH.getCode()){
            // 文章不存在，或已被删除
            throw new BizException(AppBizCode.ARTICLE_NOT_FOUND);
        }

        if (articleTimesEntity != null){
            // 删除原有赞，并将文章点赞数减一
            int deleteCode = articleTimesMapper.deleteByPrimaryKey(articleTimesEntity.getId());

            articleEntity.setLikeNum(articleEntity.getLikeNum() - 1);
        } else {
            // 收藏状态为收藏
            likeStatus = SystemConst.YES;

            // 新增点赞，并将文章点赞数加一
            articleTimesEntity = new ArticleTimesEntity();
            DozerUtil.map(timesDto, articleTimesEntity);
            articleTimesEntity.setType(ArticleTimesEnum.Types.LIKE.getCode());
            articleTimesEntity.setCreatedAt(new Date());
            articleTimesMapper.insert(articleTimesEntity);
            articleEntity.setLikeNum(articleEntity.getLikeNum() + 1);
        }
        articleEntity.setUpdatedAt(new Date());
        articleMapper.updateByPrimaryKeySelective(articleEntity);
        return likeStatus;
    }


    @Transactional(readOnly = false, rollbackFor = Exception.class)
    @Override
    public int articleStar(@DataSource(field = "rCashId") Integer rCashId, ArticleTimesDto timesDto) {
        int starStatus = SystemConst.NO;
        // 获取原有收藏数据
        ArticleCollectEntity articleCollectEntity = articleCollectEntityMapper.selectByArticleAndUser(timesDto.getArticleId(), timesDto.getUid());
        // 获取文章
        ArticleEntity articleEntity = articleMapper.selectByPrimaryKey(timesDto.getArticleId());
        if (articleEntity == null || articleEntity.getStatus() != ArticleEnums.Status.PUBLISH.getCode()){
            // 文章不存在，或已被删除
            throw new BizException(AppBizCode.ARTICLE_NOT_FOUND);
        }
        if (articleCollectEntity != null){
            // 取消收藏，并将文章点赞数减一
            int deleteCode = articleCollectEntityMapper.deleteByPrimaryKey(articleCollectEntity.getId());
            articleEntity.setCollectNum(articleEntity.getCollectNum() - 1);
        } else {
            // 收藏状态为收藏
            starStatus = SystemConst.YES;

            // 新增点赞，并将文章点赞数加一
            articleCollectEntity = new ArticleCollectEntity();
            DozerUtil.map(timesDto, articleCollectEntity);
            articleCollectEntity.setCreatedAt(new Date());
            articleCollectEntityMapper.insert(articleCollectEntity);

            articleEntity.setCollectNum(articleEntity.getCollectNum() + 1);
        }

        articleEntity.setUpdatedAt(new Date());
        articleMapper.updateByPrimaryKeySelective(articleEntity);

        return starStatus;
    }


    @Transactional(readOnly = true, rollbackFor = Exception.class)
    @Override
    public PageInfo<ArticleListVo> listMyCollects(@DataSource(field = "rCashId") Integer rCashId, ArticleSearchDto articleSearchDto) {
        if (StringUtils.isNotEmpty(articleSearchDto.getOrderBy())){
            String order = StringUtils.defaultIfEmpty(articleSearchDto.getOrder(), SystemEnums.Order.DESC.getOrder());
            articleSearchDto.setOrderBy(ArticleEnums.OrderBy.getColumnByCode(articleSearchDto.getOrderBy()) + " " + order);
        }

        PageHelper.startPage(articleSearchDto.getPageNum(), articleSearchDto.getPageSize());
        List<ArticleEntity> tempList = articleMapper.listMyCollects(articleSearchDto);

        List<ArticleListVo> list = null;
        if (tempList != null && tempList.size() > 0){
            list = Lists.newArrayList();
            ArticleListVo voItem = null;
            for (ArticleEntity articleEntity : tempList) {
                voItem = new ArticleListVo();
                DozerUtil.map(articleEntity, voItem);
                if (StringUtils.isNotEmpty(articleEntity.getDesc())){
                    String text = Html2Text.getContent(articleEntity.getDesc());

                    voItem.setDesc(text.substring(0, text.length() > 100 ? 100 : text.length()));
                }
                list.add(voItem);
            }

            tempList.clear();
            tempList = null;
        }
        PageInfo<ArticleListVo> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }
}
