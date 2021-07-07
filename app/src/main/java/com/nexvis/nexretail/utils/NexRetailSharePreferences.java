package com.nexvis.nexretail.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class NexRetailSharePreferences {

    private static final String APP_SHARED_PREFS = "SunFixApp";
    private SharedPreferences sharedPrefs;
    private static NexRetailSharePreferences mAppShareConstant;
    private Context mContext = null;

    public enum SharedPreKeyType {
        TOKEN,
        ACCESS_TOKEN,
    }

    private NexRetailSharePreferences(Context context) {
        mContext = context;
        this.sharedPrefs = mContext.getSharedPreferences(APP_SHARED_PREFS, Activity.MODE_PRIVATE);
    }

    public synchronized static NexRetailSharePreferences getConstant(Context context) {
        if (null == mAppShareConstant) {
            mAppShareConstant = new NexRetailSharePreferences(context);
        }
        return mAppShareConstant;
    }

    private void clearSharePreferenceAll(){
        sharedPrefs.edit().clear().apply();
    }

    public void clearAll() {
        clearSharePreferenceAll();
    }


    public void setToken(String token) {
        sharedPrefs.edit().putString(SharedPreKeyType.TOKEN.toString(), token).apply();
    }

    public String getToken() {
        return sharedPrefs.getString(SharedPreKeyType.TOKEN.toString(), "");
    }

    public void setAccessToken(String accessToken) {
        sharedPrefs.edit().putString(SharedPreKeyType.ACCESS_TOKEN.toString(), accessToken).apply();
    }

    public String getAccessToken() {
        return sharedPrefs.getString(SharedPreKeyType.ACCESS_TOKEN.toString(), "");
    }

}
