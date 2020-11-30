package com.zrz.mqtt;

import com.baomidou.dynamic.datasource.toolkit.CryptoUtils;

public class Demo {

    public static void main(String[] args) throws Exception {
        String password = "123456";

        String encodePassword = CryptoUtils.encrypt(password);
        System.out.println(encodePassword);

        //自定义publicKey
        String[] arr = CryptoUtils.genKeyPair(512);
        System.out.println("privateKey:" + arr[0]);
        System.out.println("publicKey:" + arr[1]);
        System.out.println("password:" + CryptoUtils.encrypt(arr[0], password));
    }
}
