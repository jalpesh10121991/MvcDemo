package in.mvcdemo.Utills;

import android.content.Context;
import android.content.SharedPreferences;

public class VPreferences {

    public static final String PREF_NAME = "PHOTO_NAME";
    public static final String PREF_USER_DETAIL = "PREF_USER_DETAIL";
    public static final String PREF_EMAIL = "PREF_EMAIL";
    public static final String rootUrlForImage = "http://backslashinfotech.in/photopass/webservices/";
    public static SharedPreferences preferences;
    public Context context;

    public VPreferences(Context context) {
        this.context = context;
        preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static boolean putStringInPreferences(final Context context,
                                                 final String value, final String key) {
        final SharedPreferences sharedPreferences = context
                .getSharedPreferences(Constant.PREFERENCE_NAME,
                        Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
        return true;
    }

    public static String getStringFromPreferences(final Context context,
                                                  final String defaultValue, final String key) {
        final SharedPreferences sharedPreferences = context
                .getSharedPreferences(Constant.PREFERENCE_NAME,
                        Context.MODE_PRIVATE);
        final String temp = sharedPreferences.getString(key, defaultValue);
        return temp;
    }

    public static boolean putBooleanInPreferences(final Context context,
                                                  final boolean value, final String key) {
        final SharedPreferences sharedPreferences = context
                .getSharedPreferences(Constant.PREFERENCE_NAME,
                        Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
        return true;
    }
    public static boolean getBooleanFromPreferences(final Context context,
                                                    final boolean defaultValue, final String key) {
        final SharedPreferences sharedPreferences = context
                .getSharedPreferences(Constant.PREFERENCE_NAME,
                        Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(key, defaultValue);
    }

    public static boolean isLogin(Context context) {
        return VPreferences.getBooleanFromPreferences(context, false,
                Constant.IS_LOGIN);
    }

    public static void setIsLogin(Context context, boolean isRegistered) {
        VPreferences.putBooleanInPreferences(context, isRegistered,
                Constant.IS_LOGIN);
    }

    public static String getPreferanceUserName(Context context) {
        return VPreferences.getStringFromPreferences(context, "",
                Constant.USER_NAME);
    }

    public static String setPreferanceUserName(Context context, String username) {
        VPreferences.putStringInPreferences(context, username,
                Constant.USER_NAME);
        return username;
    }

    public static String getPreferanceSpouse(Context context) {
        return VPreferences.getStringFromPreferences(context, "",
                Constant.SNAME);
    }

    public static String setPreferanceSpouse(Context context, String username) {
        VPreferences.putStringInPreferences(context, username,
                Constant.SNAME);
        return username;
    }

    public static String getPreferanceLongitude(Context context) {
        return VPreferences.getStringFromPreferences(context, "",
                Constant.LONGITUDE);
    }

    public static String setPreferanceLongitude(Context context, String username) {
        VPreferences.putStringInPreferences(context, username,
                Constant.LONGITUDE);
        return username;
    }

    public static String getPreferanceLattitude(Context context) {
        return VPreferences.getStringFromPreferences(context, "",
                Constant.LATITUDE);
    }

    public static String setPreferanceLattitude(Context context, String username) {
        VPreferences.putStringInPreferences(context, username,
                Constant.LATITUDE);
        return username;
    }

    public static String getPreferanceBothName(Context context) {
        return VPreferences.getStringFromPreferences(context, "",
                Constant.BNAME);
    }

    public static String setPreferanceBothName(Context context, String username) {
        VPreferences.putStringInPreferences(context, username,
                Constant.BNAME);
        return username;
    }

    public static String getPreferanceEmail(Context context) {
        return VPreferences.getStringFromPreferences(context, "",
                Constant.USER_EMAIL);
    }

    public static void setPreferanceEmail(Context context, String username) {
        VPreferences.putStringInPreferences(context, username,
                Constant.USER_EMAIL);
    }

    public static String getPreferanceEventName(Context context) {
        return VPreferences.getStringFromPreferences(context, "",
                Constant.EVENT_NAME);
    }

    public static void setPreferanceEventName(Context context, String username) {
        VPreferences.putStringInPreferences(context, username,
                Constant.EVENT_NAME);
    }

    public static String getPreferanceTeamName(Context context) {
        return VPreferences.getStringFromPreferences(context, "",
                Constant.TEAM_NAME);
    }

    public static void setPreferanceTeamName(Context context, String username) {
        VPreferences.putStringInPreferences(context, username,
                Constant.TEAM_NAME);
    }



    public static void reset() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
    }

    /**
     * @param context
     * @return
     */
    public static String getPreferanceDeviceID(Context context) {
        return VPreferences.getStringFromPreferences(context, "",
                Constant.DEVICE_ID);
    }

    /**
     * @param context
     * @param username
     */
    public static String setPreferanceBorG(Context context, String username) {
        VPreferences.putStringInPreferences(context, username,
                Constant.BORG);
        return username;
    }

    public static String getPreferanceBorG(Context context) {
        return VPreferences.getStringFromPreferences(context, "",
                Constant.BORG);
    }

    public static String setPreferanceRetype(Context context, String username) {
        VPreferences.putStringInPreferences(context, username,
                Constant.RETYPE);
        return username;
    }

    public static String getPreferanceRetype(Context context) {
        return VPreferences.getStringFromPreferences(context, "",
                Constant.RETYPE);
    }

    /**
     * @param context
     * @param username
     */
    public static String setPreferanceDeviceID(Context context, String username) {
        VPreferences.putStringInPreferences(context, username,
                Constant.DEVICE_ID);
        return username;
    }

    public static String getPreferanceSelectPhoto(Context context) {
        return VPreferences.getStringFromPreferences(context, "",
                Constant.SELECTED_PHOTO);
    }

    /**
     * @param context
     * @param username
     */
    public static String setPreferanceSelectPhoto(Context context, String username) {
        VPreferences.putStringInPreferences(context, username,
                Constant.SELECTED_PHOTO);
        return username;
    }

    public static void setPreferanceAccessToken(Context context, String username) {
        VPreferences.putStringInPreferences(context, username,
                Constant.USER_ACCESS_TOKEN);
    }

    public static String getPreferanceAccessToken(Context context) {
        return VPreferences.getStringFromPreferences(context, "",
                Constant.USER_ACCESS_TOKEN);
    }

    public static String getPreferanceAddress(Context context) {
        return VPreferences.getStringFromPreferences(context, "",
                Constant.USER_ADDRESS);
    }

    public static void setPreferanceAddress(Context context, String username) {
        VPreferences.putStringInPreferences(context, username,
                Constant.USER_ADDRESS);
    }

    public static String getPreferanceTeam(Context context) {
        return VPreferences.getStringFromPreferences(context, "",
                Constant.USER_TEAM);
    }

    public static void setPreferanceTeam(Context context, String username) {
        VPreferences.putStringInPreferences(context, username,
                Constant.USER_TEAM);
    }



    public static String getPreferanceEventID(Context context) {
        return VPreferences.getStringFromPreferences(context, "",
                Constant.EVENT_ID);
    }

    public static void setPreferanceEventID(Context context, String username) {
        VPreferences.putStringInPreferences(context, username,
                Constant.EVENT_ID);
    }



    public static String setPreferanceWeddingKey(Context context, String username) {
        VPreferences.putStringInPreferences(context, username,
                Constant.WEDDING_KEY);
        return username;
    }

    public static String getPreferanceWeddingKey(Context context) {
        return VPreferences.getStringFromPreferences(context, "",
                Constant.WEDDING_KEY);
    }

    public static String getPreferanceProfileImg(Context context) {
        return VPreferences.getStringFromPreferences(context, "",
                Constant.USER_PHOTO);
    }

    public static String setPreferanceProfileImg(Context context, String username) {
        VPreferences.putStringInPreferences(context, username,
                Constant.USER_PHOTO);
        return username;
    }

    public static void reset(Context context) {

        final SharedPreferences sharedPreferences = context
                .getSharedPreferences(Constant.PREFERENCE_NAME,
                        Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }


    public void set(String key, String str) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, str);
        editor.commit();
    }

    public void set(String key, int val) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(key, val);
        editor.commit();
    }

    public void set(String key, long val) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong(key, val);
        editor.commit();
    }

    public void set(String key, boolean val) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, val);
        editor.commit();
    }

    public String getStrPref(String key) {
        return preferences.getString(key, "");
    }

    public int getInt(String key) {
        return preferences.getInt(key, 0);
    }

    public boolean getBool(String key) {
        return preferences.getBoolean(key, false);
    }

    public long getLong(String key) {
        return preferences.getLong(key, 0);
    }

    public void clearUserData() {
        set(PREF_EMAIL, "");
    }

    public void saveCredential(String uEmail) {
        set(PREF_EMAIL, uEmail);
    }


}
