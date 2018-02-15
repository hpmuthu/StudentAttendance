package com.example.vittal.studentattendance.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.support.v4.app.ActivityCompat;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vittal.studentattendance.R;
import com.example.vittal.studentattendance.activity.DashboardActivity;
import com.example.vittal.studentattendance.database.LogModel;
import com.example.vittal.studentattendance.database.LogModel_Table;
import com.example.vittal.studentattendance.database.StudentModel;
import com.example.vittal.studentattendance.database.StudentModel_Table;
import com.example.vittal.studentattendance.helper.PreferencesManager;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.Date;


public class FingerprintHandler extends FingerprintManager.AuthenticationCallback {

    PreferencesManager myPref;
    private Context context;
    private Bundle mBundle;

    // Constructor
    public FingerprintHandler(Context mContext, Bundle bundle) {
        this.context = mContext;
        this.mBundle = bundle;

        myPref = new PreferencesManager(context);
    }

    public void startAuth(FingerprintManager manager, FingerprintManager.CryptoObject cryptoObject) {
        CancellationSignal cancellationSignal = new CancellationSignal();
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        manager.authenticate(cryptoObject, cancellationSignal, 0, this, null);
    }

    @Override
    public void onAuthenticationError(int errMsgId, CharSequence errString) {
        this.updateError("Fingerprint Authentication error\n" + errString);
    }

    @Override
    public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
        this.updateError("Fingerprint Authentication help\n" + helpString);
    }

    @Override
    public void onAuthenticationFailed() {
        this.updateError("Fingerprint Authentication failed.");
    }

    @Override
    public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
        String action = mBundle.getString("action").toLowerCase();
        if(action.equalsIgnoreCase("login")) {
            long userId = myPref.getLongValue("userId");
            Date currentDate = new Date();

            LogModel logModel = new LogModel();
            logModel.setUser_id(userId);
            logModel.setIn_time(currentDate);
            logModel.insert();

            LogModel currentLogModel = SQLite.select()
                                    .from(LogModel.class)
                                    .where(LogModel_Table.user_id.is(userId))
                                    .and(LogModel_Table.in_time.is(currentDate))
                                    .querySingle();

            long logId = currentLogModel.get_id();

            myPref.setLongValue("userId", userId);
            myPref.setLongValue("logId", logId);

            AppUtils.createLoginSession(context);
            Toast.makeText(context, context.getResources().getString(R.string.loging_success), Toast.LENGTH_LONG).show();
            ((Activity) context).finish();
            Intent intent = new Intent(context, DashboardActivity.class);
            context.startActivity(intent);

        } else if(action.equalsIgnoreCase("register")) {
            String name = mBundle.getString("name");
            String registerNumber = mBundle.getString("registerNumber");
            String systemNumber = mBundle.getString("systemNumber");

            StudentModel studentModel = new StudentModel();
            studentModel.setName(name);
            studentModel.setRegister_number(registerNumber);
            studentModel.setSystem_number(systemNumber);
            studentModel.insert();

            StudentModel currentStudentModel = SQLite.select()
                                            .from(StudentModel.class)
                                            .where(StudentModel_Table.name.eq(name))
                                            .and(StudentModel_Table.register_number.eq(registerNumber))
                                            .and(StudentModel_Table.system_number.eq(systemNumber))
                                            .querySingle();

            long userId = currentStudentModel.get_id();
            Date currentDate = new Date();

            LogModel logModel = new LogModel();
            logModel.setUser_id(userId);
            logModel.setIn_time(currentDate);
            logModel.insert();

            LogModel currentLogModel = SQLite.select()
                    .from(LogModel.class)
                    .where(LogModel_Table.user_id.is(userId))
                    .and(LogModel_Table.in_time.is(currentDate))
                    .querySingle();

            long logId = currentLogModel.get_id();

            myPref.setLongValue("userId", userId);
            myPref.setLongValue("logId", logId);

            AppUtils.createLoginSession(context);
            Toast.makeText(context, context.getResources().getString(R.string.register_success), Toast.LENGTH_LONG).show();
            ((Activity) context).finish();
            Intent intent = new Intent(context, DashboardActivity.class);
            context.startActivity(intent);
        } else if(action.equalsIgnoreCase("logout")) {
            long userId = myPref.getLongValue("userId");
            long logId = myPref.getLongValue("logId");

            LogModel currentLogModel = SQLite.select()
                                    .from(LogModel.class)
                                    .where(LogModel_Table._id.is(logId))
                                    .querySingle();

            Date currentDate = new Date();
            currentLogModel.setOut_time(currentDate);
            currentLogModel.update();

            ((Activity) context).finish();
            Toast.makeText(context, context.getResources().getString(R.string.logout_success), Toast.LENGTH_LONG).show();
            AppUtils.logoutUser(context);
        }


    }

    private void updateError(String e){
        TextView textView = (TextView) ((Activity)context).findViewById(R.id.errorTextView);
        textView.setText(e);
    }

}
