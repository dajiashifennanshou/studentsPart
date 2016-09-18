package com.xdk.df.parentend.application;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/5.
 */
public class XdkApplication extends Application {
    private List<Activity> activityList = new LinkedList();
    private static XdkApplication instance;
    private static Context context;

    @Override
    public void onCreate() {
        context = getApplicationContext();
        super.onCreate();
    }

    public static XdkApplication getInstance() {
        if (null == instance) {
            instance = new XdkApplication();
        }
        return instance;
    }

    //添加Activity到容器中
    public void addActivity(Activity activity) {
        activityList.add(activity);
    }

    //遍历所有Activity并finish
    public void exit() {
        for (Activity activity : activityList) {
            activity.finish();
        }
        System.exit(0);
    }

    public static Context getContext() {
        return context;
    }
}
