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
import com.ayc.service.app.dto.AycOitLogDto;
import com.ayc.service.app.entity.AycOitLogEntity;
import com.ayc.service.app.security.AppSessionUser;
import com.ayc.service.app.service.AycOitLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
/**
 * @Title:
 * @Description: //TODO OIT明细类
 * @Author: 程亚磊
 * @Date: 2019/2/25 15:09
 */
@Controller
@RequestMapping(value = "oit")
public class AycOitLogController {

    private Logger logger = LoggerFactory.getLogger(AycOitLogController.class);

    @Autowired
    private AppConfigs appConfigs;
    @Autowired
    private AycOitLogService aycOitLogService;


    /**
     * @Title:
     * @Description: //TODO OIT明细列表展示
     * @Author: 程亚磊
     * @Date: 2019/2/25 18:31
     */
    @ValidateGroup(fileds = {
            @ValidateFiled(index = 0, notNull = true, icode = BizCode.CLIENT_ERROR, message = "token"),
            @ValidateFiled(index = 1, notNull = true, icode = BizCode.CLIENT_ERROR, message = "表单"),
            @ValidateFiled(index = 1, filedName = "pageNum", notNull = true, icode = BizCode.CLIENT_ERROR, message = "pageNum"),
            @ValidateFiled(index = 1, filedName = "pageSize", notNull = true, icode = BizCode.CLIENT_ERROR, message = "pageSize"),
    })
    @AppAuth(index = 0, clazz = AppSessionUser.class)
    @PostMapping("list")
    @ResponseBody
    public AjaxResult list(@RequestHeader String token, @RequestBody AycOitLogDto aycOitLogDto) {
        long startTime = System.currentTimeMillis();
        logger.info("OIT明细列表接口调用开始,参数:[dto:{}]", JSON.toJSONString(aycOitLogDto));

        PageVo<AycOitLogEntity> list = null;
        Integer pageNum = null;
        Integer pageSize = null;
        if (aycOitLogDto.getPageNum() != null) {
            pageNum = aycOitLogDto.getPageNum();
        }
        if (aycOitLogDto.getPageSize() != null) {
            pageSize = aycOitLogDto.getPageSize();
        }
        try {
            AppSessionUser appSessionUser = WebContextUtils.getCurrentSessionUser();
            if(appSessionUser == null){
                throw new BizException(AppBizCode.USER_TOKEN_ERROR);
            }
            if(aycOitLogDto.getUid() == null){
                aycOitLogDto.setUid(appSessionUser.getUserId());
            }
            if (pageNum != null) {
                pageNum = pageNum == null || new Integer(0).compareTo(pageNum) >= 0 ? 1 : pageNum;
                pageSize = pageSize == null || new Integer(9).compareTo(pageSize) >= 0 ? 10 : pageSize;
                aycOitLogDto.setPageSize(pageSize);
                aycOitLogDto.setPageNum(pageNum);
            }
            list = aycOitLogService.list(appConfigs.getBizdbIndex(), aycOitLogDto);
        } catch (BizException e) {
            long endTime = System.currentTimeMillis();
            logger.error("OIT明细列表接口调用异常!耗时:{},异常信息:{}", (endTime - startTime), e.getMessage(), e);
            return AjaxResult.failed();
        } catch (Exception e) {
            // 记录异常信息
            long endTime = System.currentTimeMillis();
            logger.error("OIT明细列表接口调用异常!耗时:{},异常信息:{}", (endTime - startTime), e.getMessage(), e);
            return AjaxResult.failed();
        }
        long endTime = System.currentTimeMillis();
        logger.info("OIT明细列表接口调用完成,耗时:{},返回结果:[list:{}]", (endTime - startTime), list);
        return AjaxResult.success().putObj(list);
    }
}
