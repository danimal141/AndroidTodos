package com.example.danimal141.todos.db;

import android.provider.BaseColumns;

/**
 * Created by danimal141 on 2015/01/17.
 */
public class TaskContract {

    public static final String DB_NAME = "com.example.danimal141.todos.db.tasks";
    public static final int DB_VERSION = 1;
    public static final String TABLE = "tasks";

    public class Columns {
        public static final String TASK = "task";
        public static final String ID = BaseColumns._ID;
    }
}
