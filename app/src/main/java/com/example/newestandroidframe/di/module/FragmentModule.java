package com.example.newestandroidframe.di.module;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;

import com.example.newestandroidframe.di.scope.ContextLife;
import com.example.newestandroidframe.di.scope.PreFragment;

import dagger.Module;
import dagger.Provides;

/**
 * Created by zhangyb on 2017/5/3.
 */

@Module
public class FragmentModule {
    private Fragment fragment;

    public FragmentModule(Fragment fragment) {
        this.fragment = fragment;
    }

    @Provides
    @PreFragment
    @ContextLife("Activity")
    public Context providerActivityContext(){
        return fragment.getActivity();
    }

    @Provides
    @PreFragment
    public Activity providerActivity(){
        return fragment.getActivity();
    }

    @Provides
    @PreFragment
    public Fragment providerFragment(){
        return fragment;
    }
}
