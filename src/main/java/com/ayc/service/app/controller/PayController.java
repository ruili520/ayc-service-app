package com.ayc.service.app.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.ayc.framework.aspect.AppAuth;
import com.ayc.framework.aspect.ValidateFiled;
import com.ayc.framework.aspect.ValidateGroup;
import com.ayc.framework.common.AjaxResult;
import com.ayc.framework.common.BizCode;
import com.ayc.framework.common.BizException;
import com.ayc.framework.util.IPUtil;
import com.ayc.framework.util.WebContextUtils;
import com.ayc.service.app.common.constants.AycFinancialConst;
import com.ayc.service.app.common.enums.OrderEnums;
import com.ayc.service.app.common.utils.PayCommonUtil;
import com.ayc.service.app.common.utils.PropertyUtil;
import com.ayc.service.app.common.utils.XMLUtil;
import com.ayc.service.app.configs.AppBizCode;
import com.ayc.service.app.configs.AppConfigs;
import com.ayc.service.app.dto.AycFinancialDto;
import com.ayc.service.app.dto.OrderEntityDto;
import com.ayc.service.app.entity.AlipayNotice;
import com.ayc.service.app.entity.ArticleAwardLogEntity;
import com.ayc.service.app.entity.OrderEntity;
import com.ayc.service.app.security.AppSessionUser;
import com.ayc.service.app.service.ArticleAwardLogService;
import com.ayc.service.app.service.OrderService;
import org.apache.commons.lang3.math.NumberUtils;
import org.jdom2.JDOMException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 打赏购买支付接口控制器
 *
 * @author wwq
 * @version 1.0
 * @createDate 2019/2/13 11:39
 */
@RequestMapping("/pay")
@Controller
public class PayController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private AppConfigs appConfigs;
    @Autowired
    private OrderService orderService;
    @Autowired
    private ArticleAwardLogService articleAwardLogService;

    @RequestMapping("/index")
    public String index(){
        return "index";
    }

    /**
     * 获取微信预付单
     * @param orderDto
     * @return
     */
    @ValidateGroup(fileds = {
            @ValidateFiled(index = 0, notNull = true, icode = BizCode.CLIENT_ERROR, message = "token"),
            @ValidateFiled(index = 1, notNull = true, icode = BizCode.CLIENT_ERROR, message = "表单"),
            @ValidateFiled(index = 1, filedName = "amount", notNull = true, icode = BizCode.CLIENT_ERROR, message = "订单支付金额"),
            @ValidateFiled(index = 1, filedName = "orderType", notNull = true, icode = BizCode.CLIENT_ERROR, message = "订单类型"),
            @ValidateFiled(index = 1, filedName = "articleId", notNull = true, icode = BizCode.CLIENT_ERROR, message = "文章id"),
            @ValidateFiled(index = 1, filedName = "toUid", notNull = true, icode = BizCode.CLIENT_ERROR, message = "被打赏的用户id"),
            @ValidateFiled(index = 1, filedName = "articleTitle", notNull = true, icode = BizCode.CLIENT_ERROR, message = "文章标题"),
    })
    @AppAuth(index = 0, clazz = AppSessionUser.class)
    @PostMapping("/preWxPreOrder")
    @ResponseBody
    public AjaxResult preWxPreOrder(@RequestHeader String token, @RequestBody OrderEntityDto orderDto, HttpServletRequest request) {
        long startTime = System.currentTimeMillis();
        logger.info("接口调用开始,参数:[dto:{}}]", JSON.toJSONString(orderDto));
        String orderStr = "";
        try {
            AppSessionUser appSessionUser = WebContextUtils.getCurrentSessionUser();
            if(appSessionUser == null){
                throw new BizException(AppBizCode.USER_TOKEN_ERROR);
            }
            if (orderDto.getUid() == null) {
                orderDto.setUid(appSessionUser.getUserId());
            }
            BigDecimal amount = orderDto.getAmount();
            // 付款金额必须大于0
            if (BigDecimal.ZERO.compareTo(amount) == 1){
                return AjaxResult.failed(AppBizCode.PAYMENT_AMOUNT_ERROR);
            }

            // 新增记录
            byte type = orderDto.getOrderType();
            Integer objId = null;
            if (type == OrderEnums.Types.GIVE_FEWARD.getCode()){
                // 打赏功能
                objId = articleAwardLogService.addAwardLog(appConfigs.getBizdbIndex(), orderDto);
            } else if (type == OrderEnums.Types.BUY.getCode()){
                // 购买

            }

            // 创建订单
            OrderEntity orderEntity = new OrderEntity();
            orderEntity.setUid(orderDto.getUid());
            orderEntity.setCash(amount);
            orderEntity.setObjId(objId);
            orderEntity.setObjType(orderDto.getOrderType());

            int addCode = orderService.addOrder(appConfigs.getBizdbIndex(), orderEntity);
            if (addCode == 0){
                // 订单创建失败
                return AjaxResult.failed(BizCode.BUSI_ERROR);
            }

            SortedMap<Object, Object> parameters = PayCommonUtil.getWXPrePayID(); // 获取预付单，此处已做封装，需要工具类

            parameters.put("body", "爱原创-打赏");

            parameters.put("spbill_create_ip", IPUtil.getIP(request));
            // 商户订单号：当前时间+3位随机数
            parameters.put("out_trade_no", orderEntity.getCode());
            // 测试时，每次支付一分钱，微信支付所传的金额是以分为单位的，因此实际开发中需要x100
            parameters.put("total_fee", "1");
            // 上线后，将此代码放开
            parameters.put("total_fee", amount.multiply(new BigDecimal(100)) + "");

            // 设置签名
            String sign = PayCommonUtil.createSign("UTF-8", parameters);
            parameters.put("sign", sign);
            // 封装请求参数结束
            String requestXML = PayCommonUtil.getRequestXml(parameters); // 获取xml结果

            logger.debug("封装请求参数是：" + requestXML);
            // 调用统一下单接口
            String result = PayCommonUtil.httpsRequest(PropertyUtil.getInstance().getProperty("WxPay.payURL"), "POST",
                    requestXML);
            logger.debug("调用统一下单接口：" + result);
            SortedMap<Object, Object> parMap = PayCommonUtil.startWXPay(result);
            logger.debug("最终的map是：" + parMap.toString());
            if (parMap != null) {
                orderStr = JSON.toJSONString(parMap);
            } else {
                return AjaxResult.failed(BizCode.BUSI_ERROR);
            }
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
        logger.info("接口调用完成,耗时:{},返回结果:[orderStr:{}]", (endTime - startTime), orderStr);
        return AjaxResult.success().putObj(orderStr);
    }

    /**
     * 获取支付宝预付单
     * @param orderDto
     * @return
     */
    @ValidateGroup(fileds = {
            @ValidateFiled(index = 0, notNull = true, icode = BizCode.CLIENT_ERROR, message = "token"),
            @ValidateFiled(index = 1, notNull = true, icode = BizCode.CLIENT_ERROR, message = "表单"),
            @ValidateFiled(index = 1, filedName = "amount", notNull = true, icode = BizCode.CLIENT_ERROR, message = "订单支付金额"),
            @ValidateFiled(index = 1, filedName = "orderType", notNull = true, icode = BizCode.CLIENT_ERROR, message = "订单类型"),
            @ValidateFiled(index = 1, filedName = "articleId", notNull = true, icode = BizCode.CLIENT_ERROR, message = "文章id"),
            @ValidateFiled(index = 1, filedName = "toUid", notNull = true, icode = BizCode.CLIENT_ERROR, message = "被打赏的用户id"),
            @ValidateFiled(index = 1, filedName = "articleTitle", notNull = true, icode = BizCode.CLIENT_ERROR, message = "文章标题"),
    })
    @AppAuth(index = 0, clazz = AppSessionUser.class)
    @PostMapping("/preAlipayOrder")
    @ResponseBody
    public AjaxResult preAlipayOrder(@RequestHeader String token, @RequestBody OrderEntityDto orderDto) {
        long startTime = System.currentTimeMillis();
        logger.info("接口调用开始,参数:[dto:{}}]", JSON.toJSONString(orderDto));
        String form = "";
        try {
            AppSessionUser appSessionUser = WebContextUtils.getCurrentSessionUser();
            if(appSessionUser == null){
                throw new BizException(AppBizCode.USER_TOKEN_ERROR);
            }
            if (orderDto.getUid() == null) {
                orderDto.setUid(appSessionUser.getUserId());
            }
            BigDecimal amount = orderDto.getAmount();
            // 付款金额必须大于0
            if (BigDecimal.ZERO.compareTo(amount) == 1){
                return AjaxResult.failed(AppBizCode.PAYMENT_AMOUNT_ERROR);
            }

            // 新增记录
            byte type = orderDto.getOrderType();
            Integer objId = null;
            if (type == OrderEnums.Types.GIVE_FEWARD.getCode()){
                // 打赏功能
                objId = articleAwardLogService.addAwardLog(appConfigs.getBizdbIndex(), orderDto);
            } else if (type == OrderEnums.Types.BUY.getCode()){
                // 购买

            }

            // 创建订单
            OrderEntity orderEntity = new OrderEntity();
            orderEntity.setUid(orderDto.getUid());
            orderEntity.setCash(amount);
            orderEntity.setObjId(objId);
            orderEntity.setObjType(orderDto.getOrderType());

            int addCode = orderService.addOrder(appConfigs.getBizdbIndex(), orderEntity);
            if (addCode == 0){
                // 订单创建失败
                return AjaxResult.failed(BizCode.BUSI_ERROR);
            }

            AlipayClient alipayClient = PayCommonUtil.getAliClient();
            //实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.web.pay
            AlipayTradeWapPayRequest alipayRequest = new AlipayTradeWapPayRequest();//创建API对应的request

            //SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
            Map<String, Object> model = new HashMap<>();
            // 对一笔交易的具体描述信息。如果是多种商品，请将商品描述字符串累加传给body。
            model.put("out_trade_no", orderEntity.getCode());
            model.put("subject", "爱原创——打赏");
            model.put("body", "打赏文章--" + orderDto.getArticleTitle());
            model.put("total_amount", orderDto.getAmount().setScale(2).toString());
            model.put("product_code", "QUICK_WAP_PAY");

            alipayRequest.setReturnUrl(PropertyUtil.getInstance().getProperty("AliPay.returnUrl"));
            //在公共参数中设置回跳和通知地址
            alipayRequest.setNotifyUrl(PropertyUtil.getInstance().getProperty("AliPay.notifyUrl"));
            alipayRequest.setBizContent(JSONObject.toJSONString(model));
            try {
                form = alipayClient.pageExecute(alipayRequest).getBody(); //调用SDK生成表单
                System.out.println(form);
            } catch (AlipayApiException e) {
                long endTime = System.currentTimeMillis();
                logger.error("支付宝支付接口调用异常，支付宝异常!耗时:{},异常信息:{}", (endTime - startTime), e.getMessage(), e);
                throw new BizException(AppBizCode.PAYMENT_ALIPAY_ERROR);
            }

        } catch (BizException e) {
            long endTime = System.currentTimeMillis();
            logger.error("支付宝支付接口调用异常，业务异常!耗时:{},异常信息:{}", (endTime - startTime), e.getMessage(), e);
            return AjaxResult.failed(e.getCode());
        } catch (Exception e) {
            // 记录异常信息
            long endTime = System.currentTimeMillis();
            logger.error("支付宝支付接口调用异常，系统异常!耗时:{},异常信息:{}", (endTime - startTime), e.getMessage(), e);
            return AjaxResult.failed();
        }
        long endTime = System.currentTimeMillis();
        logger.info("支付宝支付接口调用完成,耗时:{},返回结果:[orderStr:{}]", (endTime - startTime), form);
        return AjaxResult.success().putObj(form);
    }

    /**
     * 支付宝H5支付异步通知
     * @param request
     * @return
     */
    @PostMapping("/notify/alipay_h5")
    @ResponseBody
    public String alipayNotify(HttpServletRequest request) {
        Map<String, String> params = new HashMap<String, String>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++){
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            logger.debug("name:" + name + "    value:" + valueStr);
            // 乱码解决，这段代码在出现乱码时使用。
            // valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }
        // 切记alipaypublickey是支付宝的公钥，请去open.alipay.com对应应用下查看。
        boolean flag = true; // 校验公钥正确性防止意外
        try {
            flag = AlipaySignature.rsaCheckV1(params, PropertyUtil.getInstance().getProperty("AliPay.publicKey"),
                    "utf-8", "RSA2");
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        if (flag) {
            String orderCode = params.get("out_trade_no");
            String tradeStatus = params.get("trade_status");

            OrderEntity orderEntity = orderService.getOrderByCode(appConfigs.getBizdbIndex(), orderCode);
            boolean isTradeSuccess = false;
            // 判断交易结果
            switch (tradeStatus) {
                case "TRADE_FINISHED": // 失败
                    orderEntity.setStatus(OrderEnums.Status.FAILED.getCode());
                    break;
                case "TRADE_SUCCESS": // 完成
                    isTradeSuccess = true;
                    orderEntity.setStatus(OrderEnums.Status.SUCCESS.getCode());
                    break;
                case "WAIT_BUYER_PAY": // 待支付
                    orderEntity.setStatus(OrderEnums.Status.WAIT_FOR_PAY.getCode());
                    break;
                case "TRADE_CLOSED": // 交易关闭
                    orderEntity.setStatus(OrderEnums.Status.CLOSE.getCode());
                    break;
                default:
                    break;
            }
            // 修改订单状态
            orderEntity.setUpdatedAt(new Date());
            int updateCode = orderService.update(appConfigs.getBizdbIndex(), orderEntity);

            if (updateCode == 0){
                logger.error("支付宝支付结果修改失败，支付宝支付结果:[{}]，修改后实体:[{}]" , params, orderEntity);
            }
            // 修改记录中的状态
            byte type = orderEntity.getObjType();
            if (type == OrderEnums.Types.GIVE_FEWARD.getCode() && isTradeSuccess){
                // 打赏功能，获取打赏记录
                ArticleAwardLogEntity articleAwardLogEntity = articleAwardLogService.selectByPrimaryKey(appConfigs.getBizdbIndex(), orderEntity.getObjId());
                // 如果已经打赏记录已经是成功状态就直接跳过
                if (OrderEnums.Status.SUCCESS.getCode() != articleAwardLogEntity.getStatus()) {
                    articleAwardLogEntity.setStatus(orderEntity.getStatus());
                    articleAwardLogService.update(appConfigs.getBizdbIndex(), articleAwardLogEntity);

                    AycFinancialDto aycFinancialDto = new AycFinancialDto();
                    aycFinancialDto.setPayType(AycFinancialConst.FINANCIAL_PAY_TYPE_ALIPAY);
                    aycFinancialDto.setPayCode(params.get("trade_no"));
                    aycFinancialDto.setPayAccount(params.get("buyer_logon_id"));
                    aycFinancialDto.setCash(NumberUtils.createBigDecimal(params.get("total_amount")));
                    articleAwardLogService.updateByPayStatus(appConfigs.getBizdbIndex(), orderEntity, aycFinancialDto);
                }
            } else if (type == OrderEnums.Types.BUY.getCode()){
                // 购买

            }
        }

        saveAlipayResultLog(params);
        return "success";
    }

    /**
     * 保存支付宝支付的结果
     * @param params
     */
    private void saveAlipayResultLog(Map<String, String> params) {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat f1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
        AlipayNotice alipayNotice = new AlipayNotice();
        try // set一堆日期和属性
        {
            alipayNotice.setAppId(params.get("app_id"));
            alipayNotice.setBody(params.get("body"));
            alipayNotice.setBuyerId(params.get("buyer_id"));
            alipayNotice.setBuyerLogonId(params.get("buyer_logon_id"));
            alipayNotice.setBuyerPayAmount(Double.parseDouble(params.get("buyer_pay_amount")));
            alipayNotice.setCharset(params.get("charset"));
            alipayNotice.setFundBillList(params.get("fund_bill_list"));
            alipayNotice.setGmtClose(f.parse(params.get("gmt_close")));
            alipayNotice.setGmtCreate(f.parse(params.get("gmt_create")));
            alipayNotice.setGmtPayment(f.parse(params.get("gmt_payment")));
            alipayNotice.setGmtRefund(f1.parse(params.get("gmt_refund")));
            alipayNotice.setNotifyTime(f.parse(params.get("notify_time")));
        } catch (ParseException e) {
            PayCommonUtil.saveE("/data0/log/app/aliParseException", e); // 由于需要在外网测试，所以将错误日志保存一下方便调试
            logger.error("--------------------------------日期转换异常");
            e.printStackTrace();
        }
        try {
            alipayNotice.setInvoiceAmount(Double.parseDouble(params.get("invoice_amount")));
            alipayNotice.setNotifyId(params.get("notify_id"));
            alipayNotice.setNotifyType(params.get("notify_type"));
            alipayNotice.setOutBizNo(params.get("out_biz_no"));
            alipayNotice.setOutTradeNo(params.get("out_trade_no"));
            alipayNotice.setPassbackParams(params.get("passback_params"));
            alipayNotice.setPointAmount(Double.parseDouble(params.get("point_amount")));
            alipayNotice.setReceiptAmount(Double.parseDouble(params.get("receipt_amount")));
            alipayNotice.setRefundFee(Double.parseDouble(params.get("refund_fee")));
            alipayNotice.setSellerEmail(params.get("seller_email"));
            alipayNotice.setSellerId(params.get("seller_id"));
            alipayNotice.setSign(params.get("sign"));
            alipayNotice.setSignType(params.get("sign_type"));
            alipayNotice.setSubject(params.get("subject"));
            alipayNotice.setTotalAmount(Double.parseDouble(params.get("total_amount")));
            alipayNotice.setTradeNo(params.get("trade_no"));
            alipayNotice.setTradeStatus(params.get("trade_status"));
            alipayNotice.setVersion(params.get("version"));
            alipayNotice.setVoucherDetailList(params.get("voucher_detail_list"));
            alipayNotice.setCreateTime(new Date());
            PayCommonUtil.saveLog("/data0/log/app/支付宝实体类.txt", alipayNotice.toString());
        } catch (Exception e) {
            PayCommonUtil.saveE("/data0/log/app/支付宝异常了.txt", e);
        }
        logger.debug("----------------------------执行到了最后！！！--------------");
    }

    /**
     * 微信异步通知
     */
    @RequestMapping("/notify/wx_h5")
    @ResponseBody
    public void wxNotify(HttpServletRequest request, HttpServletResponse response) throws IOException, JDOMException {
        String result = PayCommonUtil.reciverWx(request); // 接收到异步的参数
        Map<String, String> m = new HashMap<String, String>();// 解析xml成map
        if (m != null && !"".equals(m)) {
            m = XMLUtil.doXMLParse(result);
        }
        // 过滤空 设置 TreeMap
        SortedMap<Object, Object> packageParams = new TreeMap<Object, Object>();
        Iterator it = m.keySet().iterator();
        while (it.hasNext()) {
            String parameter = (String) it.next();
            String parameterValue = m.get(parameter);
            String v = "";
            if (null != parameterValue) {
                v = parameterValue.trim();
            }
            packageParams.put(parameter, v);
        }
        // 判断签名是否正确
        String resXml = "";
        if (PayCommonUtil.isTenpaySign("UTF-8", packageParams)) {
            if ("SUCCESS".equals((String) packageParams.get("return_code"))) {
                // 如果返回成功
                // 商户号
                String mch_id = (String) packageParams.get("mch_id");
                // 商户订单号
                String orderCode = (String) packageParams.get("out_trade_no");

                String openid = (String) packageParams.get("openid");

                String total_fee = (String) packageParams.get("total_fee");
                // 微信支付订单号
                String transaction_id = (String) packageParams.get("transaction_id");

                OrderEntity orderEntity = orderService.getOrderByCode(appConfigs.getBizdbIndex(), orderCode);

                // 验证商户ID 和 价格 以防止篡改金额
                if (PropertyUtil.getInstance().getProperty("WxPay.mchid").equals(mch_id) && orderEntity != null
                    // &&
                    // total_fee.trim().toString().equals(orders.getOrderAmount())
                    // 实际项目中将此注释删掉，以保证支付金额相等
                ) {
                    // insertWxNotice(packageParams);
                    // 订单状态为已付款 OrderEnums.Status
                    orderEntity.setStatus(OrderEnums.Status.SUCCESS.getCode());

                    // 变更数据库中该订单状态
                    orderService.update(appConfigs.getBizdbIndex(), orderEntity);

                    // 修改记录中的状态
                    byte type = orderEntity.getObjType();
                    if (type == OrderEnums.Types.GIVE_FEWARD.getCode()){
                        // 打赏功能，获取打赏记录
                        ArticleAwardLogEntity articleAwardLogEntity = articleAwardLogService.selectByPrimaryKey(appConfigs.getBizdbIndex(), orderEntity.getObjId());
                        // 如果已经打赏记录已经是成功状态就直接跳过
                        if (OrderEnums.Status.SUCCESS.getCode() != articleAwardLogEntity.getStatus()){
                            articleAwardLogEntity.setStatus(orderEntity.getStatus());
                            articleAwardLogService.update(appConfigs.getBizdbIndex(), articleAwardLogEntity);

                            // 打赏功能，获取打赏记录
                            AycFinancialDto aycFinancialDto = new AycFinancialDto();
                            aycFinancialDto.setPayType(AycFinancialConst.FINANCIAL_PAY_TYPE_WX);
                            aycFinancialDto.setPayCode(transaction_id);
                            aycFinancialDto.setPayAccount(openid);
                            aycFinancialDto.setCash(NumberUtils.createBigDecimal(total_fee).divide(new BigDecimal(100)).setScale(2));
                            articleAwardLogService.updateByPayStatus(appConfigs.getBizdbIndex(), orderEntity, aycFinancialDto);
                        }
                    } else if (type == OrderEnums.Types.BUY.getCode()){
                        // 购买
                    }

                    resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
                            + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
                } else {
                    resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
                            + "<return_msg><![CDATA[参数错误]]></return_msg>" + "</xml> ";
                }
            } else {
                // 如果微信返回支付失败，将错误信息返回给微信
                resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
                        + "<return_msg><![CDATA[交易失败]]></return_msg>" + "</xml> ";
            }
        } else {
            resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
                    + "<return_msg><![CDATA[通知签名验证失败]]></return_msg>" + "</xml> ";
        }

        // 处理业务完毕，将业务结果通知给微信
        // ------------------------------
        BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
        out.write(resXml.getBytes());
        out.flush();
        out.close();
    }
}
