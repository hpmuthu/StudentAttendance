package com.example.vittal.studentattendance.base;


import android.graphics.Point;
import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;

import com.example.vittal.studentattendance.helper.PreferencesManager;

public class BaseActivity extends AppCompatActivity {
    PreferencesManager myPref;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myPref = new PreferencesManager(BaseActivity.this);
        calculateScreenSize();
    }

    private void calculateScreenSize() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = getDisplaySize(display);
        int width = size.x;
        int height = size.y;
        myPref.setIntValue("ScreenWidth", width);//width 9.3
        myPref.setIntValue("ScreenHeight", height);//height 15.5

    }

    private Point getDisplaySize(final Display display) {
        final Point point = new Point();
        try {
            display.getSize(point);
        } catch (NoSuchMethodError ignore) {
            point.x = display.getWidth();
            point.y = display.getHeight();
        }
        return point;
    }

}
