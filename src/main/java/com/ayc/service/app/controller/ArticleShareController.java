package com.ayc.service.app.controller;

import com.alibaba.fastjson.JSON;
import com.ayc.framework.aspect.AppAuth;
import com.ayc.framework.aspect.ValidateFiled;
import com.ayc.framework.aspect.ValidateGroup;
import com.ayc.framework.common.AjaxResult;
import com.ayc.framework.common.BizCode;
import com.ayc.framework.common.BizException;
import com.ayc.framework.util.WebContextUtils;
import com.ayc.service.app.configs.AppBizCode;
import com.ayc.service.app.configs.AppConfigs;
import com.ayc.service.app.dto.ArticleShareDto;
import com.ayc.service.app.security.AppSessionUser;
import com.ayc.service.app.service.ArticleShareService;
import com.ayc.service.app.vo.ArticleShareVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * 文章分享
 *
 * @author wwq
 * @version 1.0
 * @createDate 2019/2/25 13:45
 */
@RequestMapping("/share")
@Controller
public class ArticleShareController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private AppConfigs appConfigs;
    @Autowired
    private ArticleShareService articleShareService;

    /**
     * 获取分享对象
     * @param articleShareDto
     * @return
     */
    @ValidateGroup(fileds = {
            @ValidateFiled(index = 0, notNull = true, icode = BizCode.CLIENT_ERROR, message = "token"),
            @ValidateFiled(index = 1, notNull = false, icode = BizCode.CLIENT_ERROR, message = "表单"),
            @ValidateFiled(index = 1, filedName = "articleId", notNull = true, icode = BizCode.CLIENT_ERROR, message = "文章id"),
    })
    @AppAuth(index = 0, clazz = AppSessionUser.class)
    @PostMapping("/getShareUrl")
    @ResponseBody
    public AjaxResult getShareUrl(@RequestHeader String token,@RequestBody  ArticleShareDto articleShareDto) {
        long startTime = System.currentTimeMillis();
        logger.info("获取分享对象接口调用开始,参数:[dto:{}}]", JSON.toJSONString(articleShareDto));
        ArticleShareVo articleShareVo = null;
        try {
            AppSessionUser appSessionUser = WebContextUtils.getCurrentSessionUser();
            if(appSessionUser == null){
                throw new BizException(AppBizCode.USER_TOKEN_ERROR);
            }
            articleShareVo = articleShareService.createShareUrl(appConfigs.getBizdbIndex(), articleShareDto);
        } catch (BizException e) {
            long endTime = System.currentTimeMillis();
            logger.error("获取分享对象接口调用异常!耗时:{},异常信息:{}", (endTime - startTime), e.getMessage(), e);
            return AjaxResult.failed(e.getCode());
        } catch (Exception e) {
            // 记录异常信息
            long endTime = System.currentTimeMillis();
            logger.error("获取分享对象接口调用异常!耗时:{},异常信息:{}", (endTime - startTime), e.getMessage(), e);
            return AjaxResult.failed();
        }
        long endTime = System.currentTimeMillis();
        logger.info("获取分享对象接口调用完成,耗时:{},返回结果:[articleShareVo:{}]", (endTime - startTime), articleShareVo);
        return AjaxResult.success().putObj(articleShareVo);
    }
}
