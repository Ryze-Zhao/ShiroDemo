package com.zhaolearn.shirointegration5.common.utils;

public class MyStringUtils extends org.apache.commons.lang3.StringUtils {
    /**
     * 定义下划线
     */
    private static final char UNDERLINE = '_';


     /**
      * Byte数组为空判断,参考isEmpty(可变数组)
      * @param bytes
      * @return: boolean
      * @author: HeHaoZhao
      * @date: 2018/12/24 11:46
      */
    public static boolean isNull(byte[] bytes){
        return bytes.length == 0 || bytes == null;
    }

    /**
     * Byte数组不为空判断,参考isNotEmpty(可变数组)
     * @param bytes
     * @return: boolean
     * @author: HeHaoZhao
     * @date: 2018/12/24 11:46
     */
    public static boolean isNotNull(byte[] bytes) {
        return !isNull(bytes);
    }


    /**
     * 驼峰转下划线工具
     * @param param
     * @return: java.lang.String
     * @author: HeHaoZhao
     * @date: 2018/12/24 11:44
     */
    public static String camelToUnderline(String param) {
        if (isNotBlank(param)) {
            int len = param.length();
            StringBuilder sb = new StringBuilder(len);
            for (int i = 0; i < len; ++i) {
                char c = param.charAt(i);
                if (Character.isUpperCase(c)) {
                    sb.append(UNDERLINE);
                    sb.append(Character.toLowerCase(c));
                } else {
                    sb.append(c);
                }
            }
            return sb.toString();
        } else {
            return "";
        }
    }

    /**
     * 下划线转驼峰工具
     * @param param
     * @return: java.lang.String
     * @author: HeHaoZhao
     * @date: 2018/12/24 11:43
     */
    public static String underlineToCamel(String param) {
        if (isNotBlank(param)) {
            int len = param.length();
            StringBuilder sb = new StringBuilder(len);
            for (int i = 0; i < len; ++i) {
                char c = param.charAt(i);
                if (c == 95) {
                    ++i;
                    if (i < len) {
                        sb.append(Character.toUpperCase(param.charAt(i)));
                    }
                } else {
                    sb.append(c);
                }
            }
            return sb.toString();
        } else {
            return "";
        }
    }


    /**
     * 在字符串两周添加''
     * @param param
     * @return: java.lang.String
     * @author: HeHaoZhao
     * @date: 2018/12/24 11:43
     */
    public static String addSingleQuotes(String param) {
        return "\'" + param + "\'";
    }
}
