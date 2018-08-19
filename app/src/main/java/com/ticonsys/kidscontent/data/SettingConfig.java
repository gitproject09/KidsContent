package com.ticonsys.kidscontent.data;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by user on 2016-09-20.
 */
public class SettingConfig {
    public static final String PRE_NAME = "com.pororo_preferences";
    static final String PORTSIP_REGNAME = "PORTSIP_REGNAME";
    static final String PORTSIP_USERNAME = "PORTSIP_USERNAME";
    static final String PORTSIP_USERPASSWORD = "PORTSIP_USEPWD";
    static final String PORTSIP_SIPSERVER = "PORTSIP_SIPSERVER";
    static final String PORTSIP_SIPSERVERPORT = "PORTSIP_SIPPORT";
    static final String PORTSIP_USERDOMAIN = "PORTSIP_USERDOMAIN";
    static final String PORTSIP_AUTHNAME = "PORTSIP_AUTHNAME";
    static final String PORTSIP_USEDISPALYNAME= "PORTSIP_DISNAME";

    static final String PORTSIP_STUNSVR= "PORTSIP_STUNSVR";
    static final String PORTSIP_STUNPORT= "PORTSIP_STUNPORT";
    static final String PORTSIP_TRANSTYPE= "PORTSIP_TRANSTYPE";
    static final String PORTSIP_SRTPTYPE= "PORTSIP_SRTPTYPE";

    static final String PORTSIP_BACKGROUND_SERVICE = "PORTSIP_BACKGROUND_SERVICE";

    static final String BACKGROUND_CALL = "BACKGROUND_CALL";

    static final String TALKING_MODE = "TALKING_MODE";

    static final String MAIN_ACT_FINISH_STATUS = "MAIN_ACT_FINISH_STATUS";

    static final String PORTSIP_DYNAMIC_SERVER_IP = "PORTSIP_DYNAMIC_SERVER_IP";

    static final String USER_REG_NUMBER = "USER_REG_NUMBER";
    static final String USER_STATUS = "USER_STATUS";


    public static String getPortsipRegname(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PRE_NAME, Context.MODE_PRIVATE);

        return preferences.getString(PORTSIP_REGNAME, "");
    }

    public static String getPortsipUserpassword(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PRE_NAME, Context.MODE_PRIVATE);

        return preferences.getString(PORTSIP_USERPASSWORD, "");
    }



    public static void setKeepAlive(Context context, String aliveTag) {
        SharedPreferences preferences = context.getSharedPreferences(PRE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PORTSIP_BACKGROUND_SERVICE, aliveTag);
        editor.commit();

    }

    public static String getKeepAlive(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PRE_NAME, Context.MODE_PRIVATE);
        return preferences.getString(PORTSIP_BACKGROUND_SERVICE, "");
    }

    public static void setServerIp(Context context, String serverIp) {
        SharedPreferences preferences = context.getSharedPreferences(PRE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PORTSIP_SIPSERVER, serverIp);
        editor.commit();
    }

    public static String getServerIp(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PRE_NAME, Context.MODE_PRIVATE);
        return preferences.getString(PORTSIP_SIPSERVER, ""); // 58.225.75.151
    }

    public static void setTransType(Context context, int enum_transType) {
        SharedPreferences mPrefrence = context.getSharedPreferences(PRE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mPrefrence.edit();
        editor.putInt(PORTSIP_TRANSTYPE, enum_transType);
        editor.commit();

    }



    public static String getBackgroundCallAnswer(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PRE_NAME, Context.MODE_PRIVATE);
        return preferences.getString(BACKGROUND_CALL, "");
    }


    public static void setTalkingMode(Context context, String isTalking) {
        SharedPreferences preferences = context.getSharedPreferences(PRE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(TALKING_MODE, isTalking);
        editor.commit();

    }

    public static String getTalkingMode(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PRE_NAME, Context.MODE_PRIVATE);
        return preferences.getString(TALKING_MODE, "");
    }


    public static void setMainActivityFinishStatus(Context context, String isFinish) {
        SharedPreferences preferences = context.getSharedPreferences(PRE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(MAIN_ACT_FINISH_STATUS, isFinish);
        editor.commit();

    }

    public static String getMainActivityFinishStatus(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PRE_NAME, Context.MODE_PRIVATE);
        return preferences.getString(MAIN_ACT_FINISH_STATUS, "");
    }

    public static String getUserNumber(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PRE_NAME, Context.MODE_PRIVATE);
        return preferences.getString(USER_REG_NUMBER, "");
    }

    public static String getUserStatus(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PRE_NAME, Context.MODE_PRIVATE);
        return preferences.getString(USER_STATUS, "");
    }

}