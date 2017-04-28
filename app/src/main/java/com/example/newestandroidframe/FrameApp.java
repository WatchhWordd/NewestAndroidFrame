package com.example.newestandroidframe;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatDelegate;

import com.example.newestandroidframe.greendao.DaoMaster;
import com.example.newestandroidframe.greendao.DaoSession;
import com.example.newestandroidframe.util.Constants;
import com.example.newestandroidframe.util.PreferenceUtil;
import com.socks.library.KLog;
import com.squareup.leakcanary.BuildConfig;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import org.greenrobot.greendao.query.QueryBuilder;

/**
 * Created by zhangyb on 2017/4/28.
 */

public class FrameApp extends Application {

    private static FrameApp frameApp;
    private RefWatcher refWatcher;
    private static DaoSession daoSession;

    public static FrameApp getInstance() {
        if (frameApp == null) {
            frameApp = new FrameApp();
        }
        return frameApp;
    }

    private FrameApp() {
    }

    public RefWatcher getRefWatcher(Context context) {
        FrameApp application = (FrameApp) context.getApplicationContext();
        return application.refWatcher;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        initLeakcanary();
        initActivityLifeCycleListener();
        initStrictMode();
        initDayLightMode();
        KLog.init(BuildConfig.DEBUG);
        setUpDataBase();

        boolean isNight = PreferenceUtil.getInstance().getBoolean(Constants.ISNIGHT);
        if (isNight) {
            //使用夜间模式
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            //不使用夜间模式
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    private void initLeakcanary() {

        if (BuildConfig.DEBUG) {
            refWatcher = LeakCanary.install(this);
        } else {
            refWatcher = initLeakcanaryRelease();
        }
    }

    private RefWatcher initLeakcanaryRelease() {
        return RefWatcher.DISABLED;
    }

    /**
     * activity生命周期监听
     */
    private void initActivityLifeCycleListener() {

        this.registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle bundle) {

            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
    }

    private void initStrictMode() {
        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(
                new StrictMode.ThreadPolicy.Builder()
                    .detectAll()
//                            .penaltyDialog() // 弹出违规提示对话框
                    .penaltyLog() // 在logcat中打印违规异常信息
                    .build());
            StrictMode.setVmPolicy(
                new StrictMode.VmPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .build());
        }
    }

    private void initDayLightMode() {
        if (PreferenceUtil.getInstance().getBoolean(Constants.NIGHT_THEME_MODE)) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    private void setUpDataBase() {

        DaoMaster.DevOpenHelper sqLOpenHeler = new DaoMaster.DevOpenHelper(this, Constants.TABLE_NAME);
        SQLiteDatabase sqliteDatabase = sqLOpenHeler.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(sqliteDatabase);
        daoSession = daoMaster.newSession();
        // 在 QueryBuilder 类中内置两个 Flag 用于方便输出执行的 SQL 语句与传递参数的值
        QueryBuilder.LOG_SQL = BuildConfig.DEBUG;
        QueryBuilder.LOG_VALUES = BuildConfig.DEBUG;
    }
}
