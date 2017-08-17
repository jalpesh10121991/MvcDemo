package in.mvcdemo.Utills;

import android.os.Environment;

public class Constant {

    public static final String APP_FOLDER = Environment.getExternalStorageDirectory().getAbsolutePath() + "/theTeamMate/";
    public static final String APP_LOGS_FOLDER = APP_FOLDER + "LOGS/";
    public static final int CONNECTION_TIMEOUT = 1000000;
    public static final String BASE_URL = "https://nametheteammate.com/admin/webservices/index.php/user";
    public static final String PREFERENCE_NAME = "in.teammate";
    public static final String IMG_URL = "https://nametheteammate.com/admin/webservices/";
    public static final String PENDING_IMG_URL = "https://nametheteammate.com/admin/webservices/";
	

    //Preferance Strings
    public static final String IS_REGISTERED = PREFERENCE_NAME + "is_register";
    public static final String IS_OTP = PREFERENCE_NAME + "is_otp";
    public static final String IS_LOGIN = PREFERENCE_NAME + "is_login";
    public static final String USER_NAME = PREFERENCE_NAME + "user_name";
    public static final String USER_EMAIL = PREFERENCE_NAME + "user_email";
    public static final String ACCESS_TOKEN = PREFERENCE_NAME + "user_token";
    public static final String USER_ID = PREFERENCE_NAME + "user_id";
    public static final String USER_INVITE = PREFERENCE_NAME + "user_invite";
    public static final String USER_PHOTO = PREFERENCE_NAME + "user_photo";
    public static final String DEVICE_ID = PREFERENCE_NAME + "device_id";
    public static final String MAP_ONOFF = PREFERENCE_NAME + "map_on_off";
    public static final String USER_SCAN_ID = PREFERENCE_NAME + "user_scan_id";
    public static final String LATITUDE = PREFERENCE_NAME + "latitude";
    public static final String LONGITUDE = PREFERENCE_NAME + "logitude";
    public static final String PASSWORD = PREFERENCE_NAME + "password";
    public static final String USER_ADDRESS = PREFERENCE_NAME + "address";
    public static final String USER_CONTACT = PREFERENCE_NAME + "contact";
    public static final String USER_WUrl = PREFERENCE_NAME + "w_url";
    public static final String USER_ACCESS_TOKEN = PREFERENCE_NAME + "access_token";
    public static final String WEDDING_KEY = PREFERENCE_NAME + "wedding_key";
    public static final String SELECTED_PHOTO = PREFERENCE_NAME + "selected_photo";
    public static final String BORG = PREFERENCE_NAME + "borg";
    public static final String SNAME = PREFERENCE_NAME + "sname";
    public static final String BNAME = PREFERENCE_NAME + "bname";
    public static final String RETYPE = PREFERENCE_NAME + "retype";
    public static final String EVENT_NAME = PREFERENCE_NAME + "event_type";
    public static final String TEAM_NAME = PREFERENCE_NAME + "team_type";
    public static final String EVENT_ID =PREFERENCE_NAME + "event_id";
    public static final String USER_TEAM =PREFERENCE_NAME + "user_team";
    //Location Services
    public static final int SUCCESS_RESULT = 0;
    public static final int FAILURE_RESULT = 1;
    public static final String PACKAGE_NAME =
            "com.google.android.gms.location.sample.locationaddress";
    public static final String RECEIVER = PACKAGE_NAME + ".RECEIVER";
    public static final String RESULT_DATA_KEY = PACKAGE_NAME + ".RESULT_DATA_KEY";
    public static final String LOCATION_DATA_EXTRA = PACKAGE_NAME + ".LOCATION_DATA_EXTRA";
    public static String WEB_SERVICE_END_POINT = "https://nametheteammate.com/admin/webservices/index.php/user";
}
