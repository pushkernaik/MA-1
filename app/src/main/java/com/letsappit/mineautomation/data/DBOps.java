package com.letsappit.mineautomation.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.format.Time;

import com.letsappit.mineautomation.BO.ListItem;
import com.letsappit.mineautomation.BO.PrimaryLocation;
import com.letsappit.mineautomation.BO.Truck;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import utils.Util;

/**
 * Created by radhaprasadborkar on 02/11/15.
 */
public class DBOps {
//normalize julian date
    public static long normalizeDate(long startDate) {
        // normalize the start date to the beginning of the (UTC) day
        Time time = new Time();
        time.set(startDate);

        int julianDay = Time.getJulianDay(startDate, time.gmtoff);
        return time.setJulianDay(julianDay);
    }

/////////Primarylocation methods////
    public static long insertNewLocation(Context context,PrimaryLocation primaryLocation) {
       MADbHelper dbHelper = new MADbHelper(context);
       SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues loc_values = new ContentValues();
        loc_values.put(MAContract.Location.COLUMN_CODE, primaryLocation.getCode());
        loc_values.put(MAContract.Location.COLUMN_DESCRIPTION, primaryLocation.getDescription());
        loc_values.put(MAContract.Location.COLUMN_UPDATED, Util.getFormatedCurrentDate());
        return db.insert(MAContract.Location.TABLE_NAME, null, loc_values);


    }

    public static int updateLocation(Context context, PrimaryLocation primaryLocation) {
        MADbHelper dbHelper = new MADbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(MAContract.Location.COLUMN_DESCRIPTION, primaryLocation.getDescription());
        values.put(MAContract.Location.COLUMN_UPDATED, Util.getFormatedCurrentDate());
        // updating row
        return db.update(MAContract.Location.TABLE_NAME, values, MAContract.Location.COLUMN_CODE + " = ?",
                new String[]{primaryLocation.getCode()});
    }



    public static ArrayList<PrimaryLocation> getAllLocations(Context context) throws ParseException {
        ArrayList<PrimaryLocation> allPrimaryLocations = new ArrayList<>();
        MADbHelper dbHelper = new MADbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query(MAContract.Location.TABLE_NAME,
                new String[]{MAContract.Location.COLUMN_CODE, MAContract.Location.COLUMN_DESCRIPTION, MAContract.Location.COLUMN_UPDATED},
                null,null,null,null,null);
        for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext())
        {
            allPrimaryLocations.add(new PrimaryLocation(cursor.getString(0),cursor.getString(1),Util.getDateFromString(cursor.getString(2))));
        }
        return allPrimaryLocations;
    }
    public static PrimaryLocation getLocationByCode(Context context,String code) throws ParseException {
        MADbHelper dbHelper = new MADbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query(MAContract.Location.TABLE_NAME,
                new String[]{MAContract.Location.COLUMN_CODE, MAContract.Location.COLUMN_DESCRIPTION, MAContract.Location.COLUMN_UPDATED},
                MAContract.Location.COLUMN_CODE + " = ?", new String[]{code}, null, null, null);

        if(cursor.moveToFirst())
            return new PrimaryLocation(cursor.getString(0),cursor.getString(1),Util.getDateFromString(cursor.getString(2)));
        else
            return new PrimaryLocation("","",new Date());
    }

///////////////end primary location methods//////////////


    ////////////////// TRUCK METHODS///////////////////////

    public static long insertNewTruck(Context context,Truck truck) {
        MADbHelper dbHelper = new MADbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues truck_values = new ContentValues();
        truck_values.put(MAContract.Truck.COLUMN_CODE, truck.getCode());
        truck_values.put(MAContract.Truck.COLUMN_REG_NUMBER, truck.getReg_no());
        truck_values.put(MAContract.Truck.COLUMN_T_C_C, truck.getTransport_cont_code());
        truck_values.put(MAContract.Truck.COLUMN_DRIVER_CODE, truck.getDriverCode());
        truck_values.put(MAContract.Truck.COLUMN_GROUP_CODE, truck.getGroup_code());
        truck_values.put(MAContract.Truck.COLUMN_CARD_ID, truck.getCard_id());
        truck_values.put(MAContract.Truck.COLUMN_CAT_CODE, truck.getCat_code());
        truck_values.put(MAContract.Truck.COLUMN_UPDATED, Util.getFormatedDate(truck.getReg_date()));
        truck_values.put(MAContract.Truck.COLUMN_TW, truck.getTare_wt());
        truck_values.put(MAContract.Truck.COLUMN_CAPACITY, truck.getCapacity());
        return db.insert(MAContract.Truck.TABLE_NAME, null, truck_values);


    }
    public static ArrayList<String> getAllTruckRegNo(Context context) {
        ArrayList<String> allTruckRegNo = new ArrayList<>();
        MADbHelper dbHelper = new MADbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query(MAContract.Truck.TABLE_NAME,
                new String[]{MAContract.Truck.COLUMN_REG_NUMBER},
                null,null,null,null,null);
        for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext())
        {
            allTruckRegNo.add(cursor.getString(0));
        }
        return allTruckRegNo;
    }
    public static Truck getTruckByRegNo(Context context,String regNo) throws ParseException {
        MADbHelper dbHelper = new MADbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query(MAContract.Truck.TABLE_NAME,
                new String[]{
                        MAContract.Location.COLUMN_CODE,
                        MAContract.Truck.COLUMN_REG_NUMBER,
                        MAContract.Truck.COLUMN_TW,
                        MAContract.Truck.COLUMN_T_C_C,
                        MAContract.Truck.COLUMN_DRIVER_CODE,
                        MAContract.Truck.COLUMN_CARD_ID,
                        MAContract.Truck.COLUMN_CAPACITY,
                        MAContract.Truck.COLUMN_CAT_CODE,
                        MAContract.Truck.COLUMN_GROUP_CODE,
                        MAContract.Truck.COLUMN_UPDATED},
                MAContract.Truck.COLUMN_REG_NUMBER + " = ?", new String[]{regNo}, null, null, null);

        if(cursor.moveToFirst())
            return new Truck(
                    cursor.getString(0),
                    cursor.getString(1),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(8),
                    cursor.getString(5),
                    cursor.getString(7),
                    Util.getDateFromString(cursor.getString(9)),
                    cursor.getInt(2),
                    cursor.getInt(6));
        else
            return null;
    }


    /////////////////////TRUCK METHODS END //////////////////////////

    // master methods
    public static ArrayList<ListItem> getAllRows(Context context,String tableName,String code,String description)
    {
        ArrayList<ListItem> allRows = new ArrayList<>();
        MADbHelper dbHelper = new MADbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query(tableName,
                new String[]{code, description},
                null,null,null,null,null);
        for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext())
        {
            allRows.add(new ListItem(cursor.getString(0),cursor.getString(1)));
        }
        return allRows;
    }
    public static int deleteRow(Context context, String code,String tableName,String codeColumnName) {
        MADbHelper dbHelper = new MADbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int i = db.delete(tableName, codeColumnName + " = ?",
                new String[]{code});
        db.close();
        return i;
    }


//
//    static ContentValues createUserValues(String user_id, String name, String email, String api_key) {
//        // Create a new map of values, where column names are the keys
//        ContentValues testValues = new ContentValues();
//        testValues.put(WeatherContract.User.COLUMN_USER_ID, user_id);
//        testValues.put(WeatherContract.User.COLUMN_USER_NAME, name);
//        testValues.put(WeatherContract.User.COLUMN_USER_EMAIL, email);
//        testValues.put(WeatherContract.User.COLUMN_USER_API, api_key);
//
//        return testValues;
//    }
//
//    public static long insertUserValues(Context context,String user_id, String name, String email, String api_key) {
//        // insert our test records into the database
//        WeatherDbHelper dbHelper = new WeatherDbHelper(context);
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//        ContentValues testValues = DBOps.createUserValues(user_id, name, email, api_key);
//
//        long locationRowId;
//        locationRowId = db.insert(WeatherContract.User.TABLE_NAME, null, testValues);
//
//        db.close();
//        return locationRowId;
//    }
//
//    public static String getAPIKey(Context context) {
//        // insert our test records into the database
//        String API_KEY = "";
//        WeatherDbHelper dbHelper = new WeatherDbHelper(context);
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//        Cursor cursor = db.query(WeatherContract.User.TABLE_NAME, new String[]{WeatherContract.User.COLUMN_USER_API}, null, null, null, null, null);
//        if(cursor.moveToFirst())
//        {
//            API_KEY = cursor.getString(0);
//        }
//        if(TextUtils.isEmpty(API_KEY)||API_KEY.equals(""))
//        {
//            return "";
//        }
//        db.close();
//        cursor.close();
//        return API_KEY;
//    }
//
//    public static String getUserId(Context context) {
//        // insert our test records into the database
//        String userId = "";
//        WeatherDbHelper dbHelper = new WeatherDbHelper(context);
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//        Cursor cursor = db.query(WeatherContract.User.TABLE_NAME, new String[]{WeatherContract.User.COLUMN_USER_ID}, null, null, null, null, null);
//        if(cursor.moveToFirst())
//        {
//            userId = cursor.getString(0);
//        }
//        if(TextUtils.isEmpty(userId)||userId.equals(""))
//        {
//            return "";
//        }
//        db.close();
//        cursor.close();
//        return userId;
//    }
//    public static ArrayList<String> getModels(Context context,String brand_id) {
//        // insert our test records into the database
//        ArrayList<String> models = new ArrayList<String>();
//        WeatherDbHelper dbHelper = new WeatherDbHelper(context);
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//        Cursor cursor = db.query(WeatherContract.Models.TABLE_NAME, new String[]{WeatherContract.Models.COLUMN_MODEL_NAME}, WeatherContract.Models.COLUMN_MODEL_BRAND_ID + " =?", new String[]{brand_id}, null, null, null);
//        for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext())
//        {
//            models.add(cursor.getString(0));
//        }
//        db.close();
//        return models;
//    }
//
//    public static void insertModels(Context context,ArrayList<ContentValues> models)
//    { WeatherDbHelper dbHelper = new WeatherDbHelper(context);
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//        for(ContentValues model : models)
//        {
//            db.insert(WeatherContract.Models.TABLE_NAME,null,model);
//        }
//        db.close();
//    }
//    public static void insertWIP(Context context,ArrayList<ContentValues> wip)
//    { WeatherDbHelper dbHelper = new WeatherDbHelper(context);
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//        for(ContentValues wipValue : wip)
//        {
//            db.insert(WeatherContract.WIP.TABLE_NAME,null,wipValue);
//        }
//        db.close();
//    }
//    public static ArrayList<Model> getAllModels(Context context)
//    {
//        ArrayList<Model> models = new ArrayList<>();
//        WeatherDbHelper dbHelper = new WeatherDbHelper(context);
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//
//        Cursor cursor = db.query(WeatherContract.Models.TABLE_NAME, new String[]{WeatherContract.Models.COLUMN_MODEL_NAME, WeatherContract.Models.COLUMN_MODEL_ID, WeatherContract.Models.COLUMN_MODEL_BRAND_ID}, null, null, null, null, null);
//
//          for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext())
//               {
//                   models.add(new Model(cursor.getString(1),cursor.getString(0),cursor.getString(2)));
//               }
//        db.close();
//        cursor.close();
//        return models;
//    }
//    public static String getModelId(Context context,String modelName)
//    {
//        String id="";
//        WeatherDbHelper dbHelper = new WeatherDbHelper(context);
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//
//        Cursor cursor = db.query(WeatherContract.Models.TABLE_NAME, new String[]{WeatherContract.Models.COLUMN_MODEL_ID}, WeatherContract.Models.COLUMN_MODEL_NAME+" =?", new String[]{modelName}, null, null, null);
//
//        for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext())
//        {
//            id = cursor.getString(0);
//        }
//        db.close();
//        cursor.close();
//        return id;
//    }
//    public static String getModelName(Context context,String modelId)
//    {
//        String name="";
//        WeatherDbHelper dbHelper = new WeatherDbHelper(context);
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//
//        Cursor cursor = db.query(WeatherContract.Models.TABLE_NAME, new String[]{WeatherContract.Models.COLUMN_MODEL_NAME}, WeatherContract.Models.COLUMN_MODEL_ID+" =?", new String[]{modelId}, null, null, null);
//
//        for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext())
//        {
//            name = cursor.getString(0);
//        }
//        db.close();
//        cursor.close();
//        return name;
//    }
//    static ContentValues createCarValues(String model, String vin, String regid, String brand_id,String car_id) {
//        // Create a new map of values, where column names are the keys
//        ContentValues testValues = new ContentValues();
//        testValues.put(WeatherContract.Cars.COLUMN_MODEL_CAR_ID, car_id);
//        testValues.put(WeatherContract.Cars.COLUMN_MODEL_VIN, vin);
//        testValues.put(WeatherContract.Cars.COLUMN_MODEL_REG, regid);
//        testValues.put(WeatherContract.Cars.COLUMN_MODEL_BRAND_ID, brand_id);
//        testValues.put(WeatherContract.Cars.COLUMN_MODEL_ID, model);
//
//        return testValues;
//    }
//    public static long insertCarValues(Context context,String vin, String model_id, String regid, String brand_id,String car_id) {
//        // insert our test records into the database
//        WeatherDbHelper dbHelper = new WeatherDbHelper(context);
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//        ContentValues testValues = DBOps.createCarValues(model_id, vin, regid, brand_id, car_id);
//
//        long locationRowId;
//        locationRowId = db.insert(WeatherContract.Cars.TABLE_NAME, null, testValues);
//
//        db.close();
//        return locationRowId;
//    }
//
//    public static int updateServiceStatus(Context context,String bId,String code)
//    {//Log.i("update status", "bID =" + bId + " code" + code);
//        WeatherDbHelper dbHelper = new WeatherDbHelper(context);
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put(WeatherContract.Booking.COLUMN_BOOK_STATUS, code);
//        // updating row
//        return db.update(WeatherContract.Booking.TABLE_NAME, values, WeatherContract.Booking.COLUMN_BOOK_BID + " = ?",
//                new String[] {bId});
//    }
//    public static int updateWIP(Context context,String wip,String bId)
//    {
//        //Log.i("update status","wip ="+wip+" code"+bId);
//        WeatherDbHelper dbHelper = new WeatherDbHelper(context);
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put(WeatherContract.Booking.COLUMN_BOOK_WIP, wip);
//        // updating row
//        return db.update(WeatherContract.Booking.TABLE_NAME, values, WeatherContract.Booking.COLUMN_BOOK_BID + " = ?",
//                new String[] {bId});
//    }
//    public static String getStatusBody(Context context,String code)
//    {String statusBody="";
//
//        WeatherDbHelper dbHelper = new WeatherDbHelper(context);
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//        Cursor cursor = db.query(WeatherContract.WIP.TABLE_NAME, new String[]{
//                        WeatherContract.WIP.COLUMN_WIP_DESCRIPTION}, WeatherContract.WIP.COLUMN_WIP_CODE + " = ?",
//                new String[]{code}, null, null, null, null);
//
//        for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext())
//        {
//           statusBody = cursor.getString(0);
//        }
//        db.close();
//        cursor.close();
//        return statusBody;
//
//    }
//    public static ArrayList<Service> getBookings(Context context)
//    {
//        ArrayList<Service> servicsList = new ArrayList<Service>();
//
//        WeatherDbHelper dbHelper = new WeatherDbHelper(context);
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//
//        Cursor cursor = db.query(WeatherContract.Booking.TABLE_NAME, new String[]{
//                WeatherContract.Booking.COLUMN_BOOK_WIP,
//                WeatherContract.Booking.COLUMN_BOOK_TOS,
//                WeatherContract.Booking.COLUMN_BOOK_CAR_ID,
//                WeatherContract.Booking.COLUMN_BOOK_STATUS,
//                WeatherContract.Booking.COLUMN_BOOK_BID},null, null, null, null, null);
//        //Log.i("getBoookings","crusor"+cursor.getCount());
//        for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext())
//        {
//            String wip   = cursor.getString(0);
//            String mileage   = cursor.getString(1);
//            String mName = getModelName(context, carNameById(context, cursor.getString(2)));
//            String status = getStatusBody(context, cursor.getString(3));
//            String bId = cursor.getString(4);
//            //Log.i("getBoookings",wip+mileage+mName+status);
//            servicsList.add(new Service(wip,status,mileage,mName,bId));
//        }
//        db.close();
//        cursor.close();
//        return servicsList;
//    }
//
//    private static String carNameById(Context context, String id) {
//        String carName = "";
//        WeatherDbHelper dbHelper = new WeatherDbHelper(context);
//
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//
//        Cursor cursor = db.query(WeatherContract.Cars.TABLE_NAME, new String[]{WeatherContract.Cars.COLUMN_MODEL_ID}, WeatherContract.Cars.COLUMN_MODEL_CAR_ID + " = ?",
//                new String[] {id}, null, null, null, null);
//
//        for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext())
//        {
//           carName = cursor.getString(0);
//        }
//        db.close();
//        cursor.close();
//        return carName;
//    }
//
//    public static long insertBookingValues(Context context, String bId, String mos, String tos, String mileage, String details, String carId,String wip,String date,String time) {
//        // insert our test records into the database
//        WeatherDbHelper dbHelper = new WeatherDbHelper(context);
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//        ContentValues testValues = new ContentValues();
//        testValues.put(WeatherContract.Booking.COLUMN_BOOK_BID,bId);
//        testValues.put(WeatherContract.Booking.COLUMN_BOOK_CAR_ID,carId);
//        testValues.put(WeatherContract.Booking.COLUMN_BOOK_MILEAGE,mileage);
//        testValues.put(WeatherContract.Booking.COLUMN_BOOK_WIP,wip);
//        testValues.put(WeatherContract.Booking.COLUMN_BOOK_STATUS,"B");
//        testValues.put(WeatherContract.Booking.COLUMN_BOOK_MOS,mos);
//        testValues.put(WeatherContract.Booking.COLUMN_BOOK_TOS,tos);
//        testValues.put(WeatherContract.Booking.COLUMN_BOOK_COMPLETE,"0");
//        testValues.put(WeatherContract.Booking.COLUMN_BOOK_DATE,date);
//        testValues.put(WeatherContract.Booking.COLUMN_BOOK_TIME, time);
//        long locationRowId;
//        locationRowId = db.insert(WeatherContract.Booking.TABLE_NAME, null, testValues);
//
//        db.close();
//        return locationRowId;
//    }
//    public static ArrayList<Service> getHistory(Context context,String car_id)
//    {
//        ArrayList<Service> servicsList = new ArrayList<Service>();
//
//        WeatherDbHelper dbHelper = new WeatherDbHelper(context);
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//
//        Cursor cursor = db.query(WeatherContract.Booking.TABLE_NAME, new String[]{
//                WeatherContract.Booking.COLUMN_BOOK_WIP,
//                WeatherContract.Booking.COLUMN_BOOK_TOS,
//                WeatherContract.Booking.COLUMN_BOOK_CAR_ID,
//                WeatherContract.Booking.COLUMN_BOOK_STATUS,
//                WeatherContract.Booking.COLUMN_BOOK_BID},WeatherContract.Booking.COLUMN_BOOK_CAR_ID + " = ?",
//                new String[] {car_id}, null, null, null);
////        Log.i("getBoookings","crusor"+cursor.getCount());
//        for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext())
//        {
//
//            String wip   = cursor.getString(0);
//            String mileage   = cursor.getString(1);
//            String mName = getModelName(context, carNameById(context, cursor.getString(2)));
//            String status = getStatusBody(context, cursor.getString(3));
//            String bId = cursor.getString(4);
//          //  Log.i("getBoookings",wip+mileage+mName+status);
//            if(wip.equalsIgnoreCase("X")) {
//                servicsList.add(new Service(wip, status, mileage, mName, bId));
//            }
//        }
//        db.close();
//        cursor.close();
//        return servicsList;
//    }
//    public static ArrayList<Car> getAllCars(Context context)
//    {
//        ArrayList<Car> allCars = new ArrayList<Car>();
//        WeatherDbHelper dbHelper = new WeatherDbHelper(context);
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//
//        Cursor cursor = db.query(WeatherContract.Cars.TABLE_NAME, new String[]{WeatherContract.Cars.COLUMN_MODEL_VIN, WeatherContract.Cars.COLUMN_MODEL_REG, WeatherContract.Cars.COLUMN_MODEL_ID, WeatherContract.Cars.COLUMN_MODEL_CAR_ID}, null, null, null, null, null);
//
//        for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext())
//        {
//            String mId   = cursor.getString(2);
//            String mName = getModelName(context, mId);
//            allCars.add(new Car(mName,cursor.getString(0),cursor.getString(1),mId,cursor.getString(3)));
//        }
//        db.close();
//        cursor.close();
//        return allCars;
//    }
//
//
//    public static ArrayList<ServiceLocation> getAllLocations(Context context) {
//        ArrayList<ServiceLocation> allLocations = new ArrayList<ServiceLocation>();
//        WeatherDbHelper dbHelper = new WeatherDbHelper(context);
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//
//        Cursor cursor = db.query(WeatherContract.Location.TABLE_NAME, new String[]{WeatherContract.Location.COLUMN_LOCATION_NAME,WeatherContract.Location.COLUMN_LOCATION_LAT,WeatherContract.Location.COLUMN_LOCATION_LONG}, null, null, null, null, null);
//
//        for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext())
//        {
//            String name   = cursor.getString(0);
//            String lat = cursor.getString(1);
//            String lon = cursor.getString(2);
//            allLocations.add(new ServiceLocation(name,lat,lon));
//        }
//        db.close();
//        cursor.close();
//        return allLocations;
//    }
//    public static void saveToken(Context context,String token, int version) {
//        WeatherDbHelper dbHelper = new WeatherDbHelper(context);
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//        String updateQuery = "update " + WeatherContract.Token.APP_TABLE + " set "
//                + WeatherContract.Token.COLUMN_APP_TOKEN + "= '" + token + "' where "
//                + WeatherContract.Token.COLUMN_APP_ID + " = " + 1 + "";
//        db.execSQL(updateQuery);
//
//    }
//
//
//    public static void logoutDB() {
//    }
}
