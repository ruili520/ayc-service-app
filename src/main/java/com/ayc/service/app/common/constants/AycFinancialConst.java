package com.ayc.service.app.common.constants;

public class AycFinancialConst {

    // 流水表收支行为
    // 购买
    public static final byte FINANCIAL_ACTION_BUY = 1;
    // 打赏
    public static final byte FINANCIAL_ACTION_AWARD = 2;
    // 提现
    public static final byte FINANCIAL_ACTION_WITHDRAW = 3;
    // 转化
    public static final byte FINANCIAL_ACTION_CONVERT = 4;
    // 被购买
    public static final byte FINANCIAL_ACTION_BE_BUY = 5;
    // 被打赏
    public static final byte FINANCIAL_ACTION_BE_AWARD = 6;

    // 流水表支付渠道
    // 微信
    public static final byte FINANCIAL_PAY_TYPE_WX = 1;
    // 余额
    public static final byte FINANCIAL_PAY_TYPE_BALANCE = 2;
    // 支付宝
    public static final byte FINANCIAL_PAY_TYPE_ALIPAY = 3;
}
