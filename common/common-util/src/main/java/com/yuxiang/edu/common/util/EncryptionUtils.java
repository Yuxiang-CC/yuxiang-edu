package com.yuxiang.edu.common.util;

import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;

import java.io.UnsupportedEncodingException;

/**
 * @Author: yuxiang
 * @Date: 2020/11/17 9:41
 */
public class EncryptionUtils {

    /**
     * hutool 工具包DigestUtil.md5Hex("yuxiang2020"); 加密后得到
     */
    private static final byte[] SALT = "30dbec59c0f6deb7aaf2ed466fbd810f".getBytes();

    /**
     * 加密算法
     */
    private static final Digester sha512 = new Digester(DigestAlgorithm.SHA512);

    static {
        sha512.setSalt(SALT);
    }

    public static String encodePassword(final String msg) {

        return sha512.digestHex(msg);
    }



    public static void main(String[] args) throws UnsupportedEncodingException {


//        Digester sha512 = new Digester(DigestAlgorithm.SHA512);
//        sha512.setSalt(SALT);
//        String passwords = DigestUtil.md5Hex("yuxiang2020");
//        System.out.println(passwords);
        System.out.println(EncryptionUtils.encodePassword("yuxiang"));
        System.out.println(EncryptionUtils.encodePassword("yuxiang"));
        System.out.println(EncryptionUtils.encodePassword("yuxiang1"));
        System.out.println(EncryptionUtils.encodePassword("yuxiang0"));


    }
}
