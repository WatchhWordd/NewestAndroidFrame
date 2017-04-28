package com.example.newestandroidframe.network.manager;

import android.content.Context;

import com.example.newestandroidframe.network.HttpRequestManager;
import com.example.newestandroidframe.network.api.NewServerApi;

/**
 * Created by zhangyb on 2017/4/28.
 */

public class NewsManager {

    private Context context;

    private NewServerApi newServerApi;

    public NewsManager(Context context) {
        this.context = context;
        newServerApi = HttpRequestManager.getInstance(context).createAppServerApi(NewServerApi.class,"");
    }
}
