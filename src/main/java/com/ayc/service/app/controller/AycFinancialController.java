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
import com.ayc.service.app.dto.AycFinancialDto;
import com.ayc.service.app.dto.FinancialDto;
import com.ayc.service.app.entity.AycFinancialEntity;
import com.ayc.service.app.security.AppSessionUser;
import com.ayc.service.app.service.AycFinancialService;
import com.ayc.service.app.vo.FinancialVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @Description: //TODO 现金明细接口类
 * @Author: edward
 * @Date: 2019/2/27 10:40
 */
@Controller
@RequestMapping(value = "finanical")
public class AycFinancialController {

    private Logger logger = LoggerFactory.getLogger(AycFinancialController.class);

    @Autowired
    private AppConfigs appConfigs;
    @Autowired
    private AycFinancialService aycFinancialService;

    /**
    * @Description: //TODO 现金明细列表展示
    * @Auther: edward
    * @param: [token, financialDto]
    * @return: com.ayc.framework.common.AjaxResult
    * @throws:
    * @Date: 2019/2/27 10:40
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
    public AjaxResult list(@RequestHeader String token, @RequestBody FinancialDto financialDto) {
        long startTime = System.currentTimeMillis();
        logger.info("现金明细列表接口调用开始,参数:[dto:{}]", JSON.toJSONString(financialDto));

        PageVo<FinancialVo> list = null;
        Integer pageNum = null;
        Integer pageSize = null;
        if (financialDto.getPageNum() != null) {
            pageNum = financialDto.getPageNum();
        }
        if (financialDto.getPageSize() != null) {
            pageSize = financialDto.getPageSize();
        }
        try {
            AppSessionUser appSessionUser = WebContextUtils.getCurrentSessionUser();
            if(appSessionUser == null){
                throw new BizException(AppBizCode.USER_TOKEN_ERROR);
            }
            if(financialDto.getUid() == null){
                financialDto.setUid(appSessionUser.getUserId());
            }
            if (pageNum != null) {
                pageNum = pageNum == null || new Integer(0).compareTo(pageNum) >= 0 ? 1 : pageNum;
                pageSize = pageSize == null || new Integer(9).compareTo(pageSize) >= 0 ? 10 : pageSize;
                financialDto.setPageSize(pageSize);
                financialDto.setPageNum(pageNum);
            }
            list = aycFinancialService.list(appConfigs.getBizdbIndex(), financialDto);
        } catch (BizException e) {
            long endTime = System.currentTimeMillis();
            logger.error("列表接口调用异常!耗时:{},异常信息:{}", (endTime - startTime), e.getMessage(), e);
            return AjaxResult.failed();
        } catch (Exception e) {
            // 记录异常信息
            long endTime = System.currentTimeMillis();
            logger.error("现金明细列表接口调用异常!耗时:{},异常信息:{}", (endTime - startTime), e.getMessage(), e);
            return AjaxResult.failed();
        }
        long endTime = System.currentTimeMillis();
        logger.info("现金明细列表接口调用完成,耗时:{},返回结果:[list:{}]", (endTime - startTime), list);
        return AjaxResult.success().putObj(list);
    }
}
