package com.base;

import com.comment.Logger;

import android.app.Application;
import android.content.Context;

public class MyApplication extends Application{
	private static MyApplication instance=null;
	

    public static MyApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        Logger.info("MyApplication创建成功.....");
    }
    
    
}
