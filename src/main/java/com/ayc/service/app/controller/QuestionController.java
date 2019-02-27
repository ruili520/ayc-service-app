
package com.ayc.service.app.controller;

import com.ayc.framework.aspect.AppAuth;
import com.ayc.framework.aspect.ValidateFiled;
import com.ayc.framework.aspect.ValidateGroup;
import com.ayc.framework.common.AjaxResult;
import com.ayc.framework.common.BizCode;
import com.ayc.framework.common.BizException;
import com.ayc.framework.util.WebContextUtils;
import com.ayc.service.app.configs.AppBizCode;
import com.ayc.service.app.configs.AppConfigs;
import com.ayc.service.app.dto.TestDto;
import com.ayc.service.app.entity.TestEntity;
import com.ayc.service.app.security.AppSessionUser;
import com.ayc.service.app.service.QuestionService;
import com.ayc.service.app.service.TestSevice;
import com.ayc.service.app.vo.AycQuestionVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 帮助中心
 *
 * @author wwq
 * @version 1.0
 * @createDate 2019/2/18 21:44
 */
@Controller
@RequestMapping("/help")
public class QuestionController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private QuestionService questionService;
    @Autowired
    private AppConfigs appConfigs;

    /**
     * 问答列表
     * @return
     */
    @ValidateGroup(fileds = {
            @ValidateFiled(index = 0, notNull = true, icode = BizCode.CLIENT_ERROR, message = "token"),
    })
    @AppAuth(index = 0, clazz = AppSessionUser.class)
    @GetMapping("/list")
    @ResponseBody
    public AjaxResult list(@RequestHeader String token) {
        long startTime = System.currentTimeMillis();
        logger.info("问答列表接口调用开始,参数:[]");
        List<AycQuestionVo> list = null;
        try {
            AppSessionUser appSessionUser = WebContextUtils.getCurrentSessionUser();
            if(appSessionUser == null){
                throw new BizException(AppBizCode.USER_TOKEN_ERROR);
            }
            list = questionService.listQuestions(appConfigs.getBizdbIndex());
        } catch (BizException e) {
            long endTime = System.currentTimeMillis();
            logger.error("问答列表接口调用异常!耗时:{},异常信息:{}", (endTime - startTime), e.getMessage(), e);
            return AjaxResult.failed();
        } catch (Exception e) {
            // 记录异常信息
            long endTime = System.currentTimeMillis();
            logger.error("问答列表接口调用异常!耗时:{},异常信息:{}", (endTime - startTime), e.getMessage(), e);
            return AjaxResult.failed();
        }
        long endTime = System.currentTimeMillis();
        logger.info("接口调用完成,耗时:{},返回结果:[list:{}]", (endTime - startTime), list);
        return AjaxResult.success().putObj(list);
    }

}