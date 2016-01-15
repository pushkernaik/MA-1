/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.letsappit.mineautomation.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Manages a local database for weather data.
 */
public class MADbHelper extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version.
    private static final int DATABASE_VERSION = 1;
Boolean loaded = false;
    static final String DATABASE_NAME = "VMSB.db";
 Context context;

    public MADbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {



        final String SQL_CREATE_TOKEN_TABLE = "CREATE TABLE " + MAContract.Token.APP_TABLE + " (" +
                // Why AutoIncrement here, and not above?
                // Unique keys will be auto-generated in either case.  But for weather
                // forecasting, it's reasonable to assume the user will want information
                // for a certain date and all dates *following*, so the forecast data
                // should be sorted accordingly.
                MAContract.Token._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +

                MAContract.Token.COLUMN_APP_ID + " INTEGER NOT NULL," +
                // the ID of the location entry associated with this weather data
                MAContract.Token.COLUMN_APP_TOKEN + " TEXT NOT NULL " +

                " );";


        //this is the query for creating the table
        //this will be executed only once
        final String SQL_CREATE_LOCATION_TABLE = "CREATE TABLE " + MAContract.Location.TABLE_NAME + " (" +
                // Why AutoIncrement here, and not above?
                // Unique keys will be auto-generated in either case.  But for weather
                // forecasting, it's reasonable to assume the user will want information
                // for a certain date and all dates *following*, so the forecast data
                // should be sorted accordingly.
                MAContract.Location._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +

                // the ID of the location entry associated with this weather data

                MAContract.Location.COLUMN_CODE + " TEXT NOT NULL, " +
                MAContract.Location.COLUMN_DESCRIPTION + " TEXT ," +

                MAContract.Location.COLUMN_UPDATED + " TEXT, " +
                " UNIQUE (" + MAContract.Location.COLUMN_CODE +  ") ON CONFLICT REPLACE);";

        final String SQL_CREATE_ZONE_TABLE = "CREATE TABLE " + MAContract.Zone.TABLE_NAME + " (" +
                // Why AutoIncrement here, and not above?
                // Unique keys will be auto-generated in either case.  But for weather
                // forecasting, it's reasonable to assume the user will want information
                // for a certain date and all dates *following*, so the forecast data
                // should be sorted accordingly.
                MAContract.Zone._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +

                // the ID of the location entry associated with this weather data

                MAContract.Zone.COLUMN_CODE + " TEXT NOT NULL, " +
                MAContract.Zone.COLUMN_DESCRIPTION + " TEXT ," +
                MAContract.Zone.COLUMN_PRIMARY_LOC + " TEXT ," +
                MAContract.Zone.COLUMN_UPDATED + " TEXT ," +
                // Set up the location column as a foreign key to location table.
                " FOREIGN KEY (" + MAContract.Zone.COLUMN_PRIMARY_LOC + ") REFERENCES " +
                MAContract.Location.TABLE_NAME + " (" + MAContract.Location.COLUMN_CODE + ") " +


                ");";

        final String SQL_CREATE_WEIGHBRIDGE_TABLE = "CREATE TABLE " + MAContract.Weighbridge.TABLE_NAME + " (" +
                // Why AutoIncrement here, and not above?
                // Unique keys will be auto-generated in either case.  But for weather
                // forecasting, it's reasonable to assume the user will want information
                // for a certain date and all dates *following*, so the forecast data
                // should be sorted accordingly.
                MAContract.Weighbridge._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +

                // the ID of the location entry associated with this weather data

                MAContract.Weighbridge.COLUMN_CODE + " TEXT NOT NULL, " +
                MAContract.Weighbridge.COLUMN_DESCRIPTION + " TEXT ," +
                MAContract.Weighbridge.COLUMN_LOCATION_CODE + " TEXT ," +
                MAContract.Weighbridge.COLUMN_UPDATED + " TEXT " +
                " );";

        final String SQL_CREATE_TRUCK_TABLE = "CREATE TABLE " + MAContract.Truck.TABLE_NAME + " (" +
                // Why AutoIncrement here, and not above?
                // Unique keys will be auto-generated in either case.  But for weather
                // forecasting, it's reasonable to assume the user will want information
                // for a certain date and all dates *following*, so the forecast data
                // should be sorted accordingly.
                MAContract.Truck._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +

                // the ID of the location entry associated with this weather data

                MAContract.Truck.COLUMN_CODE + " TEXT UNIQUE NOT NULL, " +
                MAContract.Truck.COLUMN_REG_NUMBER + " TEXT UNIQUE NOT NULL," +
                MAContract.Truck.COLUMN_TW + " INT ," +
                MAContract.Truck.COLUMN_DRIVER_CODE + " TEXT , " +
                MAContract.Truck.COLUMN_CARD_ID + " TEXT ," +
                MAContract.Truck.COLUMN_CAPACITY+ " INT ," +
                MAContract.Truck.COLUMN_CAT_CODE + " TEXT UNIQUE NOT NULL, " +
                MAContract.Truck.COLUMN_GROUP_CODE + " TEXT UNIQUE NOT NULL," +
                MAContract.Truck.COLUMN_UPDATED + " TEXT ," +
                MAContract.Truck.COLUMN_T_C_C + " TEXT " +

                " );";
        final String SQL_CREATE_PERMIT_TABLE = "CREATE TABLE " + MAContract.Permit.TABLE_NAME + " (" +
                // Why AutoIncrement here, and not above?
                // Unique keys will be auto-generated in either case.  But for weather
                // forecasting, it's reasonable to assume the user will want information
                // for a certain date and all dates *following*, so the forecast data
                // should be sorted accordingly.
                MAContract.Permit._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +

                // the ID of the location entry associated with this weather data

                MAContract.Permit.COLUMN_CODE + " TEXT NOT NULL, " +
                MAContract.Permit.COLUMN_DESCRIPTION + " TEXT ," +
                MAContract.Permit.COLUMN_PERMIT_NUMBER + " TEXT ," +
                MAContract.Permit.COLUMN_TYPE + " TEXT ," +
                MAContract.Permit.COLUMN_UPDATED + " TEXT " +
                " );";
        //execute the queries to create the table
        sqLiteDatabase.execSQL(SQL_CREATE_TOKEN_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_WEIGHBRIDGE_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_PERMIT_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_LOCATION_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_TRUCK_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_ZONE_TABLE);


    }



    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        // Note that this only fires if you change the version number for your database.
        // It does NOT depend on the version number for your application.
        // If you want to update the schema without wiping data, commenting out the next 2 lines
        // should be your top priority before modifying this method.
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MAContract.Token.APP_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MAContract.Permit.TABLE_NAME);

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MAContract.Weighbridge.TABLE_NAME);

        onCreate(sqLiteDatabase);
    }

}
