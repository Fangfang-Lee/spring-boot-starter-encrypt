package com.duliday.encrypt.util;

import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
import com.sun.org.apache.xml.internal.security.utils.Base64;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

/**
 * @Date 2020/1/8 下午7:38
 * @Email justase@163.com
 * @Author Jason Lee
 * @Description AES加解密工具类
 */
public class AESUtils {

    private static final String ALGORITHM = "AES/ECB/PKCS5Padding";

    public static String encrypt(String encryptStr, String encryptKey)
        throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException, BadPaddingException, IllegalBlockSizeException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(128);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(encryptKey.getBytes(), "AES"));
        return Base64.encode(cipher.doFinal(encryptStr.getBytes("utf-8")));
    }

    public static String decrypt(String decryptStr, String decryptKey)
        throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException, BadPaddingException, IllegalBlockSizeException, Base64DecodingException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(128);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(decryptKey.getBytes(), "AES"));
        return new String(cipher.doFinal(Base64.decode(decryptStr.getBytes())));
    }

    public static void main(String[] args) throws Exception {
        String key = "1234567890abcdef";
        String content = "你好";
        String encryptContent = AESUtils.encrypt(content, key);
        System.out.println(encryptContent);
        String decryptContent = AESUtils.decrypt(encryptContent, key);
        System.out.println(decryptContent);
        
    }
}
