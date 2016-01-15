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

import android.provider.BaseColumns;

/**
 * Defines table and column names for the weather database.
 */
public class MAContract {

    // To make it easy to query for the exact date, we normalize all dates that go into
    // the database to the start of the the Julian day at UTC.

    /*
        Inner class that defines the table contents of the location table

         This is where you will add the strings.
     */
//token is needed to register to the server
    public static final class Token implements BaseColumns {
        public static final String APP_TABLE = "token";
        public static final String COLUMN_APP_ID = "id";
        public static final String COLUMN_APP_TOKEN = "token";

    }
    // definr a new class for each table you need to create
    // the class should estend BaseColumns which adds a predefined column _id to this table
    public static final class Weighbridge implements BaseColumns {
        public static final String TABLE_NAME = "weighbridge"; // table name that can be accessed from all the classes

        public static final String COLUMN_CODE= "code";  //attributes for table
        public static final String COLUMN_DESCRIPTION= "description";
        public static final String COLUMN_LOCATION_CODE= "loc_code";
        public static final String COLUMN_UPDATED = "updated";

//this is how a single table is defined along with the table name and atributes
        // Every table is defined as a class so that it becomes easy to refer clumn names and table names from the instance of this class
        //this makes the code more robust

    }


//create a new class for every other table
    //1. the class name should start with Uppercase Letter and camelCase can be followed
    //2. all the String refering table name  and attributes should be static
    //3. no need to add id feild as the class extends base columns

    //example 2.
    public static final class Truck implements BaseColumns {
        public static final String TABLE_NAME = "truck"; // table name that can be accessed from all the classes

        public static final String COLUMN_CODE= "code";  //attributes for table
        public static final String COLUMN_REG_NUMBER= "r_number";
        public static final String COLUMN_TW= "tare_wt";
        public static final String COLUMN_T_C_C= "trans_c_c";
        public static final String COLUMN_DRIVER_CODE = "driver_code";
        public static final String COLUMN_CAPACITY= "capacity";
        public static final String COLUMN_CARD_ID = "card_id";
        public static final String COLUMN_CAT_CODE= "cat_code";

        public static final String COLUMN_UPDATED = "updated";
        public static final String COLUMN_GROUP_CODE = "group_code";
//this is how a single table is defined along with the table name and atributes
        // Every table is defined as a class so that it becomes easy to refer clumn names and table names from the instance of this class
        //this makes the code more robust

    }


    public static final class Permit implements BaseColumns {
        public static final String TABLE_NAME = "permit"; // table name that can be accessed from all the classes

        public static final String COLUMN_CODE= "code";  //attributes for table
        public static final String COLUMN_PERMIT_NUMBER= "p_number";
        public static final String COLUMN_DESCRIPTION= "p_description";
        public static final String COLUMN_TYPE= "type";
        public static final String COLUMN_UPDATED = "updated";

//this is how a single table is defined along with the table name and atributes
        // Every table is defined as a class so that it becomes easy to refer clumn names and table names from the instance of this class
        //this makes the code more robust

    }
    public static final class Location implements BaseColumns {
        public static final String TABLE_NAME = "location"; // table name that can be accessed from all the classes

        public static final String COLUMN_CODE= "code";  //attributes for table

        public static final String COLUMN_DESCRIPTION= "l_description";

        public static final String COLUMN_UPDATED = "updated";

//this is how a single table is defined along with the table name and atributes
        // Every table is defined as a class so that it becomes easy to refer clumn names and table names from the instance of this class
        //this makes the code more robust

    }
    public static final class Zone implements BaseColumns {
        public static final String TABLE_NAME = "zone"; // table name that can be accessed from all the classes

        public static final String COLUMN_CODE= "code";  //attributes for table

        public static final String COLUMN_DESCRIPTION= "l_description";
        public static final String COLUMN_PRIMARY_LOC= "prim_loc_code";

        public static final String COLUMN_UPDATED = "updated";

//this is how a single table is defined along with the table name and atributes
        // Every table is defined as a class so that it becomes easy to refer clumn names and table names from the instance of this class
        //this makes the code more robust

    }



}
