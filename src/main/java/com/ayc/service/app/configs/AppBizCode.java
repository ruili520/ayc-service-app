package com.ayc.service.app.configs;

import com.ayc.framework.common.ICode;

/**
 * Author:  wwb
 * Date:  2019/2/2215:01
 * Description:App业务异常代码定义
 */
public enum AppBizCode  implements ICode {
    ARTICLE_NOT_FOUND("904001","文章未找到"),
    ARTICLE_ORDERBY_UNDEFINED("904002","文章排序字段未定义"),

    PAYMENT_AMOUNT_ERROR("905001", "支付金额不能小于0"),
    PAYMENT_ALIPAY_ERROR("905101", "支付宝接口异常"),

    USER_TOKEN_ERROR("906001","token失效，请重新登陆");


    private final String code;
    private String message;

    AppBizCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }}
