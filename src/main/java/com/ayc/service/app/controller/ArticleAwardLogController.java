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
import com.ayc.service.app.dto.ArticleAwardDto;
import com.ayc.service.app.security.AppSessionUser;
import com.ayc.service.app.service.ArticleAwardLogService;
import com.ayc.service.app.vo.ArticleAwardLogVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


/**
 * 用户打赏
 *
 * @author wwq
 * @version 1.0
 * @createDate 2019/2/18 15:38
 */
@Controller
@RequestMapping("/articleAward")
public class ArticleAwardLogController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private AppConfigs appConfigs;
    @Autowired
    private ArticleAwardLogService articleAwardLogService;

    /**
     * 我的打赏列表
     * @param articleAwardDto
     * @return
     */
    @ValidateGroup(fileds = {
            @ValidateFiled(index = 0, notNull = true, icode = BizCode.CLIENT_ERROR, message = "token"),
            @ValidateFiled(index = 1,  notNull = true, icode = BizCode.CLIENT_ERROR, message = "表单"),
            @ValidateFiled(index = 1, filedName = "pageNum", notNull = true, icode = BizCode.CLIENT_ERROR, message = "pageNum"),
            @ValidateFiled(index = 1, filedName = "pageSize", notNull = true, icode = BizCode.CLIENT_ERROR, message = "pageSize"),
    })
    @AppAuth(index = 0, clazz = AppSessionUser.class)
    @PostMapping("/myAwardList")
    @ResponseBody
    public AjaxResult myAwardList(@RequestHeader String token, @RequestBody ArticleAwardDto articleAwardDto) {
        long startTime = System.currentTimeMillis();
        logger.info("我的打赏列表接口调用开始,参数:[dto:{}}]", JSON.toJSONString(articleAwardDto));

        PageVo<ArticleAwardLogVo> list = null;
        Integer pageNum = null;
        Integer pageSize = null;
        if(articleAwardDto.getPageNum() != null){
            pageNum = articleAwardDto.getPageNum();
        }
        if(articleAwardDto.getPageSize() != null){
            pageSize = articleAwardDto.getPageSize();
        }
        try {
            AppSessionUser appSessionUser = WebContextUtils.getCurrentSessionUser();
            if(appSessionUser == null){
                throw new BizException(AppBizCode.USER_TOKEN_ERROR);
            }
            if(articleAwardDto.getUid() == null) {
                articleAwardDto.setUid(appSessionUser.getUserId());
            }
            if(pageNum != null){
                pageNum = pageNum == null || new Integer(0).compareTo(pageNum) >= 0 ? 1 : pageNum;
                pageSize = pageSize == null || new Integer(9).compareTo(pageSize) >= 0 ? 10 : pageSize;
                articleAwardDto.setPageSize(pageSize);
                articleAwardDto.setPageNum(pageNum);
            }
            list = articleAwardLogService.listAwardLogs(appConfigs.getBizdbIndex(), articleAwardDto);
        } catch (BizException e) {
            long endTime = System.currentTimeMillis();
            logger.error("列表接口调用异常!耗时:{},异常信息:{}", (endTime - startTime), e.getMessage(), e);
            return AjaxResult.failed();
        } catch (Exception e) {
            // 记录异常信息
            long endTime = System.currentTimeMillis();
            logger.error("我的打赏列表接口调用异常!耗时:{},异常信息:{}", (endTime - startTime), e.getMessage(), e);
            return AjaxResult.failed();
        }
        long endTime = System.currentTimeMillis();
        logger.info("我的打赏列表接口调用完成,耗时:{},返回结果:[list:{}]", (endTime - startTime), list);
        return AjaxResult.success().putObj(list);
    }

    /**
     * 打赏我的列表
     * @param articleAwardDto
     * @return
     */
    @ValidateGroup(fileds = {
            @ValidateFiled(index = 0, notNull = true, icode = BizCode.CLIENT_ERROR, message = "token"),
            @ValidateFiled(index = 1, notNull = true, icode = BizCode.CLIENT_ERROR, message = "表单"),
            @ValidateFiled(index = 1, filedName = "pageNum", notNull = true, icode = BizCode.CLIENT_ERROR, message = "pageNum"),
            @ValidateFiled(index = 1, filedName = "pageSize", notNull = true, icode = BizCode.CLIENT_ERROR, message = "pageSize"),
    })
    @AppAuth(index = 0, clazz = AppSessionUser.class)
    @PostMapping("/awardMeList")
    @ResponseBody
    public AjaxResult awardMeList(@RequestHeader String token, @RequestBody ArticleAwardDto articleAwardDto) {
        long startTime = System.currentTimeMillis();
        logger.info("打赏我的列表接口调用开始,参数:[dto:{}}]", JSON.toJSONString(articleAwardDto));


        PageVo<ArticleAwardLogVo> list = null;
        Integer pageNum = null;
        Integer pageSize = null;
        if(articleAwardDto.getPageNum() != null){
            pageNum = articleAwardDto.getPageNum();
        }
        if(articleAwardDto.getPageSize() != null){
            pageSize = articleAwardDto.getPageSize();
        }
        try {
            AppSessionUser appSessionUser = WebContextUtils.getCurrentSessionUser();
            if(appSessionUser == null){
                throw new BizException(AppBizCode.USER_TOKEN_ERROR);
            }
            if(articleAwardDto.getUid() == null) {
                articleAwardDto.setToUid(appSessionUser.getUserId());
            }
            if(pageNum != null){
                pageNum = pageNum == null || new Integer(0).compareTo(pageNum) >= 0 ? 1 : pageNum;
                pageSize = pageSize == null || new Integer(9).compareTo(pageSize) >= 0 ? 10 : pageSize;
                articleAwardDto.setPageSize(pageSize);
                articleAwardDto.setPageNum(pageNum);
            }
            list = articleAwardLogService.listAwardLogs(appConfigs.getBizdbIndex(), articleAwardDto);
        } catch (BizException e) {
            long endTime = System.currentTimeMillis();
            logger.error("打赏我的列表调用异常!耗时:{},异常信息:{}", (endTime - startTime), e.getMessage(), e);
            return AjaxResult.failed();
        } catch (Exception e) {
            // 记录异常信息
            long endTime = System.currentTimeMillis();
            logger.error("打赏我的列表接口调用异常!耗时:{},异常信息:{}", (endTime - startTime), e.getMessage(), e);
            return AjaxResult.failed();
        }
        long endTime = System.currentTimeMillis();
        logger.info("打赏我的列表接口调用完成,耗时:{},返回结果:[list:{}]", (endTime - startTime), list);
        return AjaxResult.success().putObj(list);
    }
}
