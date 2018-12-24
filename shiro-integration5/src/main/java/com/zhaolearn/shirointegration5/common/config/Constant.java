package com.zhaolearn.shirointegration5.common.config;

/**
 * 常量
 * 时间过期命名为EXRP_单位_过期时间
 * @author: HeHaoZhao
 * @date: 2018/12/24 14:17
 */
public class Constant {

    /**
     * 设置redis成功后会返回OK，用这个常量保存，避免魔法值
     */
    public final static String OK = "OK";

    /**
     * 时间，以秒为单位，一分钟
     */
    public final static int EXRP_S_MINUTE = 60;

    /**
     * 时间，以秒为单位，半小时
     */
    public final static int EXRP_S_HALFHOUR = 30 * 60;

    /**
     * 时间，以秒为单位，一小时
     */
    public final static int EXRP_S_HOUR = 60 * 60;

    /**
     * 时间，以秒为单位，一天
     */
    public final static int EXRP_S_DAY = 60 * 60 * 24;

    /**
     * 时间，以毫秒为单位，一分钟
     */
    public final static int EXRP_MS_MINUTE = 60*1000;

    /**
     * 时间，以毫秒为单位，一小时
     */
    public final static int EXRP_MS_HALFHOUR = 30 * 60*1000;

    /**
     * 时间，以毫秒为单位，一小时
     */
    public final static int EXRP_MS_HOUR = 60 * 60*1000;

    /**
     * 时间，以毫秒为单位，一天
     */
    public final static int EXRP_MS_DAY = 60 * 60 * 24*1000;

    /**
     * redis-key-前缀-shiro:cache:
     */
    public final static String PREFIX_SHIRO_CACHE = "shiro:cache:";

}
