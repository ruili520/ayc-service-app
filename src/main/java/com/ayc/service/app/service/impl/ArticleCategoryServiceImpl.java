package com.ayc.service.app.service.impl;

import com.ayc.service.app.dao.mapper.ArticleCategoryEntityMapper;
import com.ayc.service.app.entity.ArticleCategoryEntity;
import com.ayc.framework.datasource.annotation.DataSource;
import com.ayc.framework.util.DozerUtil;
import com.ayc.service.app.dto.ArticleCategoryDto;
import com.ayc.service.app.service.ArticleCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Author:  wwb
 * Date:  2019/1/3011:40
 * Description:
 */
@Service("articleCategoryService")
public class ArticleCategoryServiceImpl implements ArticleCategoryService {
    @Autowired
    private ArticleCategoryEntityMapper articleCategoryEntityMapper;
    @Override
    public List<ArticleCategoryEntity> queryList(@DataSource(field = "rCashId") Integer rCashId, ArticleCategoryDto articleCategoryDto) {
        List<ArticleCategoryEntity> list = articleCategoryEntityMapper.queryList();
        return list;
    }

    @Override
    public ArticleCategoryEntity addArticleCategory(@DataSource(field = "rCashId")Integer rCashId, ArticleCategoryDto articleCategoryDto) {
        ArticleCategoryEntity articleCategoryEntity = new ArticleCategoryEntity();
        if (articleCategoryDto.getId()!=null){
            articleCategoryEntity = articleCategoryEntityMapper.selectByPrimaryKey(articleCategoryDto.getId());
            DozerUtil.map(articleCategoryDto,articleCategoryEntity);
            articleCategoryEntity.setUpdatedAt(new Date());
            articleCategoryEntityMapper.updateByPrimaryKeySelective(articleCategoryEntity);

        }else{

            DozerUtil.map(articleCategoryDto,articleCategoryEntity);
            if (articleCategoryDto.getPid()==null){
                articleCategoryEntity.setPid(0);
            }
            articleCategoryEntity.setArticles(0);
            articleCategoryEntity.setCreatedAt(new Date());
            articleCategoryEntityMapper.addCategory(articleCategoryEntity);
        }
        return articleCategoryEntity;
    }

    @Override
    public ArticleCategoryEntity deleteArticleCategory(Integer rCashId, ArticleCategoryDto articleCategoryDto) {
        ArticleCategoryEntity articleCategoryEntity = new ArticleCategoryEntity(articleCategoryDto.getId());
        articleCategoryEntityMapper.deleteCategory(articleCategoryDto.getId());
        return articleCategoryEntity;
    }
}
