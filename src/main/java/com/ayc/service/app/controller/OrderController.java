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
import com.ayc.service.app.dto.OrderDto;
import com.ayc.service.app.entity.OrderEntity;
import com.ayc.service.app.security.AppSessionUser;
import com.ayc.service.app.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/order")
public class OrderController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private AppConfigs appConfigs;
    @Autowired
    private OrderService orderService;
    @ValidateGroup(fileds = {
            @ValidateFiled(index = 0, notNull = true, icode = BizCode.CLIENT_ERROR, message = "token"),
            @ValidateFiled(index = 1, notNull = true, icode = BizCode.CLIENT_ERROR, message = "表单"),
            @ValidateFiled(index = 1, filedName = "cash", notNull = true, icode = BizCode.CLIENT_ERROR, message = "订单金额"),
            @ValidateFiled(index = 1, filedName = "uid", notNull = true, icode = BizCode.CLIENT_ERROR, message = "用户ID"),
            @ValidateFiled(index = 1, filedName = "objId", notNull = true, icode = BizCode.CLIENT_ERROR, message = "订单类型对应的id"),
            @ValidateFiled(index = 1, filedName = "objType", notNull = true, icode = BizCode.CLIENT_ERROR, message = "订单类型"),
    })
    @AppAuth(index = 0, clazz = AppSessionUser.class)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addOrder(@RequestHeader String token, @RequestBody OrderDto orderDto){
        long startTime = System.currentTimeMillis();
        logger.info("添加订单接口调用开始,参数:[dto:{}}]", JSON.toJSONString(orderDto));
        try {
            AppSessionUser appSessionUser = WebContextUtils.getCurrentSessionUser();
            if(appSessionUser == null){
                throw new BizException(AppBizCode.USER_TOKEN_ERROR);
            }
            if(orderDto.getUid() == null){
                orderDto.setUid(appSessionUser.getUserId());
            }
            OrderEntity orderEntity = new OrderEntity();
            DozerUtil.map(orderDto,orderEntity);
            orderService.addOrder(appConfigs.getBizdbIndex(),orderEntity);
        } catch (BizException e) {
            long endTime = System.currentTimeMillis();
            logger.error("添加订单接口调用异常!耗时:{},异常信息:{}", (endTime - startTime), e.getMessage(), e);
            return AjaxResult.failed();
        } catch (Exception e) {
            // 记录异常信息
            long endTime = System.currentTimeMillis();
            logger.error("添加订单接口调用异常!耗时:{},异常信息:{}", (endTime - startTime), e.getMessage(), e);
            return AjaxResult.failed();
        }
        long endTime = System.currentTimeMillis();
        logger.info("添加订单接口调用完成,耗时:{},返回结果:[AycArticleCommentEntity:{}]", (endTime - startTime));
        return AjaxResult.success();
    }
}
