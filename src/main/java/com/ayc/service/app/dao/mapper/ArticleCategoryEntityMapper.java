package com.ayc.service.app.dao.mapper;

import com.ayc.service.app.entity.ArticleCategoryEntity;
import com.ayc.framework.dao.IBaseMapper;

import java.util.List;

public interface ArticleCategoryEntityMapper extends IBaseMapper {

//    获取文章分类列表
    List<ArticleCategoryEntity> queryList();

//    添加文章分类
    int addCategory(ArticleCategoryEntity articleCategoryEntity);

//    删除文章分类
    int deleteCategory(Integer id);

    ArticleCategoryEntity selectByPrimaryKey(Integer id);
//   更新文章分类内容
    int updateByPrimaryKeySelective(ArticleCategoryEntity record);
}