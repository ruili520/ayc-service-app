package com.ayc.service.app.common.utils;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Date;

/**
 * 时间工具类
 *
 * @author wwq
 * @version 1.0
 * @createDate 2019/2/13 0:13
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {

    /**
     * 获取当前时间的字符串 格式（yyyy-MM-dd）
     * @return
     */
    public static String getDate() {
        return formatDate(new Date());
    }

    /**
     * 获取当前时间的字符串 格式（yyyy-MM-dd HH:mm:ss）
     * @return
     */
    public static String getDateTime(){
        return formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 格式化日期
     * @param date
     * @param pattern
     * @return
     */
    public static String formatDate(Date date, Object... pattern){
        String formatDate = null;
        if (pattern != null && pattern.length > 0){
            formatDate = DateFormatUtils.format(date, pattern[0].toString());
        } else {
            formatDate = DateFormatUtils.format(date, "yyyy-MM-dd");
        }
        return formatDate;
    }

    /**
     * 获取当前时间的年份字符串
     * @return
     */
    public static String getYear(){
        return formatDate(new Date(), "yyyy");
    }

    /**
     * 获取当前时间的月份字符串
     * @return
     */
    public static String getMonth(){
        return formatDate(new Date(), "MM");
    }


    /**
     * 获取当前时间的日期字符串
     * @return
     */
    public static String getDay() {
        return formatDate(new Date(), "dd");
    }

    /**
     * 获取当前星期字符串
     * @return
     */
    public static String getWeek() {
        return formatDate(new Date(), "E");
    }

    /**
     * 获取当前的时间，精确到毫秒
     * @return
     */
    public static String getCurrentTime() {
        return formatDate(new Date(), "yyyyMMddHHmmssSSS");
    }
}
