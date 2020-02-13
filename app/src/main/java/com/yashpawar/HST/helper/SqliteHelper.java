package com.yashpawar.HST.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SqliteHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "HST";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_HST = "HST";
    public static final String ID = "id";
    public static final String COMPANY_NAME = "company_name";
    public static final String PARTY_NAME = "party_name";
    public static final String ISI = "ISI";
    public static final String CYLINDER_NUMBER = "cyilinder_number";
    public static final String CYLINDER_TYPE = "cylinder_type";
    public static final String DATE_OF_TASTE = "date_of_taste";
    public static final String LAST_HYDRO_TASTE = "last_hydro_taste";
    public static final String ORIGINAL_TW = "orignal_tw";
    public static final String CURRENT_TW = "current_tw";
    public static final String LOSS_OF_WEIGHT = "loss_of_weight";
    public static final String WATER_CAPICITY = "water_capacity";
    public static final String PRESSURE = "Pressure";
    public static final String C1 = "C1";
    public static final String C2 = "C2";
    public static final String C3 = "C3";
    public static final String TE = "TE";
    public static final String PE = "PE";
    public static final String REMARK = "REMARK";
    public static final String NEXT_HYDRO_TEST = "NEXT_HYDRO_TEST";


    //SQL for creating users table
    private static final String SQL_TABLE_HST = " CREATE TABLE " + TABLE_HST
            + " ( "
            + ID + " INTEGER PRIMARY KEY, "
            + COMPANY_NAME + " TEXT, "
            + PARTY_NAME + " TEXT, "
            + CYLINDER_NUMBER + " TEXT, "
            + ISI + " TEXT, "
            + CYLINDER_TYPE + " TEXT, "
            + DATE_OF_TASTE + " TEXT, "
            + LAST_HYDRO_TASTE + " TEXT, "
            + ORIGINAL_TW + " TEXT, "
            + CURRENT_TW + " TEXT,"
            + LOSS_OF_WEIGHT + " TEXT,"
            + WATER_CAPICITY + " TEXT,"
            + PRESSURE + " TEXT,"
            + C1 + " TEXT,"
            + C2 + " TEXT,"
            + C3 + " TEXT,"
            + TE + " TEXT,"
            + PE + " TEXT,"
            + REMARK + " TEXT,"
            + NEXT_HYDRO_TEST + " TEXT"
            + " ) ";


    public SqliteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //Create Table when oncreate gets called
        //sqLiteDatabase.execSQL(SQL_TABLE_HST);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //drop table to create new one if database version updated
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + TABLE_HST);
    }
}