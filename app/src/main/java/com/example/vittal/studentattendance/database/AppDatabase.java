package com.example.vittal.studentattendance.database;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * Created by user on 12/28/2017.
 */

@Database(name = AppDatabase.NAME, version = AppDatabase.VERSION)

public class AppDatabase {

    public static final String NAME = "StudentAttendance";

    public static final int VERSION = 1;
}
