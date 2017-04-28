package com.example.newestandroidframe.network.config;

/**
 * Created by zhangyb on 2017/4/28.
 */

public interface ServerConfig {

    String getContentType();

    String getAppId();

    String getAppVersion();

    String getAppKey();

    String getUuid();

    String getAccessToken();

    void setAccessToken(String accessToken);
}
