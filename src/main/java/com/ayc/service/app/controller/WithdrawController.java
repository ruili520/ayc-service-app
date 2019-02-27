package com.ayc.service.app.controller;

import com.alipay.api.response.AlipayFundTransToaccountTransferResponse;
import com.ayc.framework.aspect.AppAuth;
import com.ayc.framework.aspect.ValidateFiled;
import com.ayc.framework.aspect.ValidateGroup;
import com.ayc.framework.common.AjaxResult;
import com.ayc.framework.common.BizCode;
import com.ayc.framework.common.BizException;
import com.ayc.framework.util.DozerUtil;
import com.ayc.framework.util.WebContextUtils;
import com.ayc.service.api.dto.AliPayDto;
import com.ayc.service.api.dto.WxPayDto;
import com.ayc.service.api.service.AliPayService;
import com.ayc.service.api.service.WxPayService;
import com.ayc.service.app.configs.AppBizCode;
import com.ayc.service.app.common.utils.PayCommonUtil;
import com.ayc.service.app.configs.AppConfigs;
import com.ayc.service.app.entity.AycFinancialEntity;
import com.ayc.service.app.entity.OrderEntity;
import com.ayc.service.app.security.AppSessionUser;
import com.ayc.service.app.service.AycFinancialService;
import com.ayc.service.app.service.OrderService;
import com.ayc.service.uc.dto.AycUserDetailDto;
import com.ayc.service.uc.entity.AycUserDetailEntity;
import com.ayc.service.uc.service.UcAycUserDetailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Author:  wwb
 * Date:  2019/2/2520:17
 * Description:提现相关控制器
 */
@Controller
@RequestMapping("withdraw")
public class WithdrawController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private WxPayService wxPayService;
    @Autowired
    private AppConfigs appConfigs;
    @Autowired
    private UcAycUserDetailService aycUserDetailService;
    @Autowired
    private AliPayService aliPayService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private AycFinancialService aycFinancialService;

    @ValidateGroup(fileds = {
            @ValidateFiled(index = 0, notNull = true, icode = BizCode.CLIENT_ERROR, message = "token"),
            @ValidateFiled(index = 1, notNull = true, icode = BizCode.CLIENT_ERROR, message = "表单"),
            @ValidateFiled(index = 1, filedName = "openid", notNull = true, icode = BizCode.CLIENT_ERROR, message = "openid"),
            @ValidateFiled(index = 1, filedName = "amount", notNull = true, icode = BizCode.CLIENT_ERROR, message = "amount"),
    })
    @AppAuth(index = 0, clazz = AppSessionUser.class)
    @PostMapping("/wxpay")
    @ResponseBody
    public AjaxResult WxPay(@RequestHeader String token, @RequestBody WxPayDto wxPayDto, HttpServletRequest request, HttpServletResponse response) {
        long startTime = System.currentTimeMillis();
        logger.info("短信发送接口调用开始,参数:[dto:{}}]");
        try {
            AppSessionUser appSessionUser = WebContextUtils.getCurrentSessionUser();
            if (appSessionUser == null) {
                throw new BizException(AppBizCode.USER_TOKEN_ERROR);
            }
            AycUserDetailDto aycUserDetailDto = new AycUserDetailDto();
            aycUserDetailDto.setUid(appSessionUser.getUserId());
            DozerUtil.map(wxPayDto, aycUserDetailDto);
            wxPayService.transfer(request, response, wxPayDto);
            //变更用户余额
            aycUserDetailService.updateBalance(appConfigs.getBizdbIndex(), aycUserDetailDto);
        } catch (BizException e) {
            long endTime = System.currentTimeMillis();
            logger.error("短信发送接口调用异常!耗时:{},异常信息:{}", (endTime - startTime), e.getMessage(), e);
            return AjaxResult.failed(e.getCode());
        } catch (Exception e) {
            // 记录异常信息
            long endTime = System.currentTimeMillis();
            logger.error("短信发送接口调用异常!耗时:{},异常信息:{}", (endTime - startTime), e.getMessage(), e);
            return AjaxResult.failed();
        }
        long endTime = System.currentTimeMillis();
        logger.info("短信发送接口调用完成,耗时:{},返回结果:[testEntity:{}]", (endTime - startTime));
        return AjaxResult.success();
    }


    /**
    * @Description: //TODO 支付宝提现方法
    * @Auther: edward
    * @param: [token, aliPayDto]
    * @return: com.ayc.framework.common.AjaxResult
    * @throws: 
    * @Date: 2019/2/27 10:15
    */
    @ValidateGroup(fileds = {
            @ValidateFiled(index = 0, notNull = true, icode = BizCode.CLIENT_ERROR, message = "token"),
            @ValidateFiled(index = 1, notNull = true, icode = BizCode.CLIENT_ERROR, message = "表单"),
            @ValidateFiled(index = 1, filedName = "cash", notNull = true, icode = BizCode.CLIENT_ERROR, message = "提现金额"),
            @ValidateFiled(index = 1, filedName = "nowBalance", notNull = true, icode = BizCode.CLIENT_ERROR, message = "用户当前的余额"),
    })
    @AppAuth(index = 0, clazz = AppSessionUser.class)
    @PostMapping("/aliPay")
    @ResponseBody
    public AjaxResult AliPay(@RequestHeader String token, @RequestBody AliPayDto aliPayDto) {
        AjaxResult data;
        long startTime = System.currentTimeMillis();
        logger.info("支付宝提现开始!");
        //获取当前用户的信息
        AppSessionUser appSessionUser = WebContextUtils.getCurrentSessionUser();
        //先插入一条订单信息
        String code = PayCommonUtil.createOrderNumber();
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setCash(aliPayDto.getCash());
        orderEntity.setObjType((byte) 3);
        orderEntity.setDesc("支付宝提现");
        orderEntity.setUid(appSessionUser.getUserId());
        orderEntity.setCode(code);
        orderService.addOrder(appConfigs.getBizdbIndex(), orderEntity);
        try {
            //进行支付宝提现操作
            AlipayFundTransToaccountTransferResponse response = aliPayService.withdraw(aliPayDto,code);
            //如果返回的数据为成功，那么进行下列操作
            if ( "10000".equals(response.getCode()) ) {
                //支付成功后，修改订单状态
                String outBizNo = response.getOutBizNo();
                int result = orderService.updateByCode(appConfigs.getBizdbIndex(), outBizNo, (byte) 1);

                //扣除余额
                AycUserDetailEntity aycUserDetailEntity = new AycUserDetailEntity();
                aycUserDetailEntity.setBalance(aliPayDto.getNowBalance());
                aycUserDetailEntity.setUid(appSessionUser.getUserId());
                int result1 =  aycUserDetailService.updateByUid(appConfigs.getBizdbIndex(), aycUserDetailEntity);

                //插入一条流水记录表
                AycFinancialEntity info = new AycFinancialEntity();
                info.setPayAccount("爱原创");//支付账号，可以写死
                info.setAction((byte) 3);
                info.setPayCode(PayCommonUtil.createOrderNumber());
                info.setUid(appSessionUser.getUserId());
                info.setCash(aliPayDto.getCash());
                info.setNowBalance(aliPayDto.getNowBalance());
                info.setPayType((byte) 3);
                aycFinancialService.insert(appConfigs.getBizdbIndex(),info);
                long endTime = System.currentTimeMillis();
                data = AjaxResult.success("支付宝提现接口调用成功!");
                logger.info("支付宝提现成功!耗时:{}", (endTime - startTime));
            } else {
                long endTime = System.currentTimeMillis();
                data = AjaxResult.success("支付宝提现接口调用失败，请检查!");
                logger.info("支付宝提现失败!耗时:{}，错误代码:{},错误信息:{}", (endTime - startTime), response.getCode(), response.getMsg());
            }
            return data;
        } catch (BizException e) {
            long endTime = System.currentTimeMillis();
            logger.error("支付宝提现接口调用异常!耗时:{},异常信息:{}", (endTime - startTime), e.getMessage(), e);
            return AjaxResult.failed(e.getCode());
        } catch (Exception e) {
            e.printStackTrace();
            // 记录异常信息
            long endTime = System.currentTimeMillis();
            logger.error("支付宝提现接口调用异常!耗时:{},异常信息:{}", (endTime - startTime), e.getMessage(), e);
            return AjaxResult.failed();
        }
    }
}
