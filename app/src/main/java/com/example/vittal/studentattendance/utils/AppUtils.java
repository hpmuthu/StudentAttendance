package com.example.vittal.studentattendance.utils;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.Base64;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.vittal.studentattendance.activity.LoginActivity;
import com.example.vittal.studentattendance.helper.PreferencesManager;

import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class AppUtils {

    static PreferencesManager myPref;

    // Shared Preferences reference
    static SharedPreferences pref;
    // Editor reference for Shared preferences
    private static SharedPreferences.Editor editor;
    // Shared pref mode
    private static int PRIVATE_MODE = 0;

    public static String TAG_PERMISSION_CAMERA = "CAMERA";
    public static String TAG_PERMISSION_STORAGE = "STORAGE";
    public static String TAG_PERMISSION_LOCATION = "LOCATION";
    public static String TAG_PERMISSION_CONTACTS = "CONTACTS";
    public static String TAG_PERMISSION_CALL = "CALL";


    /*
    * this method for transparent toolbar(status bar)
     */
    public static void transparentToolbar(Activity context) {
        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
            setWindowFlag(context, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true);
        }
        if (Build.VERSION.SDK_INT >= 19) {
            context.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(context, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
            context.getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }

    private static void setWindowFlag(Activity activity, final int bits, boolean on) {
        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }


    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connec = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connec != null) {
            NetworkInfo result = connec.getActiveNetworkInfo();
            if (result != null && result.isConnectedOrConnecting())
                return true;
            else
                return false;
        }
        return false;
    }

    public static boolean isEmailValid(String email) {
        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

    public static void getVersionNo(Context context, TextView textView) {
        try {
            PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            String version = pInfo.versionName;
            textView.setText("Version " + version);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static String base64EncodeImage(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        String encImage = Base64.encodeToString(b, Base64.DEFAULT);

        return encImage;
    }

    // Create login session
    public static void createLoginSession(Context context) {
        myPref = new PreferencesManager(context);

        //save login user id
        myPref.setBooleanValue("Login_Enabled", true);
    }

    /*
      Check login method will check user login status
      If false it will redirect user to login page
    */
    public static boolean checkLogin(Context context) {

        myPref = new PreferencesManager(context);

        boolean isLoggedIn = myPref.getBooleanValue("Login_Enabled");
        // Check login status
        if (!isLoggedIn) {

            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(context, LoginActivity.class);

            // Closing all the Activities from stack
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            context.startActivity(i);

            return true;
        }
        return false;
    }

    //logout
    public static void logoutUser(Context context) {
        myPref = new PreferencesManager(context);

        myPref.setBooleanValue("Login_Enabled", false);

        Intent i = new Intent(context, LoginActivity.class);
        // Closing all the Activities
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(i);
    }

    public static boolean isEmpty(Object data) {
        if (data == null)
            return true;
        return false;
    }

    public static long getDifferenceDays(Date d1, Date d2) {
        long diff = d2.getTime() - d1.getTime();
        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }

    public static String capitalizeFirstLetter(String original) {
        if (original == null || original.length() == 0) {
            return original;
        }
        return original.substring(0, 1).toUpperCase() + original.substring(1);
    }

    public static void enableDisableUserInteraction(Activity context, boolean mode) {
        if (mode) {
            context.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        } else {
            context.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }
    }



}
