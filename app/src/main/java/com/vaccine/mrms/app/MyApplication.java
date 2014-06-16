package com.vaccine.mrms.app;

import android.app.Application;

/**
 * Created by amittal on 5/30/14.
 */
public class MyApplication extends Application {

    private String mSessionToken="";

    public String getmSessionToken(){
        return mSessionToken;
    }

    public void setmSessionToken(String value){
        mSessionToken = value;
    }





}
