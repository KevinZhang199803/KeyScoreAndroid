package com.siokagami.keyscoreandroid.base;

import android.app.Application;

public class ApplicationHolder {
    public static Application instance;

    public static Application getInstance()
    {
        if(instance==null)
        {
            instance = new Application();
        }
        return instance;
    }
}