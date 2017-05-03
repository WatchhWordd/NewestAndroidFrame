package com.example.newestandroidframe.di.scope;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by zhangyb on 2017/5/3.
 */

@Scope
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface PreApp {
}
