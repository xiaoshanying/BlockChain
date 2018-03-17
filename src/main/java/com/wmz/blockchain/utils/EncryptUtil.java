package com.wmz.blockchain.utils;

import org.springframework.util.StringUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by wmz on 2018/3/17.
 * 加密工具类
 *
 * @author wmz
 */
public class EncryptUtil {

    enum EncryptEnum {
        MD5("MD5"),
        SHA_512("SHA-512"),
        SHA_256("SHA-256");

        private String type;

        EncryptEnum(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }
    }

    /**
     * 传入字符串,返回SHA-256加密字符串
     *
     * @param str
     * @return
     */
    public static String getSHA256(final String str) {
        return baseEncrypt(str, EncryptEnum.SHA_256);
    }

    /**
     * 传入字符串,返回SHA-512加密字符串
     *
     * @param str
     * @return
     */
    public static String getSHA512(final String str) {
        return baseEncrypt(str, EncryptEnum.SHA_512);
    }

    /**
     * 获取md5加密串
     *
     * @param str
     * @return
     */
    public static String getMd5(final String str) {
        return baseEncrypt(str, EncryptEnum.MD5);
    }

    private static String baseEncrypt(final String str, final EncryptEnum encryptEnum) {
        String result = null;
        if (!StringUtils.isEmpty(str)) {
            try {
                MessageDigest messageDigest = MessageDigest.getInstance(encryptEnum.getType());
                messageDigest.update(str.getBytes());
                byte[] byteBuffer = messageDigest.digest();
                StringBuffer stringBuffer = new StringBuffer();
                for (int i = 0; i < byteBuffer.length; i++) {
                    String hex = Integer.toHexString(0xff & byteBuffer[i]);
                    if (hex.length() == 1) {
                        stringBuffer.append('0');
                    }
                    stringBuffer.append(hex);
                }
                result = stringBuffer.toString();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

}
