package com.example.vittal.studentattendance.activity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.vittal.studentattendance.R;
import com.example.vittal.studentattendance.adapter.StudentListAdapter;
import com.example.vittal.studentattendance.base.BaseActivity;
import com.example.vittal.studentattendance.database.LogModel;
import com.example.vittal.studentattendance.database.LogModel_Table;
import com.example.vittal.studentattendance.database.StudentLogJoined;
import com.example.vittal.studentattendance.database.StudentModel;
import com.example.vittal.studentattendance.database.StudentModel_Table;
import com.example.vittal.studentattendance.helper.PreferencesManager;
import com.raizlabs.android.dbflow.sql.language.OperatorGroup;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DashboardActivity extends BaseActivity implements View.OnClickListener {

    PreferencesManager myPref;
    int width, height;

    private RelativeLayout headerLayout;
    private ImageView backImageView;
    private TextView actionBarTitleTextView;
    private ImageView searchImageView;
    private ImageView logoutImageView;
    private LinearLayout contentLayout;
    private LinearLayout searchLayout;
    private LinearLayout searchInputLayout;
    private LinearLayout searchStudentLayout;
    private ImageView searchStudentImageView;
    private EditText searchStudentEditText;
    private LinearLayout searchStartDateLayout;
    private TextView searchStartDateTextView;
    private ImageView searchStartDateImageView;
    private LinearLayout searchEndDateLayout;
    private TextView searchEndDateTextView;
    private ImageView searchEndDateImageView;
    private LinearLayout searchButtonLayout;
    private ImageView searchButtonImageView;
    private ListView studentsListView;

    StudentListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

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
        searchImageView = (ImageView)findViewById( R.id.searchImageView );
        logoutImageView = (ImageView)findViewById( R.id.logoutImageView );
        contentLayout = (LinearLayout)findViewById( R.id.contentLayout );
        searchLayout = (LinearLayout)findViewById( R.id.searchLayout );
        searchInputLayout = (LinearLayout)findViewById( R.id.searchInputLayout );
        searchStudentLayout = (LinearLayout)findViewById( R.id.searchStudentLayout );
        searchStudentImageView = (ImageView)findViewById( R.id.searchStudentImageView );
        searchStudentEditText = (EditText)findViewById( R.id.searchStudentEditText );
        searchStartDateLayout = (LinearLayout)findViewById( R.id.searchStartDateLayout );
        searchStartDateTextView = (TextView)findViewById( R.id.searchStartDateTextView );
        searchStartDateImageView = (ImageView)findViewById( R.id.searchStartDateImageView );
        searchEndDateLayout = (LinearLayout)findViewById( R.id.searchEndDateLayout );
        searchEndDateTextView = (TextView)findViewById( R.id.searchEndDateTextView );
        searchEndDateImageView = (ImageView)findViewById( R.id.searchEndDateImageView );
        searchButtonLayout = (LinearLayout)findViewById( R.id.searchButtonLayout );
        searchButtonImageView = (ImageView)findViewById( R.id.searchButtonImageView );
        studentsListView = (ListView)findViewById( R.id.studentsListView );
    }

    private void setDynamicViews() {
        int backImageWidth = (int) (width * 0.08602);//0.8
        int backImageHeight = (int) (height * 0.03225);//0.5
        int backImageLeftMargin = (int) (width * 0.04301);//0.4
        int backImageRightMargin = (int) (width * 0.04301);//0.4
        int logoutImageWidth = (int) (width * 0.05376);//0.5
        int contentLayLeftRightPadding = (int) (width * 0.07526);//0.7
        int contentLayTopMargin = (int) (width * 0.05376);//0.5

        int searchLayoutWidth = (int) (width * 0.85f);//85%
        int searchLayoutBottomSpace = (int) (height * 0.01935);//0.3
        int searchInputRightMargin = (int) (width * 0.06451);//0.6
        int searchStudentLayoutBottomMargin = (int) (height * 0.01935);//0.3
        int searchIconWidth = (int) (width * 0.04301);//0.4
        int searchIconRightMargin = (int) (width * 0.02150);//0.2
        int startDateLayoutRightMargin = (int) (width * 0.06451);//0.6
        int calendarIconWidth = (int) (width * 0.05376);//0.5

        RelativeLayout.LayoutParams backImage = (RelativeLayout.LayoutParams) backImageView.getLayoutParams();
        backImage.width = backImageWidth;
        backImage.height = backImageWidth;
        backImage.setMargins(backImageLeftMargin,0,backImageRightMargin,0);
        backImageView.setLayoutParams(backImage);

        RelativeLayout.LayoutParams logoutImage = (RelativeLayout.LayoutParams) logoutImageView.getLayoutParams();
        logoutImage.width = logoutImageWidth;
        logoutImage.height = logoutImageWidth;
        logoutImage.setMargins(0,0,backImageLeftMargin,0);
        logoutImageView.setLayoutParams(logoutImage);

        RelativeLayout.LayoutParams searchImage = (RelativeLayout.LayoutParams) searchImageView.getLayoutParams();
        searchImage.width = logoutImageWidth;
        searchImage.height = logoutImageWidth;
        searchImage.setMargins(0,0,backImageLeftMargin,0);
        searchImageView.setLayoutParams(searchImage);

        /*contentLayout.setPadding(contentLayLeftRightPadding,0,contentLayLeftRightPadding,0);

        RelativeLayout.LayoutParams contentLay = (RelativeLayout.LayoutParams) contentLayout.getLayoutParams();
        contentLay.setMargins(0,contentLayTopMargin,0,0);
        contentLayout.setLayoutParams(contentLay);*/

        LinearLayout.LayoutParams searchLay = (LinearLayout.LayoutParams) searchLayout.getLayoutParams();
        searchLay.width = searchLayoutWidth;
        searchLay.setMargins(0, searchLayoutBottomSpace, 0, searchLayoutBottomSpace);
        searchLayout.setLayoutParams(searchLay);

        LinearLayout.LayoutParams searchInputLay = (LinearLayout.LayoutParams) searchInputLayout.getLayoutParams();
        searchInputLay.setMargins(0, 0, searchInputRightMargin, 0);
        searchInputLayout.setLayoutParams(searchInputLay);

        LinearLayout.LayoutParams searchStudentLay = (LinearLayout.LayoutParams) searchStudentLayout.getLayoutParams();
        searchStudentLay.setMargins(0, 0, 0, searchStudentLayoutBottomMargin);
        searchStudentLayout.setLayoutParams(searchStudentLay);

        LinearLayout.LayoutParams searchStartDateLay = (LinearLayout.LayoutParams) searchStartDateLayout.getLayoutParams();
        searchStartDateLay.setMargins(0, 0, startDateLayoutRightMargin, 0);
        searchStartDateLayout.setLayoutParams(searchInputLay);

        LinearLayout.LayoutParams searchStudentIcon = (LinearLayout.LayoutParams) searchStudentImageView.getLayoutParams();
        searchStudentIcon.setMargins(0, 0, searchIconRightMargin, 0);
        searchStudentIcon.width = searchIconWidth;
        searchStudentIcon.height = searchIconWidth;
        searchStudentImageView.setLayoutParams(searchStudentIcon);

        LinearLayout.LayoutParams startDateIcon = (LinearLayout.LayoutParams) searchStartDateImageView.getLayoutParams();
        startDateIcon.setMargins(searchIconRightMargin, 0, 0, 0);
        startDateIcon.width = calendarIconWidth;
        startDateIcon.height = calendarIconWidth;
        searchStartDateImageView.setLayoutParams(startDateIcon);
        searchEndDateImageView.setLayoutParams(startDateIcon);

        LinearLayout.LayoutParams searchIcon = (LinearLayout.LayoutParams) searchButtonImageView.getLayoutParams();
        searchIcon.width = searchIconWidth;
        searchIcon.height = searchIconWidth;
        searchButtonImageView.setLayoutParams(searchIcon);
    }

    private void defaultFunctionality() {
        List<StudentLogJoined> studentLogList = SQLite.select(StudentModel_Table._id.as("user_id").withTable(), StudentModel_Table.name,StudentModel_Table.register_number,
                                StudentModel_Table.system_number, LogModel_Table._id.as("log_id").withTable(),LogModel_Table.in_time,LogModel_Table.out_time)
                                            .from(StudentModel.class)
                                            .leftOuterJoin(LogModel.class)
                                            .on(StudentModel_Table._id.withTable().eq(LogModel_Table.user_id.withTable()))
                                            .orderBy(LogModel_Table._id.withTable(), false)
                                            .queryCustomList(StudentLogJoined.class);

        if(studentLogList.size() > 0) {
            adapter = new StudentListAdapter(this, studentLogList, width, height);
            studentsListView.setAdapter(adapter);
        }
    }

    private void makeSearchRequest() {
        try {
            Date startDate = null;
            Date endDate = null;
            List<StudentLogJoined> studentLogList = new ArrayList<>();

            String searchQuery = searchStudentEditText.getText().toString().trim();
            String temp_startDate = searchStartDateTextView.getText().toString();
            String temp_endDate = searchEndDateTextView.getText().toString();

            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            if(!temp_startDate.equals(""))
                startDate = sdf.parse(temp_startDate);

            if(!temp_endDate.equals(""))
                endDate = sdf.parse(temp_endDate);

            //condition
            OperatorGroup conditionGroup = OperatorGroup.clause();
            if(searchQuery.length() > 0) {
                conditionGroup.and((OperatorGroup.clause()
                        .and(StudentModel_Table.name.eq(searchQuery))
                        .or(StudentModel_Table.register_number.eq(searchQuery))
                        .or(StudentModel_Table.system_number.eq(searchQuery))));
            }

            if(startDate != null) {
                conditionGroup.and((OperatorGroup.clause()
                        .and(LogModel_Table.in_time.greaterThan(startDate))));
            }

            if(endDate != null) {
                conditionGroup.and((OperatorGroup.clause()
                        .and(LogModel_Table.in_time.lessThan(endDate))));
            }

            studentLogList = SQLite.select(StudentModel_Table._id.as("user_id").withTable(), StudentModel_Table.name,StudentModel_Table.register_number,
                    StudentModel_Table.system_number, LogModel_Table._id.as("log_id").withTable(),LogModel_Table.in_time,LogModel_Table.out_time)
                    .from(StudentModel.class)
                    .leftOuterJoin(LogModel.class)
                    .on(StudentModel_Table._id.withTable().eq(LogModel_Table.user_id.withTable()))
                    .where(conditionGroup)
                    .orderBy(LogModel_Table._id.withTable(), false)
                    .queryCustomList(StudentLogJoined.class);

            adapter = new StudentListAdapter(this, studentLogList, width, height);
            studentsListView.setAdapter(adapter);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void datePicker(final TextView editText) {
        final Calendar c = Calendar.getInstance();
        Calendar minDate = Calendar.getInstance();
        Calendar maxDate = Calendar.getInstance();
        minDate.add(Calendar.YEAR, -1);
        maxDate.add(Calendar.YEAR, 1);
        final int mYear = c.get(Calendar.YEAR);
        final int mMonth = c.get(Calendar.MONTH);
        final int mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dpd = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                String selectedDate = formatDate(year, monthOfYear, dayOfMonth);
                editText.setText(selectedDate);
            }
        }, mYear, mMonth, mDay);
        dpd.getDatePicker().setMaxDate(maxDate.getTimeInMillis());
        dpd.getDatePicker().setMinDate(minDate.getTimeInMillis());
        dpd.show();
    }

    private static String formatDate(int year, int month, int day) {

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(0);
        cal.set(year, month, day);
        Date date = cal.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

        return sdf.format(date);
    }

    private void makeLogoutRequest() {
        Intent i = new Intent(this, FingerprintActivity.class);
        i.putExtra("action", "logout");
        startActivity(i);
    }

    private void logout() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

        dialogBuilder.setTitle(getResources().getString(R.string.logout_lbl)); // Sets title for your alertbox

        dialogBuilder.setMessage(getResources().getString(R.string.want_logout)); // Message to be displayed on alertbox

        dialogBuilder.setIcon(R.mipmap.ic_launcher); // Icon for your alertbox

        /* When positive (yes/ok) is clicked */
        dialogBuilder.setPositiveButton("" + getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                makeLogoutRequest();
                /*AppUtils.logoutUser(DashboardActivity.this);

                finish();
                Toast.makeText(DashboardActivity.this, getResources().getString(R.string.logout_success), Toast.LENGTH_LONG).show();*/
            }
        });

        /* When negative (No/cancel) button is clicked*/
        dialogBuilder.setNegativeButton("" + getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        dialogBuilder.show();
    }

    private void setOnClickListeners() {
        backImageView.setOnClickListener(this);
        searchImageView.setOnClickListener(this);
        logoutImageView.setOnClickListener(this);
        searchStartDateLayout.setOnClickListener(this);
        searchEndDateLayout.setOnClickListener(this);
        searchButtonLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backImageView:
                onBackPressed();
                break;
            case R.id.searchImageView:
                if(searchLayout.getVisibility() == View.GONE) {
                    searchLayout.setVisibility(View.VISIBLE);
                } else {
                    searchLayout.setVisibility(View.GONE);
                    searchStudentEditText.setText("");
                    searchStartDateTextView.setText("");
                    searchEndDateTextView.setText("");
                }
                break;
            case R.id.logoutImageView:
                logout();
                break;
            case R.id.searchStartDateLayout:
                datePicker(searchStartDateTextView);
                break;
            case R.id.searchEndDateLayout:
                datePicker(searchEndDateTextView);
                break;
            case R.id.searchButtonLayout:
                makeSearchRequest();
                break;
        }
    }
}
