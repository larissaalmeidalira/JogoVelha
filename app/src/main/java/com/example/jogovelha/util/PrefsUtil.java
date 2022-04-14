package com.example.jogovelha.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PrefsUtil {

    public static void salvarSimboloJog1(String simbolo, Context context){
        SharedPreferences preferencias = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferencias.edit();
        editor.putString("simb_jog_1", simbolo);
        editor.commit();
    }

    public static String getSimboloJog1(Context context){
        SharedPreferences preferencias = PreferenceManager.getDefaultSharedPreferences(context);
        return preferencias.getString("simb_jog_1", "X");
    }

    public static String getSimboloJog2(Context context){
        SharedPreferences preferencias = PreferenceManager.getDefaultSharedPreferences(context);
        return preferencias.getString("simb_jog_2", "O");
    }

}
