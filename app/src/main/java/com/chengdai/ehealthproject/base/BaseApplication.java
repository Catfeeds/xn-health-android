package com.chengdai.ehealthproject.base;

import android.app.Application;


/**
 * 基础Application
 * Created by Administrator on 2016/8/29.
 */
public class BaseApplication extends Application {

    private static BaseApplication application;

    public static BaseApplication getInstance(){
        return application;
    }


}
