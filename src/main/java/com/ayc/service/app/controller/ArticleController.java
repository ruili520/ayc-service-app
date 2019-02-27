package com.ayc.service.app.controller;

import com.alibaba.fastjson.JSON;
import com.ayc.framework.aspect.AppAuth;
import com.ayc.framework.aspect.ValidateFiled;
import com.ayc.framework.aspect.ValidateGroup;
import com.ayc.framework.common.AjaxResult;
import com.ayc.framework.common.BizCode;
import com.ayc.framework.common.BizException;
import com.ayc.framework.util.DozerUtil;
import com.ayc.framework.util.WebContextUtils;
import com.ayc.service.app.configs.AppBizCode;
import com.ayc.service.app.configs.AppConfigs;
import com.ayc.service.app.dto.ArticleDto;
import com.ayc.service.app.dto.ArticleSearchDto;
import com.ayc.service.app.dto.ArticleTimesDto;
import com.ayc.service.app.entity.ArticleEntity;
import com.ayc.service.app.security.AppSessionUser;
import com.ayc.service.app.service.ArtilcleService;
import com.ayc.service.app.vo.ArticleListVo;
import com.ayc.service.app.vo.ArtilcleDescVo;
import com.ayc.service.app.vo.SearchResultVo;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * Author:  wwb
 * Date:  2019/2/1114:06
 * Description:文章相关接口
 */
@Controller
@RequestMapping("article")
public class ArticleController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private AppConfigs appConfigs;
    @Autowired
    private ArtilcleService articleService;

    @ValidateGroup(fileds = {
            @ValidateFiled(index = 1, notNull = true, icode = BizCode.CLIENT_ERROR, message = "表单"),
            @ValidateFiled(index = 1, filedName = "pageNum", notNull = true, icode = BizCode.CLIENT_ERROR, message = "pageNum"),
            @ValidateFiled(index = 1, filedName = "pageSize", notNull = true, icode = BizCode.CLIENT_ERROR, message = "pageSize"),
    })
    @PostMapping("/list")
    @ResponseBody
    public AjaxResult List(@RequestBody ArticleDto articleDto) {
        long startTime = System.currentTimeMillis();
        logger.info("列表接口调用开始,参数:[dto:{}}]", JSON.toJSONString(articleDto));
        PageInfo<ArticleEntity> list = null;
        Integer pageNum = null;
        Integer pageSize = null;
        if (articleDto.getPageNum()!=null){
            pageNum =articleDto.getPageNum();
        }
        if (articleDto.getPageSize()!=null){
            pageSize =articleDto.getPageSize();
        }
        try {
            pageNum = pageNum == null ? 0: pageNum;
            pageSize = pageSize == null ? 10: pageSize;
            articleDto.setPageSize(pageSize);
            articleDto.setPageNum(pageNum);
            list = articleService.queryList(appConfigs.getBizdbIndex(),articleDto);
        } catch (BizException e) {
            long endTime = System.currentTimeMillis();
            logger.error("列表接口调用异常!耗时:{},异常信息:{}", (endTime - startTime), e.getMessage(), e);
            return AjaxResult.failed();
        } catch (Exception e) {
            // 记录异常信息
            long endTime = System.currentTimeMillis();
            logger.error("获取文章列表接口调用异常!耗时:{},异常信息:{}", (endTime - startTime), e.getMessage(), e);
            return AjaxResult.failed();
        }
        long endTime = System.currentTimeMillis();
        logger.info("列表接口调用完成,耗时:{},返回结果:[testEntity:{}]", (endTime - startTime), list);
        return AjaxResult.success().putObj(list);
    }

    @ValidateGroup(fileds = {
            @ValidateFiled(index = 0, notNull = true, icode = BizCode.CLIENT_ERROR, message = "表单"),
            @ValidateFiled(index = 0, filedName = "id", notNull = true, icode = BizCode.CLIENT_ERROR, message = "id"),
    })
    @PostMapping("/desc")
    @ResponseBody
    public AjaxResult ArtcleDesc(@RequestBody ArticleDto articleDto) {
        long startTime = System.currentTimeMillis();
        logger.info("列表接口调用开始,参数:[dto:{}}]", JSON.toJSONString(articleDto));
        ArtilcleDescVo artilcleDescVo = new ArtilcleDescVo();
        try {
            ArticleEntity articleEntity = articleService.getArtcledesc(appConfigs.getBizdbIndex(),articleDto);
            DozerUtil.map(articleEntity,artilcleDescVo);
        } catch (BizException e) {
            long endTime = System.currentTimeMillis();
            logger.error("列表接口调用异常!耗时:{},异常信息:{}", (endTime - startTime), e.getMessage(), e);
            return AjaxResult.failed();
        } catch (Exception e) {
            // 记录异常信息
            long endTime = System.currentTimeMillis();
            logger.error("获取分类列表接口调用异常!耗时:{},异常信息:{}", (endTime - startTime), e.getMessage(), e);
            return AjaxResult.failed();
        }
        long endTime = System.currentTimeMillis();
        logger.info("列表接口调用完成,耗时:{},返回结果:[testEntity:{}]", (endTime - startTime), artilcleDescVo);
        return AjaxResult.success().putObj(artilcleDescVo);
    }

    @ValidateGroup(fileds = {
            @ValidateFiled(index = 0, notNull = true, icode = BizCode.CLIENT_ERROR, message = "表单"),
            @ValidateFiled(index = 0, filedName = "adviceNum", notNull = true, icode = BizCode.CLIENT_ERROR, message = "adviceNum"),
    })
    @PostMapping("/searchAdvice")
    @ResponseBody
    public AjaxResult searchAdvice(@RequestBody ArticleSearchDto searchDto) {
        long startTime = System.currentTimeMillis();
        logger.info("接口调用开始,参数:[dto:{}}]", JSON.toJSONString(searchDto));
        List<String> list = null;
        try {
            list = articleService.searchAdvice(appConfigs.getBizdbIndex(), searchDto);
        } catch (BizException e) {
            long endTime = System.currentTimeMillis();
            logger.error("接口调用异常!耗时:{},异常信息:{}", (endTime - startTime), e.getMessage(), e);
            return AjaxResult.failed();
        } catch (Exception e) {
            // 记录异常信息
            long endTime = System.currentTimeMillis();
            logger.error("接口调用异常!耗时:{},异常信息:{}", (endTime - startTime), e.getMessage(), e);
            return AjaxResult.failed();
        }
        long endTime = System.currentTimeMillis();
        logger.info("接口调用完成,耗时:{},返回结果:[list:{}]", (endTime - startTime), list);
        return AjaxResult.success().putObj(list);
    }

    @PostMapping("/search")
    @ResponseBody
    public AjaxResult search(ArticleSearchDto searchDto) {
        long startTime = System.currentTimeMillis();
        logger.info("接口调用开始,参数:[dto:{}}]", JSON.toJSONString(searchDto));
        SearchResultVo searchResultVo = null;
        try {
            searchResultVo = articleService.searchByText(appConfigs.getBizdbIndex(), searchDto);
        } catch (BizException e) {
            long endTime = System.currentTimeMillis();
            logger.error("接口调用异常!耗时:{},异常信息:{}", (endTime - startTime), e.getMessage(), e);
            return AjaxResult.failed();
        } catch (Exception e) {
            // 记录异常信息
            long endTime = System.currentTimeMillis();
            logger.error("接口调用异常!耗时:{},异常信息:{}", (endTime - startTime), e.getMessage(), e);
            return AjaxResult.failed();
        }
        long endTime = System.currentTimeMillis();
        logger.info("接口调用完成,耗时:{},返回结果:[testEntity:{}]", (endTime - startTime), searchResultVo);
        return AjaxResult.success().putObj(searchResultVo);
    }

    /**
     * 文章点赞
     * @author wwq
     * @param timesDto
     * @return
     */
    @ValidateGroup(fileds = {
            @ValidateFiled(index = 0, notNull = true, icode = BizCode.CLIENT_ERROR, message = "token"),
            @ValidateFiled(index = 1, filedName = "articleId", notNull = true, icode = BizCode.CLIENT_ERROR, message = "文章id"),
    })
    @AppAuth(index = 0, clazz = AppSessionUser.class)
    @PostMapping("/articleLike")
    @ResponseBody
    public AjaxResult articleLike(@RequestHeader String token, @RequestBody ArticleTimesDto timesDto) {
        long startTime = System.currentTimeMillis();
        logger.info("文章点赞接口调用开始,参数:[dto:{}}]", JSON.toJSONString(timesDto));

        try {
            AppSessionUser appSessionUser = WebContextUtils.getCurrentSessionUser();
            if(appSessionUser == null){
                throw new BizException(AppBizCode.USER_TOKEN_ERROR);
            }
            if(timesDto.getUid() == null) {
                timesDto.setUid(appSessionUser.getUserId());
            }
            int likeStatus = articleService.articleLike(appConfigs.getBizdbIndex(), timesDto);

            long endTime = System.currentTimeMillis();
            logger.info("文章点赞接口调用完成,耗时:{},返回结果:[testEntity:{}]", (endTime - startTime));
            return AjaxResult.success().putObj(likeStatus > 0);
        } catch (BizException e) {
            long endTime = System.currentTimeMillis();
            logger.error("文章点赞接口调用异常!耗时:{},异常信息:{}", (endTime - startTime), e.getMessage(), e);
            return AjaxResult.failed(e.getCode());
        } catch (Exception e) {
            // 记录异常信息
            long endTime = System.currentTimeMillis();
            logger.error("文章点赞接口调用异常!耗时:{},异常信息:{}", (endTime - startTime), e.getMessage(), e);
            return AjaxResult.failed();
        }
    }

    /**
     * 文章收藏
     * @author wwq
     * @param token
     * @param timesDto
     */
    @ValidateGroup(fileds = {
            @ValidateFiled(index = 0, notNull = true, icode = BizCode.CLIENT_ERROR, message = "token"),
            @ValidateFiled(index = 1, filedName = "articleId", notNull = true, icode = BizCode.CLIENT_ERROR, message = "文章id"),
            @ValidateFiled(index = 1, filedName = "articleUid", notNull = true, icode = BizCode.CLIENT_ERROR, message = "文章作者id"),
    })
    @AppAuth(index = 0, clazz = AppSessionUser.class)
    @PostMapping("/articleStar")
    @ResponseBody
    public AjaxResult articleStar(@RequestHeader String token, @RequestBody ArticleTimesDto timesDto) {
        long startTime = System.currentTimeMillis();
        logger.info("文章收藏接口调用开始,参数:[dto:{}}]", JSON.toJSONString(timesDto));

        try{
            AppSessionUser appSessionUser = WebContextUtils.getCurrentSessionUser();
            if(appSessionUser == null){
                throw new BizException(AppBizCode.USER_TOKEN_ERROR);
            }
            if(timesDto.getUid() == null) {
                timesDto.setUid(appSessionUser.getUserId());
            }
            int starStatus = articleService.articleStar(appConfigs.getBizdbIndex(), timesDto);

            long endTime = System.currentTimeMillis();
            logger.info("文章收藏接口调用完成,耗时:{},返回结果:[testEntity:{}]", (endTime - startTime));
            return AjaxResult.success().putObj(starStatus > 0);
        } catch (BizException e) {
            long endTime = System.currentTimeMillis();
            logger.error("文章收藏接口调用异常!耗时:{},异常信息:{}", (endTime - startTime), e.getMessage(), e);
            return AjaxResult.failed(e.getCode());
        } catch (Exception e) {
            // 记录异常信息
            long endTime = System.currentTimeMillis();
            logger.error("文章收藏接口调用异常!耗时:{},异常信息:{}", (endTime - startTime), e.getMessage(), e);
            return AjaxResult.failed();
        }
    }

    /**
     * 我的文章列表
     * @param token
     * @param articleDto
     * @return
     */
    @ValidateGroup(fileds = {
            @ValidateFiled(index = 0, notNull = true, icode = BizCode.CLIENT_ERROR, message = "token"),
            @ValidateFiled(index = 1, filedName = "pageNum", notNull = true, icode = BizCode.CLIENT_ERROR, message = "pageNum"),
            @ValidateFiled(index = 1, filedName = "pageSize", notNull = true, icode = BizCode.CLIENT_ERROR, message = "pageSize"),
    })
    @AppAuth(index = 0, clazz = AppSessionUser.class)
    @PostMapping("/myArticles")
    @ResponseBody
    public AjaxResult listMyArticles(@RequestHeader String token, @RequestBody ArticleDto articleDto) {
        long startTime = System.currentTimeMillis();
        logger.info("我的文章列表接口调用开始,参数:[dto:{}}]", JSON.toJSONString(articleDto));

        PageInfo<ArticleEntity> list = null;
        Integer pageNum = null;
        Integer pageSize = null;
        if(articleDto.getPageNum() != null){
            pageNum = articleDto.getPageNum();
        }
        if(articleDto.getPageSize() != null){
            pageSize = articleDto.getPageSize();
        }
        try {
            AppSessionUser appSessionUser = WebContextUtils.getCurrentSessionUser();
            if(appSessionUser == null){
                throw  new BizException(AppBizCode.USER_TOKEN_ERROR);
            }
            if(articleDto.getUid() == null) {
                articleDto.setUid(appSessionUser.getUserId());
            }
            if(pageNum != null){
                pageNum = pageNum == null || new Integer(0).compareTo(pageNum) >= 0 ? 1 : pageNum;
                pageSize = pageSize == null || new Integer(9).compareTo(pageSize) >= 0 ? 10 : pageSize;
                articleDto.setPageSize(pageSize);
                articleDto.setPageNum(pageNum);
            }
            list = articleService.queryList(appConfigs.getBizdbIndex(), articleDto);
        } catch (BizException e) {
            long endTime = System.currentTimeMillis();
            logger.error("列表接口调用异常!耗时:{},异常信息:{}", (endTime - startTime), e.getMessage(), e);
            return AjaxResult.failed();
        } catch (Exception e) {
            // 记录异常信息
            long endTime = System.currentTimeMillis();
            logger.error("获取我的文章列表接口调用异常!耗时:{},异常信息:{}", (endTime - startTime), e.getMessage(), e);
            return AjaxResult.failed();
        }
        long endTime = System.currentTimeMillis();
        logger.info("获取我的文章列表接口调用完成,耗时:{},返回结果:[testEntity:{}]", (endTime - startTime), list);
        return AjaxResult.success().putObj(list);
    }

    /**
     * 我的收藏列表
     * @param token
     * @param articleSearchDto
     * @return
     */
    @ValidateGroup(fileds = {
        @ValidateFiled(index = 0, notNull = true, icode = BizCode.CLIENT_ERROR, message = "token"),
        @ValidateFiled(index = 1, filedName = "pageNum", notNull = true, icode = BizCode.CLIENT_ERROR, message = "pageNum"),
        @ValidateFiled(index = 1, filedName = "pageSize", notNull = true, icode = BizCode.CLIENT_ERROR, message = "pageSize"),
    })
    @AppAuth(index = 0, clazz = AppSessionUser.class)
    @PostMapping("/myCollects")
    @ResponseBody
    public AjaxResult listMyCollects(@RequestHeader String token, @RequestBody ArticleSearchDto articleSearchDto) {
        long startTime = System.currentTimeMillis();
        logger.info("我的收藏列表接口调用开始,参数:[dto:{}}]", JSON.toJSONString(articleSearchDto));

        PageInfo<ArticleListVo> list = null;
        Integer pageNum = null;
        Integer pageSize = null;
        if(articleSearchDto.getPageNum() != null){
            pageNum = articleSearchDto.getPageNum();
        }
        if(articleSearchDto.getPageSize() != null){
            pageSize = articleSearchDto.getPageSize();
        }
        try {
            AppSessionUser appSessionUser = WebContextUtils.getCurrentSessionUser();
            if(appSessionUser == null){
                throw  new BizException(AppBizCode.USER_TOKEN_ERROR);
            }
            if(articleSearchDto.getUid() == null) {
                articleSearchDto.setUid(appSessionUser.getUserId());
            }
            if(pageNum != null){
                pageNum = pageNum == null || new Integer(0).compareTo(pageNum) >= 0 ? 1 : pageNum;
                pageSize = pageSize == null || new Integer(9).compareTo(pageSize) >= 0 ? 10 : pageSize;
                articleSearchDto.setPageSize(pageSize);
                articleSearchDto.setPageNum(pageNum);
            }
            list = articleService.listMyCollects(appConfigs.getBizdbIndex(), articleSearchDto);
        } catch (BizException e) {
            long endTime = System.currentTimeMillis();
            logger.error("我的收藏列表调用异常!耗时:{},异常信息:{}", (endTime - startTime), e.getMessage(), e);
            return AjaxResult.failed();
        } catch (Exception e) {
            // 记录异常信息
            long endTime = System.currentTimeMillis();
            logger.error("我的收藏列表接口调用异常!耗时:{},异常信息:{}", (endTime - startTime), e.getMessage(), e);
            return AjaxResult.failed();
        }
        long endTime = System.currentTimeMillis();
        logger.info("我的收藏列表接口调用完成,耗时:{},返回结果:[list:{}]", (endTime - startTime), list);
        return AjaxResult.success().putObj(list);
    }
}
