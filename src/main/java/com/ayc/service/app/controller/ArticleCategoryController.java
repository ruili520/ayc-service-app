package com.ayc.service.app.controller;

import com.alibaba.fastjson.JSON;
import com.ayc.framework.aspect.AppAuth;
import com.ayc.framework.aspect.ValidateFiled;
import com.ayc.framework.aspect.ValidateGroup;
import com.ayc.framework.common.BizCode;
import com.ayc.framework.util.HttpClientUtils;
import com.ayc.framework.util.WebContextUtils;
import com.ayc.service.app.configs.AppBizCode;
import com.ayc.service.app.configs.AppConfigs;
import com.ayc.service.app.dto.ArticleCategoryDto;
import com.ayc.service.app.entity.ArticleCategoryEntity;
import com.ayc.framework.common.AjaxResult;
import com.ayc.framework.common.BizException;
import com.ayc.service.app.security.AppSessionUser;
import com.ayc.service.app.service.ArticleCategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Author:  wwb
 * Date:  2019/1/3011:37
 * Description:文章分类相关接口
 */
@Controller
@RequestMapping("articlecategory")
public class ArticleCategoryController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private AppConfigs appConfigs;
    @Autowired
    private ArticleCategoryService articleCategoryService;

    @PostMapping("/list")
    @ResponseBody
    public AjaxResult List(@RequestBody ArticleCategoryDto articleCategoryDto) {
        long startTime = System.currentTimeMillis();
        logger.info("获取分类列表接口调用开始,参数:[dto:{}}]", JSON.toJSONString(articleCategoryDto));
        ArticleCategoryEntity articleCategoryEntity = null;
        List<ArticleCategoryEntity> list = null;
        try {
            AppSessionUser appSessionUser = WebContextUtils.getCurrentSessionUser();
            if(appSessionUser == null){
                throw new BizException(AppBizCode.USER_TOKEN_ERROR);
            }
            list = articleCategoryService.queryList(appConfigs.getBizdbIndex(),articleCategoryDto);
        } catch (BizException e) {
            long endTime = System.currentTimeMillis();
            logger.error("获取分类列表接口调用异常!耗时:{},异常信息:{}", (endTime - startTime), e.getMessage(), e);
            return AjaxResult.failed();
        } catch (Exception e) {
            // 记录异常信息
            long endTime = System.currentTimeMillis();
            logger.error("获取分类列表接口调用异常!耗时:{},异常信息:{}", (endTime - startTime), e.getMessage(), e);
            return AjaxResult.failed();
        }
        long endTime = System.currentTimeMillis();
        logger.info("获取分类列表接口调用完成,耗时:{},返回结果:[testEntity:{}]", (endTime - startTime), articleCategoryEntity);
        return AjaxResult.success().putObj(list);
    }
    @ValidateGroup(fileds = {
            @ValidateFiled(index = 0, notNull = true, icode = BizCode.CLIENT_ERROR, message = "token"),
    })
    @AppAuth(index = 0, clazz = AppSessionUser.class)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult AddArticleCategory(@RequestHeader String token,@RequestBody  ArticleCategoryDto articleCategoryDto) {
        long startTime = System.currentTimeMillis();
        logger.info("添加文章分类接口调用开始,参数:[dto:{}}]", JSON.toJSONString(articleCategoryDto));
        ArticleCategoryEntity articleCategoryEntity = null;
        try {
            AppSessionUser appSessionUser = WebContextUtils.getCurrentSessionUser();
            if(appSessionUser == null){
                throw new BizException(AppBizCode.USER_TOKEN_ERROR);
            }
            articleCategoryEntity = articleCategoryService.addArticleCategory(appConfigs.getBizdbIndex(),articleCategoryDto);
        } catch (BizException e) {
            long endTime = System.currentTimeMillis();
            logger.error("添加文章分类接口调用异常!耗时:{},异常信息:{}", (endTime - startTime), e.getMessage(), e);
            return AjaxResult.failed();
        } catch (Exception e) {
            // 记录异常信息
            long endTime = System.currentTimeMillis();
            logger.error("添加文章分类接口调用异常!耗时:{},异常信息:{}", (endTime - startTime), e.getMessage(), e);
            return AjaxResult.failed();
        }
        long endTime = System.currentTimeMillis();
        logger.info("添加文章分类接口调用完成,耗时:{},返回结果:[testEntity:{}]", (endTime - startTime), articleCategoryEntity);
        return AjaxResult.success().putObj(articleCategoryEntity);
    }
    @ValidateGroup(fileds = {
            @ValidateFiled(index = 0, notNull = true, icode = BizCode.CLIENT_ERROR, message = "token"),
            @ValidateFiled(index = 1, notNull = true, icode = BizCode.CLIENT_ERROR, message = "表单"),
            @ValidateFiled(index = 1, filedName = "id", notNull = true, icode = BizCode.CLIENT_ERROR, message = "id"),
    })
    @AppAuth(index = 0, clazz = AppSessionUser.class)
    @DeleteMapping("/delete")
    @ResponseBody
    public AjaxResult DeleteArticleCategory(@RequestHeader String token,@RequestBody  ArticleCategoryDto articleCategoryDto) {
        long startTime = System.currentTimeMillis();
        logger.info("删除文章分类接口调用开始,参数:[dto:{}}]", JSON.toJSONString(articleCategoryDto));
        ArticleCategoryEntity articleCategoryEntity = null;
        try {
            AppSessionUser appSessionUser = WebContextUtils.getCurrentSessionUser();
            if(appSessionUser == null){
                throw new BizException(AppBizCode.USER_TOKEN_ERROR);
            }
            articleCategoryEntity = articleCategoryService.deleteArticleCategory(appConfigs.getBizdbIndex(),articleCategoryDto);
        } catch (BizException e) {
            long endTime = System.currentTimeMillis();
            logger.error("删除文章分类接口调用异常!耗时:{},异常信息:{}", (endTime - startTime), e.getMessage(), e);
            return AjaxResult.failed();
        } catch (Exception e) {
            // 记录异常信息
            long endTime = System.currentTimeMillis();
            logger.error("删除文章分类接口调用异常!耗时:{},异常信息:{}", (endTime - startTime), e.getMessage(), e);
            return AjaxResult.failed();
        }
        long endTime = System.currentTimeMillis();
        logger.info("删除文章分类接口调用完成,耗时:{},返回结果:[testEntity:{}]", (endTime - startTime), articleCategoryEntity);
        return AjaxResult.success().putObj(articleCategoryEntity);
    }


}
