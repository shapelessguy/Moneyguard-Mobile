package com.example.mobilemoneyguard;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.spec.KeySpec;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;

public class StringCipher {
    private static String IV = "IV_MONEY_16_BYTE";
    private static String PASSWORD = "PASSWORD_VALUE_MONEYGUARD";
    private static String SALT = "SALT_MONEYGUARD_VALUE";

    public static String encryptAndEncode(String sInputFilename, String sOutputFilename) {
        try {
            Cipher c = getCipher(Cipher.ENCRYPT_MODE);
            String str = FileUtils.readFileToString(new File(sInputFilename), "utf-8");
            byte[] encryptedVal = c.doFinal(getBytes(str));
            //byte[] encryptedVal = c.doFinal(getBytes(raw));
            String s = getString(Base64.encodeBase64(encryptedVal));
            System.out.println(s);
            return s;
        } catch (Throwable t) {
            System.out.println("Error in encrypting");
            throw new RuntimeException(t);
        }
    }

    public static String decodeAndDecrypt(String sInputFilename, String sOutputFilename) throws Exception {
        byte[] decodedValue = Base64.decodeBase64(getBytes(FileUtils.readFileToString(new File(sInputFilename), "utf-8")));
        Cipher c = getCipher(Cipher.DECRYPT_MODE);
        byte[] decValue = c.doFinal(decodedValue);
        System.out.println(new String(decValue));
        return new String(decValue);
    }

    private static String getString(byte[] bytes) throws UnsupportedEncodingException {
        return new String(bytes, "UTF-8");
    }

    private static byte[] getBytes(String str) throws UnsupportedEncodingException {
        return str.getBytes("UTF-8");
    }

    private static Cipher getCipher(int mode) throws Exception {
        Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
        byte[] iv = getBytes(IV);
        c.init(mode, generateKey(), new IvParameterSpec(iv));
        return c;
    }

    private static Key generateKey() throws Exception {
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        char[] password = PASSWORD.toCharArray();
        byte[] salt = getBytes(SALT);

        KeySpec spec = new PBEKeySpec(password, salt, 1000, 128);
        SecretKey tmp = factory.generateSecret(spec);
        byte[] encoded = tmp.getEncoded();
        return new SecretKeySpec(encoded, "AES");
    }
}
