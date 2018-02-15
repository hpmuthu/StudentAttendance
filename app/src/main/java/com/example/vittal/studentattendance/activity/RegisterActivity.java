package com.example.vittal.studentattendance.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vittal.studentattendance.R;
import com.example.vittal.studentattendance.base.BaseActivity;
import com.example.vittal.studentattendance.database.StudentModel;
import com.example.vittal.studentattendance.database.StudentModel_Table;
import com.example.vittal.studentattendance.helper.PreferencesManager;
import com.raizlabs.android.dbflow.sql.language.SQLite;

public class RegisterActivity extends BaseActivity implements View.OnClickListener {

    PreferencesManager myPref;
    int width, height;

    private RelativeLayout headerLayout;
    private ImageView backImageView;
    private TextView actionBarTitleTextView;
    private LinearLayout contentLayout;
    private TextInputLayout nameEditTextLayout;
    private EditText nameEditText;
    private TextInputLayout registerNumberEditTextLayout;
    private EditText registerNumberEditText;
    private TextInputLayout systemNumberEditTextLayout;
    private EditText systemNumberEditText;
    private LinearLayout fingerPrintLayout;
    private ImageView fingerPrintImageView;
    private TextView registerHintTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        myPref = new PreferencesManager(this);
        width = myPref.getIntValue("ScreenWidth");
        height = myPref.getIntValue("ScreenHeight");

        initializeViews();
        setDynamicViews();
        setOnClickListeners();
        defaultFunctionality();
    }

    private void initializeViews() {
        headerLayout = (RelativeLayout)findViewById( R.id.headerLayout );
        backImageView = (ImageView)findViewById( R.id.backImageView );
        actionBarTitleTextView = (TextView)findViewById( R.id.actionBarTitleTextView );
        contentLayout = (LinearLayout)findViewById( R.id.contentLayout );
        nameEditTextLayout = (TextInputLayout)findViewById( R.id.nameEditTextLayout );
        nameEditText = (EditText)findViewById( R.id.nameEditText );
        registerNumberEditTextLayout = (TextInputLayout)findViewById( R.id.registerNumberEditTextLayout );
        registerNumberEditText = (EditText)findViewById( R.id.registerNumberEditText );
        systemNumberEditTextLayout = (TextInputLayout)findViewById( R.id.systemNumberEditTextLayout );
        systemNumberEditText = (EditText)findViewById( R.id.systemNumberEditText );
        fingerPrintLayout = (LinearLayout)findViewById( R.id.fingerPrintLayout );
        fingerPrintImageView = (ImageView)findViewById( R.id.fingerPrintImageView );
        registerHintTextView = (TextView)findViewById( R.id.registerHintTextView );
    }

    private void setDynamicViews() {
        int backImageWidth = (int) (width * 0.08602);//0.8
        int backImageHeight = (int) (height * 0.03225);//0.5
        int backImageLeftMargin = (int) (width * 0.05376);//0.5
        int contentLayLeftRightPadding = (int) (width * 0.07526);//0.7
        int contentLayTopMargin = (int) (width * 0.12903);//2.0
        int nameEditTextLayBottomMargin = (int) (height * 0.03225);//0.5
        int fingerPrintLayTopMargin = (int) (width * 0.04516);//0.7
        int fingerPrintLayBottomMargin = (int) (width * 0.01290);//0.2
        int fingerPrintImageWidth = (int) (width * 0.12903);//1.2

        RelativeLayout.LayoutParams backImage = (RelativeLayout.LayoutParams) backImageView.getLayoutParams();
        backImage.width = backImageWidth;
        backImage.height = backImageWidth;
        backImage.setMargins(backImageLeftMargin,0,0,0);
        backImageView.setLayoutParams(backImage);

        contentLayout.setPadding(contentLayLeftRightPadding,0,contentLayLeftRightPadding,0);

        RelativeLayout.LayoutParams contentLay = (RelativeLayout.LayoutParams) contentLayout.getLayoutParams();
        contentLay.setMargins(0,contentLayTopMargin,0,0);
        contentLayout.setLayoutParams(contentLay);

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

    }

    private void defaultFunctionality() {

    }

    private void makeRegisterRequest() {
        String name = nameEditText.getText().toString().trim();
        String registerNumber = registerNumberEditText.getText().toString();
        String systemNumber = systemNumberEditText.getText().toString();

        if(name.equals("")) {
            nameEditText.setError(getResources().getString(R.string.enter_name));
            nameEditText.requestFocus();
        } else if(registerNumber.equals("")) {
            registerNumberEditText.setError(getResources().getString(R.string.enter_register_no));
            registerNumberEditText.requestFocus();
        } else if(systemNumber.equals("")) {
            systemNumberEditText.setError(getResources().getString(R.string.enter_system_no));
            systemNumberEditText.requestFocus();
        } else {
            //register functionality
            StudentModel student1 = SQLite.select()
                    .from(StudentModel.class)
                    .where(StudentModel_Table.system_number.eq(systemNumber))
                    .querySingle();

            if(student1 != null) {
                Toast.makeText(this, getResources().getString(R.string.registerno_already_exists), Toast.LENGTH_SHORT).show();
                return;
            }

            StudentModel student2 = SQLite.select()
                    .from(StudentModel.class)
                    .where(StudentModel_Table.system_number.eq(systemNumber))
                    .querySingle();

            if(student2 != null) {
                Toast.makeText(this, getResources().getString(R.string.systemno_already_exists), Toast.LENGTH_SHORT).show();
                return;
            }

            Intent i = new Intent(this, FingerprintActivity.class);
            i.putExtra("action", "register");
            i.putExtra("name", name);
            i.putExtra("registerNumber", registerNumber);
            i.putExtra("systemNumber", systemNumber);
            startActivity(i);
        }
    }

    private void setOnClickListeners() {
        backImageView.setOnClickListener(this);
        fingerPrintLayout.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backImageView:
                onBackPressed();
                break;
            case R.id.fingerPrintLayout:
                makeRegisterRequest();
                break;
        }
    }
}
