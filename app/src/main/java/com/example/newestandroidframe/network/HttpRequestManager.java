package com.example.newestandroidframe.network;

import android.content.Context;

import com.example.newestandroidframe.FrameApp;
import com.example.newestandroidframe.network.appserver.AppServerInterceptor;
import com.example.newestandroidframe.network.appserver.TokenVerificationInterceptor;
import com.example.newestandroidframe.network.config.ServerConfig;
import com.example.newestandroidframe.network.config.ServerConfigImpl;
import com.example.newestandroidframe.network.converter.DecorationConverterFactory;
import com.example.newestandroidframe.network.converter.ResponseDecoratorList;
import com.example.newestandroidframe.util.NetUtil;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by zhangyb on 2017/4/28.
 */

public class HttpRequestManager {

    private static HttpRequestManager httpRequestManager;

    private ServerConfig serverConfig;

    //TODO appserver
    private OkHttpClient appServerHttpClient;
    private AppServerInterceptor appServerInterceptor;
    private ResponseDecoratorList appServerRespDecorList;
    private HttpLoggingInterceptor loggingInterceptor;
    private TokenVerificationInterceptor tokenVerificationInterceptor;

    public static HttpRequestManager getInstance(Context context) {
        if (httpRequestManager == null) {
            synchronized (HttpRequestManager.class) {
                if (httpRequestManager == null) {
                    httpRequestManager = new HttpRequestManager(context);
                }
            }
        }
        return httpRequestManager;
    }

    public HttpRequestManager(Context context) {
        serverConfig = new ServerConfigImpl(context);
        loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        tokenVerificationInterceptor = new TokenVerificationInterceptor(serverConfig);
        appServerInterceptor = new AppServerInterceptor(serverConfig);

        appServerHttpClient = new OkHttpClient.Builder()
            .cache(getCache())
            .connectTimeout(6, TimeUnit.SECONDS)
            .readTimeout(6, TimeUnit.SECONDS)
            .writeTimeout(6, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .addInterceptor(tokenVerificationInterceptor)
            .addInterceptor(appServerInterceptor)
            .sslSocketFactory(NetUtil.getUnsafeSslSocketFactory(), NetUtil.getUnsafeX509TrustManager())
            .hostnameVerifier(NetUtil.getUnsafeHostnameVerifier()).build();
        appServerRespDecorList = new ResponseDecoratorList();
    }

    public <T> T createAppServerApi(Class<T> tClass, String baseUrl) {
        return createAppServerApi(tClass, baseUrl, GsonConverterFactory.create());
    }

    public <T> T createAppServerApi(Class<T> tClass, String baseUrl, Converter.Factory factory) {
        return new Retrofit.Builder()
            .client(getAppServerHttpClient())
            .baseUrl(baseUrl)
            .addConverterFactory(new DecorationConverterFactory(factory, appServerRespDecorList.list()))
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .build()
            .create(tClass);
    }

    private OkHttpClient getAppServerHttpClient() {
        return appServerHttpClient;
    }

    private Cache getCache(){
       return new Cache(new File(FrameApp.getInstance().getCacheDir(), "HttpCache"), 1024 * 1024 * 100);
    }
}
