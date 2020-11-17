package com.yuxiang.edu.common.util;

import cn.hutool.core.util.RandomUtil;

/**
 * @Author: yuxiang
 * @Date: 2020/11/17 10:52
 */
public class NicknameUtils {

    public static final String NICKNAME_PREFIX = "vip_";

    public static final int NICKNAME_LENGTH = 10;

    public static final String getNickname() {

        return NICKNAME_PREFIX + RandomUtil.randomString(NICKNAME_LENGTH);
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {

            System.out.println(NicknameUtils.getNickname());
        }
    }
}
