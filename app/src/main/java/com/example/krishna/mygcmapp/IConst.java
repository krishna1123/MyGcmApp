package com.example.krishna.mygcmapp;

import android.content.Context;
import android.content.SharedPreferences;

public class IConst {

//    public static final String SENDER_ID = "457179918893";
//    public static final String URL_REGISTRATION = "http://192.168.50.118/phpws/projects/gcm/register.php";
//    public static final String URL_SEND_MESSAGE = "http://192.168.50.118/phpws/projects/gcm/send_message.php";

    public static final String SENDER_ID = "305471239422";
    public static final String URL_REGISTRATION = "http://192.168.50.119/workspace/MyGcmApp/register.php";//workspace/MyGcmApp/register.php
    public static final String URL_SEND_MESSAGE = "http://192.168.50.119/workspace/MyGcmApp/send_message.php";
    public static final String URL_SEND_NOTIFICATION = "http://192.168.50.119/workspace/MyGcmApp/send_notification.php";

    public static final String PARAM_NAME = "name";
    public static final String PARAM_EMAIL = "email";
    public static final String PARAM_PHONE_NUMBER = "phno";
    public static final String PARAM_REGISTRATION_ID = "regId";
    public static final String PARAM_MESSAGE="message";

    public static void setRegistrationId(Context context, String regId){
        SharedPreferences preferences = context.getSharedPreferences("GCM", Context.MODE_PRIVATE);
        preferences.edit().putString("regId", regId.trim()).commit();
    }

    public static String getRegistrationId(Context context){
        SharedPreferences preferences = context.getSharedPreferences("GCM", Context.MODE_PRIVATE);
        return preferences.getString("regId", "");
    }

    public static void registered(Context context, boolean flag){
        SharedPreferences preferences = context.getSharedPreferences("GCM", Context.MODE_PRIVATE);
        preferences.edit().putBoolean("isReg", flag).commit();
    }

    public static boolean isRegistered(Context context){
        SharedPreferences preferences = context.getSharedPreferences("GCM", Context.MODE_PRIVATE);
        return preferences.getBoolean("isReg", false);
    }
}
