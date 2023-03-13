package com.astix.allanasosfa.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceManager {

    private static final String PREF_NAME = "com.astix.asmapp.sharedpref";

    private static PreferenceManager sInstance;
    private final SharedPreferences mPref;

    private PreferenceManager(Context context) {
        mPref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static synchronized void initializeInstance(Context context) {
        if (sInstance == null) {
            sInstance = new PreferenceManager(context);
        }
    }

    public static synchronized PreferenceManager getInstance(Context context) {
        if (sInstance == null) {
            initializeInstance(context);
        }
        return sInstance;
    }

    public void setValue(String key, long value) {
        mPref.edit()
                .putLong(key, value)
                .commit();
    }

    public void setLongValue(String key,long defValue) {
        mPref.edit()
                .putLong(key, defValue)
                .commit();
    }

    public long getLongValue(String key,long defValue) {
        return mPref.getLong(key, defValue);
    }


    public void setValue(String key, String value) {
        mPref.edit()
                .putString(key, value)
                .commit();
    }


    public String getStringValue(String key,String defValue) {
        return mPref.getString(key, defValue);
    }

    public void setValue(String key, int value) {
        mPref.edit()
                .putInt(key, value)
                .commit();
    }

    public int getIntValue(String key, int defValue) {
        return mPref.getInt(key, defValue);
    }


    public void setValue(String key, boolean value) {
        mPref.edit()
                .putBoolean(key, value)
                .commit();
    }

    public boolean getBooleanValue(String key, boolean defValue) {
        return mPref.getBoolean(key, defValue);
    }

    public boolean contains(String key){
        return mPref.contains(key);
    }



    public void remove(String key) {
        mPref.edit()
                .remove(key)
                .commit();
    }

    public boolean clear() {
        return mPref.edit()
                .clear()
                .commit();
    }
}
