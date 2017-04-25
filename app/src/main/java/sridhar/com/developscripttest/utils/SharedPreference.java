package sridhar.com.developscripttest.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by SriMaddy on 4/26/2017.
 */

public class SharedPreference {

    private static final String PREF_NAME = "pref";
    private static final String KEY_STEP_ONE = "step1";
    private static final String KEY_STEP_TWO = "step2";
    private static final String KEY_STEP_THREE = "step3";
    private static final String KEY_STEP_FOUR = "step3";

    public void saveStepOneAnimated(Context context, boolean isAnimated) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putBoolean(KEY_STEP_ONE, isAnimated);
        editor.commit();
    }

    public boolean isStepOneAnimated(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return preferences.getBoolean(KEY_STEP_ONE, false);
    }

    public void saveStepTwoAnimated(Context context, boolean isAnimated) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putBoolean(KEY_STEP_TWO, isAnimated);
        editor.commit();
    }

    public boolean isStepTwoAnimated(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return preferences.getBoolean(KEY_STEP_TWO, false);
    }

    public void saveStepThreeAnimated(Context context, boolean isAnimated) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putBoolean(KEY_STEP_THREE, isAnimated);
        editor.commit();
    }

    public boolean isStepThreeAnimated(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return preferences.getBoolean(KEY_STEP_THREE, false);
    }

    public void saveStepFourAnimated(Context context, boolean isAnimated) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putBoolean(KEY_STEP_FOUR, isAnimated);
        editor.commit();
    }

    public boolean isStepFourAnimated(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return preferences.getBoolean(KEY_STEP_FOUR, false);
    }

    public void clearAll(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
    }
}
