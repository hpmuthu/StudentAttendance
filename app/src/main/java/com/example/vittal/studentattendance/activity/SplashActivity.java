package com.example.vittal.studentattendance.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.vittal.studentattendance.R;
import com.example.vittal.studentattendance.base.BaseActivity;
import com.example.vittal.studentattendance.helper.PreferencesManager;
import com.example.vittal.studentattendance.utils.AppUtils;


public class SplashActivity extends BaseActivity {

    PreferencesManager myPref;
    int width, height;
    //screen height 15.5cm, width 9.3cm

    private long Splash_time = 3000;

    private ImageView logoImageView;
    private TextView logoTextView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        myPref = new PreferencesManager(this);
        width = myPref.getIntValue("ScreenWidth");
        height = myPref.getIntValue("ScreenHeight");


        initializeViews();
        setDynamicViews();
        defaultFunctionality();

    }

    private void initializeViews() {
        logoImageView = (ImageView)findViewById( R.id.logoImageView );
        logoTextView = (TextView)findViewById( R.id.logoTextView );
    }

    private void setDynamicViews() {
        int logoImageWidth = (int) (width * 0.32258);//3.0
        int logoImageMarginBottom = (int) (height * 0.05806);//0.9

        LinearLayout.LayoutParams logoImage = (LinearLayout.LayoutParams) logoImageView.getLayoutParams();
        logoImage.width = logoImageWidth;
        logoImage.height = logoImageWidth;
        logoImage.setMargins(0,0,0,logoImageMarginBottom);
        logoImageView.setLayoutParams(logoImage);
    }

    private void defaultFunctionality() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (AppUtils.checkLogin(SplashActivity.this)) {
                    //user not logged in
                    finish();
                } else {
                    //user logged in
                    navigateLoginSuccessScreen();

                }
            }
        }, Splash_time);

    }

    private void navigateLoginSuccessScreen() {
        if (myPref.getLongValue("userId") > 0) {
            Intent j = new Intent(SplashActivity.this, DashboardActivity.class);
            j.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(j);
        } else {
            Intent j = new Intent(SplashActivity.this, LoginActivity.class);
            j.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(j);
        }
        finish();
    }
}
