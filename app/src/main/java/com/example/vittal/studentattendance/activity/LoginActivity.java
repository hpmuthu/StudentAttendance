package com.example.vittal.studentattendance.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.design.widget.TextInputLayout;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vittal.studentattendance.R;
import com.example.vittal.studentattendance.base.BaseActivity;
import com.example.vittal.studentattendance.database.StudentModel;
import com.example.vittal.studentattendance.database.StudentModel_Table;
import com.example.vittal.studentattendance.helper.PreferencesManager;
import com.raizlabs.android.dbflow.sql.language.SQLite;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    PreferencesManager myPref;
    int width, height;

    private LinearLayout contentLayout;
    private TextView loginTitleTextView;
    private TextInputLayout nameEditTextLayout;
    private EditText nameEditText;
    private TextInputLayout systemNumberEditTextLayout;
    private EditText systemNumberEditText;
    private LinearLayout fingerPrintLayout;
    private ImageView fingerPrintImageView;
    private TextView loginHintTextView;
    private LinearLayout registerationLayout;
    private TextView registerationTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        myPref = new PreferencesManager(this);
        width = myPref.getIntValue("ScreenWidth");
        height = myPref.getIntValue("ScreenHeight");


        initializeViews();
        setDynamicViews();
        setOnClickListeners();
        defaultFunctionality();
        askPermission();
    }

    private void initializeViews() {
        contentLayout = (LinearLayout)findViewById( R.id.contentLayout );
        loginTitleTextView = (TextView)findViewById( R.id.loginTitleTextView );
        nameEditTextLayout = (TextInputLayout)findViewById( R.id.nameEditTextLayout );
        nameEditText = (EditText)findViewById( R.id.nameEditText );
        systemNumberEditTextLayout = (TextInputLayout)findViewById( R.id.systemNumberEditTextLayout );
        systemNumberEditText = (EditText)findViewById( R.id.systemNumberEditText );
        fingerPrintLayout = (LinearLayout)findViewById( R.id.fingerPrintLayout );
        fingerPrintImageView = (ImageView)findViewById( R.id.fingerPrintImageView );
        loginHintTextView = (TextView)findViewById( R.id.loginHintTextView );
        registerationLayout = (LinearLayout)findViewById( R.id.registerationLayout );
        registerationTextView = (TextView)findViewById( R.id.registerationTextView );
    }

    private void setDynamicViews() {
        int contentLayLeftRightPadding = (int) (width * 0.07526);//0.7
        int loginTitleTextTopMargin = (int) (width * 0.12903);//2.0
        int loginTitleTextBottomMargin = (int) (height * 0.06451);//1.0
        int nameEditTextLayBottomMargin = (int) (height * 0.03225);//0.5
        int fingerPrintLayTopMargin = (int) (width * 0.04516);//0.7
        int fingerPrintLayBottomMargin = (int) (width * 0.01290);//0.2
        int fingerPrintImageWidth = (int) (width * 0.12903);//1.2
        int registerationLayTopMargin = (int) (height * 0.06451);//1.0

        contentLayout.setPadding(contentLayLeftRightPadding,0,contentLayLeftRightPadding,0);

        LinearLayout.LayoutParams loginTitleText = (LinearLayout.LayoutParams) loginTitleTextView.getLayoutParams();
        loginTitleText.setMargins(0,loginTitleTextTopMargin,0,loginTitleTextBottomMargin);
        loginTitleTextView.setLayoutParams(loginTitleText);

        LinearLayout.LayoutParams nameEditTextLay = (LinearLayout.LayoutParams) nameEditTextLayout.getLayoutParams();
        nameEditTextLay.setMargins(0,0,0,nameEditTextLayBottomMargin);
        nameEditTextLayout.setLayoutParams(nameEditTextLay);
        systemNumberEditTextLayout.setLayoutParams(nameEditTextLay);

        LinearLayout.LayoutParams fingerPrintLay = (LinearLayout.LayoutParams) fingerPrintLayout.getLayoutParams();
        fingerPrintLay.setMargins(0,fingerPrintLayTopMargin,0,fingerPrintLayBottomMargin);
        fingerPrintLayout.setLayoutParams(fingerPrintLay);

        LinearLayout.LayoutParams fingerPrintImage = (LinearLayout.LayoutParams) fingerPrintImageView.getLayoutParams();
        fingerPrintImage.width = fingerPrintImageWidth;
        fingerPrintImage.height = fingerPrintImageWidth;
        fingerPrintImageView.setLayoutParams(fingerPrintImage);

        LinearLayout.LayoutParams registerationLay = (LinearLayout.LayoutParams) registerationLayout.getLayoutParams();
        registerationLay.setMargins(0,registerationLayTopMargin,0,0);
        registerationLayout.setLayoutParams(registerationLay);
    }

    private void defaultFunctionality() {

    }

    private void makeLoginRequest() {
        String name = nameEditText.getText().toString().trim();
        String systemNumber = systemNumberEditText.getText().toString();

        if(name.equals("")) {
            nameEditText.setError(getResources().getString(R.string.enter_name));
            nameEditText.requestFocus();
        } else if(systemNumber.equals("")) {
            systemNumberEditText.setError(getResources().getString(R.string.enter_system_no));
            systemNumberEditText.requestFocus();
        } else {
            //login functionality
            StudentModel student = SQLite.select()
                    .from(StudentModel.class)
                    .where(StudentModel_Table.name.eq(name))
                    .and(StudentModel_Table.system_number.eq(systemNumber))
                    .querySingle();

            if(student != null) {
                long userId = student.get_id();
                Intent i = new Intent(this, FingerprintActivity.class);
                i.putExtra("action", "login");
                i.putExtra("userId", userId);
                startActivity(i);
            } else {
                Toast.makeText(this, getResources().getString(R.string.enter_valid_name_systemno), Toast.LENGTH_SHORT).show();
            }

        }

    }

    private void setOnClickListeners() {
        fingerPrintLayout.setOnClickListener(this);
        registerationTextView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fingerPrintLayout:
                makeLoginRequest();
                break;
            case R.id.registerationTextView:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
        }
    }

    private void askPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.USE_FINGERPRINT)) {
                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.USE_FINGERPRINT
                        }, 1);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
        }
    }
}
