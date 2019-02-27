package com.ayc.service.app.controller;

import com.alibaba.fastjson.JSON;
import com.ayc.framework.aspect.AppAuth;
import com.ayc.framework.aspect.ValidateFiled;
import com.ayc.framework.aspect.ValidateGroup;
import com.ayc.framework.common.AjaxResult;
import com.ayc.framework.common.BizCode;
import com.ayc.framework.common.BizException;
import com.ayc.framework.common.PageVo;
import com.ayc.framework.util.WebContextUtils;
import com.ayc.service.app.configs.AppBizCode;
import com.ayc.service.app.configs.AppConfigs;
import com.ayc.service.app.dto.ArtcleCommentDto;
import com.ayc.service.app.entity.AycArticleCommentEntity;
import com.ayc.service.app.security.AppSessionUser;
import com.ayc.service.app.service.AycArticleCommentService;
import com.ayc.service.app.vo.ArtcleCommentVo;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户评论
 */
@Controller
@RequestMapping("/articleComment")
public class ArticleCommentController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private AycArticleCommentService articleCommentService;
    @Autowired
    private AppConfigs appConfigs;
    /**
     * 评论列表
     */
    @ValidateGroup(fileds = {
            @ValidateFiled(index = 0, notNull = true, icode = BizCode.CLIENT_ERROR, message = "token"),
            @ValidateFiled(index = 1, notNull = true, icode = BizCode.CLIENT_ERROR, message = "表单"),
            @ValidateFiled(index = 1, filedName = "uid", notNull = false, icode = BizCode.CLIENT_ERROR, message = "用户id"),
            @ValidateFiled(index = 1, filedName = "pageNum", notNull = true, icode = BizCode.CLIENT_ERROR, message = "pageNum"),
            @ValidateFiled(index = 1, filedName = "pageSize", notNull = true, icode = BizCode.CLIENT_ERROR, message = "pageSize"),
    })
    @AppAuth(index = 0, clazz = AppSessionUser.class)
    @PostMapping("/list")
    @ResponseBody
    public AjaxResult List(@RequestHeader String token,@RequestBody ArtcleCommentDto artcleCommentDto){
        long startTime = System.currentTimeMillis();
        logger.info("获取评论列表接口调用开始,参数:[dto:{}}]", JSON.toJSONString(artcleCommentDto));

        PageVo<ArtcleCommentVo> list = null;
        Integer pageNum = null;
        Integer pageSize = null;
        if(artcleCommentDto.getPageNum() != null){
            pageNum = artcleCommentDto.getPageNum();
        }
        if(artcleCommentDto.getPageSize() != null){
            pageSize = artcleCommentDto.getPageSize();
        }
        try {
            AppSessionUser appSessionUser = WebContextUtils.getCurrentSessionUser();
            if(appSessionUser == null){
                throw new BizException(AppBizCode.USER_TOKEN_ERROR);
            }
            if(artcleCommentDto.getUid() == null){
                artcleCommentDto.setUid(appSessionUser.getUserId());
            }
            pageNum = pageNum == null || new Integer(0).compareTo(pageNum) >= 0 ? 1 : pageNum;
            pageSize = pageSize == null || new Integer(9).compareTo(pageSize) >= 0 ? 10 : pageSize;
            artcleCommentDto.setPageNum(pageNum);
            artcleCommentDto.setPageSize(pageSize);
            list = articleCommentService.queryList(appConfigs.getBizdbIndex(),artcleCommentDto);
        } catch (BizException e){
            long endTime = System.currentTimeMillis();
            logger.error("获取评论列表接口调用异常!耗时:{},异常信息:{}", (endTime - startTime), e.getMessage(), e);
            return AjaxResult.failed();
        } catch (Exception e) {
            // 记录异常信息
            long endTime = System.currentTimeMillis();
            logger.error("获取评论列表接口调用异常!耗时:{},异常信息:{}", (endTime - startTime), e.getMessage(), e);
            return AjaxResult.failed();
        }
        long endTime = System.currentTimeMillis();
        logger.info("获取评论列表接口调用完成,耗时:{},返回结果:[List<ArtcleCommentVo>:{}]", (endTime - startTime), list);
        return AjaxResult.success().putObj(list);
    }

    /**
     * 发表评论
     * @param artcleCommentDto
     * @return
     */
    @ValidateGroup(fileds = {
            @ValidateFiled(index = 0, notNull = true, icode = BizCode.CLIENT_ERROR, message = "token"),
            @ValidateFiled(index = 1, notNull = true, icode = BizCode.CLIENT_ERROR, message = "表单"),
            @ValidateFiled(index = 1, filedName = "uid", notNull = false, icode = BizCode.CLIENT_ERROR, message = "评论用户id"),
            @ValidateFiled(index = 1, filedName = "articleId", notNull = true, icode = BizCode.CLIENT_ERROR, message = "文章id"),
            @ValidateFiled(index = 1, filedName = "desc", notNull = true, icode = BizCode.CLIENT_ERROR, message = "评论内容id"),
    })
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addArticleComment(@RequestHeader String token,@RequestBody ArtcleCommentDto artcleCommentDto){
        long startTime = System.currentTimeMillis();
        logger.info("添加评论接口调用开始,参数:[dto:{}}]",JSON.toJSONString(artcleCommentDto));

        AycArticleCommentEntity articleCommentEntity = null;
        try {
            AppSessionUser appSessionUser = WebContextUtils.getCurrentSessionUser();
            if(appSessionUser == null){
                throw new BizException(AppBizCode.USER_TOKEN_ERROR);
            }
            if(artcleCommentDto.getUid() == null){
                artcleCommentDto.setUid(appSessionUser.getUserId());
            }
            articleCommentEntity = articleCommentService.addArtcleComment(appConfigs.getBizdbIndex(), artcleCommentDto);
        } catch (BizException e) {
            long endTime = System.currentTimeMillis();
            logger.error("添加评论接口调用异常!耗时:{},异常信息:{}", (endTime - startTime), e.getMessage(), e);
            return AjaxResult.failed();
        } catch (Exception e) {
            // 记录异常信息
            long endTime = System.currentTimeMillis();
            logger.error("添加评论接口调用异常!耗时:{},异常信息:{}", (endTime - startTime), e.getMessage(), e);
            return AjaxResult.failed();
        }
        long endTime = System.currentTimeMillis();
        logger.info("添加评论接口调用完成,耗时:{},返回结果:[AycArticleCommentEntity:{}]", (endTime - startTime), articleCommentEntity);
        return AjaxResult.success().putObj(articleCommentEntity);
    }
}
