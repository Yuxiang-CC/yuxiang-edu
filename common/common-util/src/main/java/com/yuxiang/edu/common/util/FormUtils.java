package com.yuxiang.edu.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串操作工具类
 *
 * @author qy
 * @since 1.0
 */
public class FormUtils {

    private static Pattern mobileP = Pattern.compile("^[1][3,4,5,7,8,9][0-9]{9}$");
    private static Pattern mailP = Pattern.compile("^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$");

    /**
     * 手机号验证
     */
    public static boolean isMobile(String str) {
        Matcher m = mobileP.matcher(str);
        return m.matches();
    }

    /**
     * 邮箱验证
     */
    public static boolean isMail(String str) {
        Matcher m = mailP.matcher(str);
        return m.matches();
    }

    public static void main(String[] args) {
//        System.out.println(com.yuxiang.edu.common.util.FormUtils.isMobile("18339533333"));
        System.out.println(FormUtils.isMail("1833@qq.com"));
        System.out.println(FormUtils.isMail("1833.@qq.com"));
        System.out.println(FormUtils.isMail("1833@.qq.com"));
        System.out.println(FormUtils.isMail("183+3@qq.com"));
        System.out.println(FormUtils.isMail("_1833@q.cn"));
    }

}
