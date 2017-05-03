package com.example.newestandroidframe.di.component;

import android.app.Activity;
import android.content.Context;

import com.example.newestandroidframe.di.module.FragmentModule;
import com.example.newestandroidframe.di.scope.ContextLife;
import com.example.newestandroidframe.di.scope.PreFragment;
import com.example.newestandroidframe.mvp.ui.fragment.NewsListFragment;

import dagger.Component;

/**
 * Created by zhangyb on 2017/5/3.
 */

@PreFragment
@Component(dependencies = AppComponent.class,modules = FragmentModule.class)
public interface FragmentComponent {

    @ContextLife("Application")
    Context getApplicationContext();

    @ContextLife("Activity")
    Context getActivityContext();

    Activity getActivity();

    void inject(NewsListFragment newsListFragment);
}
