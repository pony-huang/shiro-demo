package com.ponking.utils;

import org.apache.shiro.crypto.hash.Md5Hash;

import java.util.UUID;

/**
 * @author Peng
 * @date 2020/6/26--21:06
 **/
public class RandomUtil {


    private final static int HASH_TIMES = 1024;

    public static String createSalt(){
       return  UUID.randomUUID().toString().replace("-", "");
    }

    public static String encryptByMd5(String password,String salt){
        Md5Hash md5Hash = new Md5Hash(password,salt,HASH_TIMES);
        return md5Hash.toString();
    }
}
