package com.azhan.news.Data;

import android.content.Context;
import android.content.SharedPreferences;

public class NewsPreferences {

    public static final String PREF_NAME = "news";
    public static final String PREF_COUNTRY = "country";
    public static final String PREF_CATEGORY = "category";

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public NewsPreferences() {
        super();
    }

    public void saveCountry(Context context, String country){
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        editor.putString(PREF_COUNTRY, country);
        editor.commit();
    }

    public String getCountry(Context context){
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String country = null;
        if(sharedPreferences.contains(PREF_COUNTRY))
            country = sharedPreferences.getString(PREF_COUNTRY, null);
        return country;
    }

    public void saveCategory(Context context, String category){
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        editor.putString(PREF_CATEGORY, category);
        editor.commit();
    }

    public String getCategory(Context context){
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String category = null;
        if(sharedPreferences.contains(PREF_CATEGORY))
            category = sharedPreferences.getString(PREF_CATEGORY, null);
        return category;
    }
}