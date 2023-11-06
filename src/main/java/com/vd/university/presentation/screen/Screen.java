package com.vd.university.presentation.screen;

public abstract class Screen {

    public abstract void onCreate();
    public abstract void onDestroy();

    public String getCorrectDisplayString(String value, int maxValue) {
        String result = value;
        if (value.length() > maxValue) result = value.substring(0, maxValue - 3) + "...";

        return result;
    }
}