package com.example.newestandroidframe.di.module;

import android.content.Context;

import com.example.newestandroidframe.FrameApp;
import com.example.newestandroidframe.di.scope.ContextLife;
import com.example.newestandroidframe.di.scope.PreApp;

import dagger.Module;
import dagger.Provides;

/**
 * Created by zhangyb on 2017/5/3.
 */
@Module
public class AppModule {

    private FrameApp frameApp;

    public AppModule(FrameApp frameApp) {
        this.frameApp = frameApp;
    }

    @Provides
    @PreApp
    @ContextLife("Application")
    public Context providesAppContext() {
        return frameApp;
    }
}
