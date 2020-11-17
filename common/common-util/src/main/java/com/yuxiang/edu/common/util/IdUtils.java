package com.yuxiang.edu.common.util;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;

/**
 * @Author: yuxiang
 * @Date: 2020/11/17 10:26
 */
public class IdUtils {


    public static void main(String[] args) {

        Snowflake snowflake1 = IdUtil.getSnowflake(1, 1);
        Snowflake snowflake = IdUtil.createSnowflake(2, 1);

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                System.out.println("1=1====>" + snowflake1.nextId());
            }
        }).start();



        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                System.out.println("2=1====>" + snowflake.nextId());
            }
        }).start();

    }
}
