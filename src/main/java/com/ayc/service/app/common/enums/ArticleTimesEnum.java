package com.ayc.service.app.common.enums;

public class ArticleTimesEnum {

    public enum Types{
        READ((byte) 1, "阅读"),
        SHARE((byte) 2, "分享"),
        LIKE((byte) 3, "点赞");

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
