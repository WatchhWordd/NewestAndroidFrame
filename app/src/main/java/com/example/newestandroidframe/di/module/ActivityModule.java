package com.example.newestandroidframe.di.module;

import android.app.Activity;
import android.content.Context;

import com.example.newestandroidframe.di.scope.ContextLife;
import com.example.newestandroidframe.di.scope.PreActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by zhangyb on 2017/5/3.
 */

@Module
public class ActivityModule {

    private Activity activity;

    public ActivityModule(Activity activity) {
        this.activity = activity;
    }


    @PreActivity
    @Provides
    @ContextLife("Activity")
    public Context providerActivityContext() {
        return activity;
    }

    @PreActivity
    @Provides
    public Activity providerActivity() {
        return activity;
    }

}
