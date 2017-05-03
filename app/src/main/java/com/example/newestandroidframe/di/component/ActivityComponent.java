package com.example.newestandroidframe.di.component;

import android.app.Activity;
import android.content.Context;

import com.example.newestandroidframe.di.module.ActivityModule;
import com.example.newestandroidframe.di.scope.ContextLife;
import com.example.newestandroidframe.di.scope.PreActivity;
import com.example.newestandroidframe.mvp.ui.activity.NewsActivity;
import com.example.newestandroidframe.mvp.ui.activity.NewsChannelActivity;
import com.example.newestandroidframe.mvp.ui.activity.NewsDetailActivity;
import com.example.newestandroidframe.mvp.ui.activity.NewsPhotoDetailActivity;

import dagger.Component;

/**
 * Created by zhangyb on 2017/5/3.
 */

@PreActivity
@Component(dependencies = AppComponent.class ,modules = ActivityModule.class)
public interface ActivityComponent {
    @ContextLife("Activity")
    Context getActivityContext();

    @ContextLife("Application")
    Context getApplicationContext();

    Activity getActivity();

    void inject(NewsActivity newsActivity);

    void inject(NewsDetailActivity newsDetailActivity);

    void inject(NewsChannelActivity newsChannelActivity);

    void inject(NewsPhotoDetailActivity newsPhotoDetailActivity);
}
