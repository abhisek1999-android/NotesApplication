package com.abhi.seal.dt16062022.noteapplication.SP;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class PrefConfig {

    public static void writeIdInPref(Context context, String emailId,String LIST_KEY){

        SharedPreferences pref= PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor= pref.edit();
        editor.putString(LIST_KEY,emailId);
        editor.apply();
    }

    public static String readIdInPref(Context context,String LIST_KEY){

        SharedPreferences pref=PreferenceManager.getDefaultSharedPreferences(context);
         String  values = pref.getString(LIST_KEY,"");
        return values;
    }

    public static void writeListInPref(Context context, List<Uri> list, String LIST_KEY){

        Gson gson=new Gson();
        String jsonString= gson.toJson(list);

        SharedPreferences pref= PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor= pref.edit();
        editor.putString(LIST_KEY,jsonString);
        editor.apply();
    }

    public static ArrayList<Uri> readListFromPref(Context context,String LIST_KEY){

        SharedPreferences pref=PreferenceManager.getDefaultSharedPreferences(context);
        String jsonString = pref.getString(LIST_KEY,"");
        Gson gson=new Gson();
        Type type=new TypeToken<ArrayList<Uri>>(){}.getType();
        ArrayList<Uri> list=gson.fromJson(jsonString,type);
        return list;
    }
}
