package com.example.newestandroidframe.di.component;

import android.content.Context;

import com.example.newestandroidframe.di.module.AppModule;
import com.example.newestandroidframe.di.scope.ContextLife;
import com.example.newestandroidframe.di.scope.PreApp;

import dagger.Component;

/**
 * Created by zhangyb on 2017/5/3.
 */

@PreApp
@Component(modules = AppModule.class)
public interface AppComponent {

    @ContextLife("Application")
    Context getApplication();
}
