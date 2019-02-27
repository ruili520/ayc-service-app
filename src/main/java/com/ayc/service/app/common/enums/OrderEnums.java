package com.ayc.service.app.common.enums;

public class OrderEnums {

    /**
     * 订单支付状态
     */
    public enum Status{
        WAIT_FOR_PAY((byte) 0, "待支付"),
        SUCCESS((byte) 1, "已支付"),
        FAILED((byte) -1, "交易失败"),
        CLOSE((byte) -2, "交易取消"),
        ;

        private Byte code;
        private String description;

        Status(Byte code, String description){
            this.code = code;
            this.description = description;
        }

        public Byte getCode() {
            return code;
        }

        public String getDescription() {
            return description;
        }
    }

    /**
     * 订单类型
     */
    public enum Types{
        BUY((byte) 1, "购买"),
        GIVE_FEWARD((byte) 2, "打赏");

        private Byte code;
        private String description;

        Types(Byte code, String description){
            this.code = code;
            this.description = description;
        }

        public Byte getCode() {
            return code;
        }

        public String getDescription() {
            return description;
        }
    }
}
