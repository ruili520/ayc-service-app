
package com.ayc.service.app.configs;

import com.ayc.framework.common.ICode;

/**
 * Author:  ysj
 * Date:  2019/1/21 18:08
 * Description:业务异常扩展类
 */
public enum TestBizCode implements ICode {
    USER_LOGIN_FAILED("903001","用户登陆失败"),;

    private final String code;
    private String message;
    public String getCode() {
        return code;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    TestBizCode(String code, String message){
        this.code = code;
        this.message = message;
    }
}