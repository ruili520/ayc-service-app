package com.ayc.service.app.common.utils;

/**
 * 验证码工具类
 *
 * @author wwq
 * @version 1.0
 * @createDate 2019/2/19 20:15
 */
public class VerCodeUtil {

    /**
     * 创建验证码 4位随机数
     * @return
     */
    public static Integer createVerCode(){
        return (new Double(Math.random() * 8999)).intValue() + 1000;
    }

    public static void main(String[] args) {
        System.out.println(VerCodeUtil.createVerCode());
    }
}
