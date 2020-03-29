package com.zlove.base.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.Cipher;

import android.annotation.SuppressLint;
import android.text.TextUtils;


public class EncryptUtil {

    public static String getStringMd5(String str) throws NoSuchAlgorithmException {
        if (str == null || TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("source string empty.");
        }
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(str.getBytes());
        byte b[] = md.digest();
        int i;
        StringBuffer buf = new StringBuffer("");
        for (int offset = 0; offset < b.length; offset++) {
            i = b[offset];
            if (i < 0)
                i += 256;
            if (i < 16)
                buf.append("0");
            buf.append(Integer.toHexString(i));
        }
        str = buf.toString();
        return str;
    }

    private static final int MAX_ENCRYPT_BLOCK = 117;

    @SuppressLint("TrulyRandom")
    public static List<String> encryptByPublicKey(byte[] data, String publicKey) throws Exception {
        // Generate Key
        byte[] keyBytes = Base64.decode(publicKey);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicK = keyFactory.generatePublic(x509KeySpec);
        // Cipher
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, publicK);
        int inputLen = data.length;
        int offSet = 0;
        byte[] cache;
        int i = 0;
        List<String> result = new ArrayList<String>();
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            result.add(Base64.encodeBytes(cache));
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        return result;
    }

    @SuppressWarnings("unused")
    private static String convertToHex(byte[] data) {
        StringBuffer buf = new StringBuffer();
        int length = data.length;
        for (int i = 0; i < length; ++i) {
            int halfbyte = (data[i] >>> 4) & 0x0F;
            int two_halfs = 0;
            do {
                if ((0 <= halfbyte) && (halfbyte <= 9))
                    buf.append((char) ('0' + halfbyte));
                else
                    buf.append((char) ('a' + (halfbyte - 10)));
                halfbyte = data[i] & 0x0F;
            } while (++two_halfs < 1);
        }

        return buf.toString();

    }

    public static String getSHA1String(String source) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA1");
            messageDigest.update(source.getBytes());
            return BYTE2HEX(messageDigest.digest());
        } catch (NoSuchAlgorithmException ex) {
        }
        return "";
    }

    @SuppressLint("DefaultLocale") 
    public static String BYTE2HEX(byte[] b) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1)
                hs = hs + "0" + stmp;
            else
                hs = hs + stmp;
            if (n < b.length - 1)
                hs = hs + "";
        }
        return hs.toUpperCase();
    }

    /*
     * public static String SHA1(String text) throws NoSuchAlgorithmException, UnsupportedEncodingException {
     * MessageDigest md = MessageDigest.getInstance("SHA-1"); byte[] sha1hash = new byte[40];
     * md.update(text.getBytes()); sha1hash = md.digest(); return convertToHex(sha1hash); }
     */

    public static String getFileMd5(File file) throws FileNotFoundException {
        String value = null;
        FileInputStream in = new FileInputStream(file);
        try {
            MappedByteBuffer byteBuffer = in.getChannel().map(FileChannel.MapMode.READ_ONLY, 0, file.length());
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(byteBuffer);
            BigInteger bi = new BigInteger(1, md5.digest());
            value = bi.toString(16);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return value;
    }



}
