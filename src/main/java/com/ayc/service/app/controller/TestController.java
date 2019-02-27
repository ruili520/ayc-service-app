
package com.ayc.service.app.controller;

import com.ayc.framework.common.AjaxResult;
import com.ayc.framework.common.BizException;
import com.ayc.service.app.configs.AppConfigs;
import com.ayc.service.app.dto.TestDto;
import com.ayc.service.app.entity.TestEntity;
import com.ayc.service.app.service.TestSevice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Author:  ysj
 * Date:  2019/1/15 17:26
 * Description:
 */
@Controller
@RequestMapping("/test")
public class TestController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private TestSevice testSevice;
    @Autowired
    private AppConfigs appConfigs;
    @GetMapping("/list")
    @ResponseBody
//    @ValidateGroup(fileds = { @ValidateFiled(index = 0, notNull = true, icode = BizCode.CLIENT_ERROR, message = "表单"),
//            @ValidateFiled(index = 0, filedName = "pageNum", notNull = true, icode = BizCode.CLIENT_ERROR, message = "pageNum"),
//            @ValidateFiled(index = 0, filedName = "pageSize", notNull = false, icode = BizCode.CLIENT_ERROR, message = "pageSize"),
//             })
    //@ValidateFiled(index = 3, notNull = true, icode = BizCode.CLIENT_ERROR, message = "表单"),
    //@ValidateFiled(index = 3, filedName = "id", notNull = true, icode = BizCode.CLIENT_ERROR, message = "id"),
    public AjaxResult test() {
        long startTime = System.currentTimeMillis();
        logger.info("接口调用开始,参数:[]");
        TestEntity testEntity = null;


        try {
            TestDto dto = new TestDto();
             testSevice.testRedis(dto);
//
//            dto.setPageNum(1);
//            dto.setPageSize(10);
//            testSevice.test3rd();
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
        logger.info("接口调用完成,耗时:{},返回结果:[testEntity:{}]", (endTime - startTime), testEntity);
        return AjaxResult.success();
    }


    @GetMapping("/getToken")
    @ResponseBody
    public AjaxResult login() {
        long startTime = System.currentTimeMillis();
        logger.info("测试用户登陆");
        String token;
        try {


            token = testSevice.getToken();
        } catch (BizException e) {
            long endTime = System.currentTimeMillis();
            logger.error("接口调用异常!耗时:{},异常信息:{}", (endTime - startTime), e.getMessage(), e);
            return AjaxResult.failed(e.getCode());
        } catch (Exception e) {
            // 记录异常信息
            long endTime = System.currentTimeMillis();
            logger.error("接口调用异常!耗时:{},异常信息:{}", (endTime - startTime), e.getMessage(), e);
            return AjaxResult.failed();
        }
        long endTime = System.currentTimeMillis();
        logger.info("接口调用完成,耗时:{},返回结果:[toke:{}]", (endTime - startTime), token);

        return AjaxResult.success().putObj(token);
    }

}