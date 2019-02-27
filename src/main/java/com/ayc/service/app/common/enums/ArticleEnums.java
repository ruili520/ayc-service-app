package com.ayc.service.app.common.enums;

import com.ayc.framework.common.BizException;
import com.ayc.service.app.configs.AppBizCode;

public class ArticleEnums {

    /**
     * 文章本身状态
     */
    public enum Status{
        WAIT_FOR_AUDIT((byte) 0, "等待审核"),
        PUBLISH((byte) 1, "已发布"),
        NOT_PASS((byte) 2, "审核未通过"),
        NOT_PUBLISH((byte) 3, "未发布"),
        DELETE((byte) -1, "删除");

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
     * 打赏记录状态
     */
    public enum AwardLogStatus{
        DEFAULT((byte) 0, "默认"),
        SUCCESS((byte) 1, "成功"),
        FAILED((byte) -1, "失败");

        private Byte code;
        private String description;

        AwardLogStatus(Byte code, String description){
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
     * 排序字段
     */
    public enum OrderBy{
        TIME("1", "updated_at"),
        HOT("2", "read_times"),
         ;

        private String code;
        private String column;

         OrderBy(String code, String column){
            this.code = code;
            this.column = column;
        }

         public String getCode() {
             return code;
         }

         public String getColumn() {
             return column;
         }

         public static String getColumnByCode(String code){
             if (isOrderBy(code)){
                 for (OrderBy e : OrderBy.values()) {
                     if (e.getCode().equals(code)){
                         return e.getColumn();
                     }
                 }
             }

             throw new BizException(AppBizCode.ARTICLE_ORDERBY_UNDEFINED);
         }

         private static boolean isOrderBy(String code) {
             for (OrderBy e : OrderBy.values()) {
                 if (e.getCode().equals(code)){
                    return true;
                 }
             }
             return false;
         }
     }
}
