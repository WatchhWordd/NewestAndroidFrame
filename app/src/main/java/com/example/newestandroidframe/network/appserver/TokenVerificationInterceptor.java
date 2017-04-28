package com.example.newestandroidframe.network.appserver;

import android.text.TextUtils;

import com.example.newestandroidframe.network.CommonResponse;
import com.example.newestandroidframe.network.config.ServerConfig;
import com.google.gson.Gson;

import java.io.EOFException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;



public class TokenVerificationInterceptor implements Interceptor {

    private ServerConfig config;
    private OnTokenVerificationListener onTokenVerificationListener;

    public TokenVerificationInterceptor(ServerConfig config) {
        this.config = config;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (TextUtils.isEmpty(config.getAccessToken()) && request.url().toString().indexOf("login") == -1) {
            return null;
        }
        Response response = chain.proceed(request);
        ResponseBody responseBody = response.body();
        long contentLength = responseBody.contentLength();
        BufferedSource source = responseBody.source();
        source.request(Long.MAX_VALUE);
        Buffer buffer = source.buffer();
        Charset charset = Charset.forName("UTF-8");
        MediaType contentType = responseBody.contentType();
        if (contentType != null) {
            try {
                charset = contentType.charset(Charset.forName("UTF-8"));
            } catch (UnsupportedCharsetException e) {
                e.printStackTrace();
                return response;
            }
        }
        if (!isPlaintext(buffer)) {
            return response;
        }
        String bodyString = null;
        if (contentLength != 0) {
            bodyString = buffer.clone().readString(charset);
        }
        if (isInvalid(bodyString) && onTokenVerificationListener != null) {
            onTokenVerificationListener.onInvalid();
        }
        return response;
    }

    private boolean isInvalid(String bodyString) {
        CommonResponse cr;
        if (bodyString != null) {
            cr = new Gson().fromJson(bodyString, CommonResponse.class);
        } else {
            return false;
        }
        // ("21016", "Token不是由此终端创建，未通过token验证。"),
        // ("21018", "Token不存在，未通过token验证。"),
        // ("21019", "Token已过期，未通过token验证。"),
        if (cr != null && ("21016".equals(cr.getRetCode()) || "B00007-21016".equals(cr.getRetCode())
            || "21018".equals(cr.getRetCode()) || "B00007-21018".equals(cr.getRetCode())
            || "21019".equals(cr.getRetCode()) || "B00007-21019".equals(cr.getRetCode()))) {
            return true;
        }
        return false;
    }

    private static boolean isPlaintext(Buffer buffer) {
        try {
            Buffer prefix = new Buffer();
            long byteCount = buffer.size() < 64 ? buffer.size() : 64;
            buffer.copyTo(prefix, 0, byteCount);
            for (int i = 0; i < 16; i++) {
                if (prefix.exhausted()) {
                    break;
                }
                int codePoint = prefix.readUtf8CodePoint();
                if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
                    return false;
                }
            }
            return true;
        } catch (EOFException e) {
            return false; // Truncated UTF-8 sequence.
        }
    }

    public void setOnTokenVerificationListener(OnTokenVerificationListener onTokenVerificationListener) {
        this.onTokenVerificationListener = onTokenVerificationListener;
    }
}
