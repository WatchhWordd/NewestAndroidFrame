package com.example.newestandroidframe.util;

import android.text.TextUtils;

import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * Created by zhangyb on 2017/4/28.
 */

public class NetUtil {

    public static String md5(String string) {
        MessageDigest digest;
        StringBuffer buffer = new StringBuffer();
        try {
            digest = MessageDigest.getInstance("md5");

            byte[] result = digest.digest(string.getBytes("utf-8"));
            for (byte b : result) {
                int number = b & 0xff;
                String str = Integer.toHexString(number);
                if (str.length() == 1) {
                    buffer.append("0");
                }
                buffer.append(str);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return buffer.toString();
    }

    public static String getSign(String url, String body, String appId, String appKey, String timestamp) {
        try {
            url = new URL(url).getPath();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        appKey = appKey.trim();
        appKey = appKey.replaceAll("\"", "");
        if (body != null) {
            body = body.trim();
        }
        if (!TextUtils.isEmpty(body)) {
            body = body.replaceAll("", "");
            body = body.replaceAll("\t", "");
            body = body.replaceAll("\r", "");
            body = body.replaceAll("\n", "");
        }
        StringBuffer sb = new StringBuffer();
        sb.append(url).append(body).append(appId).append(appKey).append(timestamp);

        MessageDigest md;
        byte[] bytes = null;
        try {
            md = MessageDigest.getInstance("SHA256");
            bytes = md.digest(sb.toString().getBytes("utf-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return binaryToHexString(bytes);
    }

    private static String binaryToHexString(byte[] bytes) {
        StringBuilder hex = new StringBuilder();
        String hexStr = "0123456789abcdef";
        for (int i = 0; i < bytes.length; i++) {
            hex.append(String.valueOf(hexStr.charAt((bytes[i] & 0xF0) >> 4)));
            hex.append(String.valueOf(hexStr.charAt(bytes[i] & 0x0F)));
        }
        return hex.toString();
    }

    public static String sha256(String string) {

        MessageDigest digest;
        StringBuffer buffer = new StringBuffer();
        try {
            digest = MessageDigest.getInstance("sha-256");

            byte[] result = digest.digest(string.getBytes("utf-8"));
            for (byte b : result) {
                int number = b & 0xff;
                String str = Integer.toHexString(number);
                if (str.length() == 1) {
                    buffer.append("0");
                }
                buffer.append(str);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return buffer.toString();
    }

    public static SSLSocketFactory getUnsafeSslSocketFactory() {
        TrustManager[] trustAllCerts = new TrustManager[]{
            getUnsafeX509TrustManager()
        };

        try {
            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new SecureRandom());
            return sslContext.getSocketFactory();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static X509TrustManager getUnsafeX509TrustManager() {
        return new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        };
    }

    public static HostnameVerifier getUnsafeHostnameVerifier() {
        return new HostnameVerifier() {
            @Override
            public boolean verify(String s, SSLSession sslSession) {
                return true;
            }
        };
    }
}
