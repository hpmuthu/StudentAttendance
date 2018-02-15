package com.example.vittal.studentattendance.database;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.QueryModel;
import com.raizlabs.android.dbflow.structure.BaseQueryModel;

import java.util.Date;

/**
 * Created by user on 2/15/2018.
 */

@QueryModel(database = AppDatabase.class)
public class StudentLogJoined extends BaseQueryModel {

    @Column
    long user_id;

    @Column
    String name;

    @Column
    String register_number;

    @Column
    String system_number;

    @Column
    long log_id;

    @Column
    Date in_time;

    @Column
    Date out_time;

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegister_number() {
        return register_number;
    }

    public void setRegister_number(String register_number) {
        this.register_number = register_number;
    }

    public String getSystem_number() {
        return system_number;
    }

    public void setSystem_number(String system_number) {
        this.system_number = system_number;
    }

    public long getLog_id() {
        return log_id;
    }

    public void setLog_id(long log_id) {
        this.log_id = log_id;
    }

    public Date getIn_time() {
        return in_time;
    }

    public void setIn_time(Date in_time) {
        this.in_time = in_time;
    }

    public Date getOut_time() {
        return out_time;
    }

    public void setOut_time(Date out_time) {
        this.out_time = out_time;
    }
}
