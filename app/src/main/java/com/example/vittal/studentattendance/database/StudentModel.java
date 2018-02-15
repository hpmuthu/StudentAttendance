package com.example.vittal.studentattendance.database;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;


@Table(database = AppDatabase.class)
public class StudentModel extends BaseModel {

    @Column
    @PrimaryKey(autoincrement = true)
    long _id;

    @Column
    String name;

    @Column
    String register_number;

    @Column
    String system_number;

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
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

}
