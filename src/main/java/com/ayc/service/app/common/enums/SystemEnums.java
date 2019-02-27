package com.ayc.service.app.common.enums;


public class SystemEnums {

    public enum YesOrNo{
        YES(1),
        NO(0);
        private Integer code;
        YesOrNo(Integer code){
            this.code = code;
        }

        public Integer getCode() {
            return code;
        }
    }

    /**
     * 排序枚举
     */
    public enum Order{
        ASC("ASC"),
        DESC("DESC"),
        ;
        private String order;
        Order(String order){
            this.order = order;
        }

        public String getOrder() {
            return order;
        }
    }

}
