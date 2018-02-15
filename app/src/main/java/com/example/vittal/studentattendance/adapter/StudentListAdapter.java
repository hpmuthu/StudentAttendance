package com.example.vittal.studentattendance.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.vittal.studentattendance.R;
import com.example.vittal.studentattendance.database.StudentLogJoined;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class StudentListAdapter extends BaseAdapter {

    private Context context;
    private List<StudentLogJoined> dataList;
    LayoutInflater inflater;
    int width;
    int height;

    public StudentListAdapter(Context context, List<StudentLogJoined> dataList, int width, int height) {
        this.context = context;
        this.dataList = dataList;
        inflater = LayoutInflater.from(this.context);
        this.width = width;
        this.height = height;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {

        MyViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.student_list_row, viewGroup, false);
            holder = new MyViewHolder();
            holder.rowMainLayout = (LinearLayout) convertView.findViewById(R.id.rowMainLayout);
            holder.nameLayout = (LinearLayout) convertView.findViewById(R.id.nameLayout);
            holder.nameTitleTextView = (TextView) convertView.findViewById(R.id.nameTitleTextView);
            holder.nameTextView = (TextView) convertView.findViewById(R.id.nameTextView);
            holder.registerNumberLayout = (LinearLayout) convertView.findViewById(R.id.registerNumberLayout);
            holder.registerNumberTitleTextView = (TextView) convertView.findViewById(R.id.registerNumberTitleTextView);
            holder.registerNumberTextView = (TextView) convertView.findViewById(R.id.registerNumberTextView);
            holder.systemNumberLayout = (LinearLayout) convertView.findViewById(R.id.systemNumberLayout);
            holder.systemNumberTitleTextView = (TextView) convertView.findViewById(R.id.systemNumberTitleTextView);
            holder.systemNumberTextView = (TextView) convertView.findViewById(R.id.systemNumberTextView);
            holder.inOutTimeLayout = (LinearLayout) convertView.findViewById(R.id.inOutTimeLayout);
            holder.inTimeLayout = (LinearLayout) convertView.findViewById(R.id.inTimeLayout);
            holder.inTimeTitleTextView = (TextView) convertView.findViewById(R.id.inTimeTitleTextView);
            holder.inTimeTextView = (TextView) convertView.findViewById(R.id.inTimeTextView);
            holder.outTimeLayout = (LinearLayout) convertView.findViewById(R.id.outTimeLayout);
            holder.outTimeTitleTextView = (TextView) convertView.findViewById(R.id.outTimeTitleTextView);
            holder.outTimeTextView = (TextView) convertView.findViewById(R.id.outTimeTextView);

            convertView.setTag(holder);
        } else {
            holder = (MyViewHolder) convertView.getTag();
        }

        //set dynamic views
        int rowMainLayLeftRightPadding = (int) (width * 0.03225);//0.3
        int rowMainLayTopBottomPadding = (int) (height * 0.01290);//0.2
        int nameLayMarginBottom = (int) (height * 0.006451);//0.1

        holder.rowMainLayout.setPadding(rowMainLayLeftRightPadding, rowMainLayTopBottomPadding,
                rowMainLayLeftRightPadding, rowMainLayTopBottomPadding);

        LinearLayout.LayoutParams nameLay = (LinearLayout.LayoutParams) holder.nameLayout.getLayoutParams();
        nameLay.setMargins(0, 0, 0, nameLayMarginBottom);
        holder.nameLayout.setLayoutParams(nameLay);
        holder.registerNumberLayout.setLayoutParams(nameLay);
        holder.systemNumberLayout.setLayoutParams(nameLay);

        StudentLogJoined studentLog = dataList.get(position);
        Date inTime = studentLog.getIn_time();
        Date outTime = studentLog.getOut_time();
        String inTime_str = "";
        String outTime_str = "";
        if(inTime != null) {
            inTime_str = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(inTime);
        }

        if(outTime != null) {
            outTime_str = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(outTime);
        }

        holder.nameTextView.setText("" + studentLog.getName());
        holder.registerNumberTextView.setText("" + studentLog.getRegister_number());
        holder.systemNumberTextView.setText("" + studentLog.getSystem_number());

        if(inTime_str.equals("")) {
            inTime_str = "-";
        }

        if(outTime_str.equals("")) {
            outTime_str = "-";
        }
        holder.inTimeTextView.setText("" + inTime_str);
        holder.outTimeTextView.setText("" + outTime_str);

        return convertView;

    }

    private class MyViewHolder {
        private LinearLayout rowMainLayout;
        private LinearLayout nameLayout;
        private TextView nameTitleTextView;
        private TextView nameTextView;
        private LinearLayout registerNumberLayout;
        private TextView registerNumberTitleTextView;
        private TextView registerNumberTextView;
        private LinearLayout systemNumberLayout;
        private TextView systemNumberTitleTextView;
        private TextView systemNumberTextView;
        private LinearLayout inOutTimeLayout;
        private LinearLayout inTimeLayout;
        private TextView inTimeTitleTextView;
        private TextView inTimeTextView;
        private LinearLayout outTimeLayout;
        private TextView outTimeTitleTextView;
        private TextView outTimeTextView;

    }
}
