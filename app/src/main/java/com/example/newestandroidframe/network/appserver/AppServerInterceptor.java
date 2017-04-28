package com.example.newestandroidframe.network.appserver;

import android.util.Pair;

import com.example.newestandroidframe.network.config.ServerConfig;
import com.example.newestandroidframe.util.NetUtil;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;
import java.util.Set;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;
import rx.Observable;
import rx.functions.Func0;

/**
 * Created by zhangyb on 2017/4/28.
 */

public class AppServerInterceptor implements Interceptor {

    private static final SimpleDateFormat TIMESTAMP_FORMAT =
        new SimpleDateFormat("yyyyMMddHHmmss", Locale.US);
    private ServerConfig config;
    private int requestSn = 0;
    private String currentSequenceId;

    public AppServerInterceptor(ServerConfig config) {
        this.config = config;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request =chain.request();
        request =addRequestHeader(request);
        request =signRequest(request);
        Response response = chain.proceed(request);
        collectToken(response);
        return response;
    }

    private Request addRequestHeader(Request request) {
        Request.Builder builder = request.newBuilder();
        Set<String> names = request.headers().names();
        String time = getCurrentTime();

        Observable.from(Arrays.asList(
            pair("Content-Type", () -> config.getContentType()),
            pair("appId", () -> config.getAppId()),
            pair("appVersion", () -> config.getAppVersion()),
            pair("appKey", () -> config.getAppKey()),
            pair("clientId", this::generateClientId),
            pair("accessToken", () -> config.getAccessToken()),
            pair("sequenceId", () -> generateNextSequenceId(time)),
            pair("timestamp", () -> time)))
            .filter(pair -> !names.contains(pair.first))
            .forEach(pair -> builder.addHeader(pair.first, pair.second.call()));
        return builder.build();
    }


    private String getCurrentTime() {
        return TIMESTAMP_FORMAT.format(new Date());
    }

    private String generateClientId() {
        return config.getUuid();
    }


    private Pair<String, Func0<String>> pair(String name, Func0<String> func0) {
        return Pair.create(name, func0);
    }

    private String generateNextSequenceId(String timestamp) {
        increaseRequestSn();
        currentSequenceId = String.format(Locale.US, "%s%06d", timestamp, requestSn);
        return currentSequenceId;
    }

    private void increaseRequestSn() {
        requestSn++;
        if (requestSn > 999999) {
            requestSn = 0;
        }
    }

    private Request signRequest(Request request) throws IOException {
        String body = getRequestBodyAsString(request);
        if (body == null) {
            body = "";
        } else {
            body = body.replaceAll("\\s+", "");
        }

        String timestamp = request.header("timestamp");
        String content = body + config.getAppId() + config.getAppKey() + timestamp;

        String sign = NetUtil.md5(content);

        return request.newBuilder().addHeader("sign", sign).build();
    }

    private String getRequestBodyAsString(Request request) throws IOException {
        RequestBody requestBody = request.body();
        if (requestBody == null) {
            return null;
        }

        Buffer buffer = new Buffer();
        requestBody.writeTo(buffer);
        return buffer.readUtf8();
    }

    private void collectToken(Response response) {
        if (response.headers().names().contains("accessToken")) {
            String accessToken = response.header("accessToken");
            config.setAccessToken(accessToken);
        }
    }

    public void setConfig(ServerConfig config) {
        this.config = config;
    }
}
