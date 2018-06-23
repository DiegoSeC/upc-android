package com.barberia.upc.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Session {

    private SharedPreferences sp;

    public Session(Context ctx) {
        sp = PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public void setToken(String token) {
        sp.edit().putString("token", token).commit();
    }

    public String getToken() {
        String token = sp.getString("token", "");
        return token;
    }
    
}
