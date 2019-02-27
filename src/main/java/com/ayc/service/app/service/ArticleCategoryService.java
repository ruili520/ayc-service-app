package com.ayc.service.app.service;

import com.ayc.service.app.dto.ArticleCategoryDto;
import com.ayc.service.app.entity.ArticleCategoryEntity;

import java.util.List;

/**
 * Author:  wwb
 * Date:  2019/1/3011:40
 * Description:
 */
public interface ArticleCategoryService {

    List<ArticleCategoryEntity> queryList(Integer rCashId, ArticleCategoryDto articleCategoryDto);

    ArticleCategoryEntity addArticleCategory(Integer rCashId,ArticleCategoryDto articleCategoryDto);

    ArticleCategoryEntity deleteArticleCategory(Integer rCashId,ArticleCategoryDto articleCategoryDto);
}
