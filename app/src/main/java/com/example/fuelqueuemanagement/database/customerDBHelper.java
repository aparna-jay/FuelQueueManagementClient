package com.example.fuelqueuemanagement.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class customerDBHelper extends SQLiteOpenHelper{
    // Database configurations
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "MaxFuelDB";
    private static final String TABLE_CUSTOMER = "customer";
    // Table Configurations
    private static final String COLUMN_CUSTOMER_ID = "id";
    private static final String COLUMN_CUSTOMER_NAME = "name";
    private static final String COLUMN_CUSTOMER_EMAIL = "email";
    private static final String COLUMN_CUSTOMER_PASSWORD = "password";
    private static final String COLUMN_VEHICLE_TYPE = "vehicleType";

    // Create customer table
    private final String CREATE_CUSTOMER_TABLE = "CREATE TABLE " + TABLE_CUSTOMER + "("
            + COLUMN_CUSTOMER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_CUSTOMER_NAME + " TEXT,"
            + COLUMN_CUSTOMER_EMAIL + " TEXT," + COLUMN_CUSTOMER_PASSWORD + " TEXT," + COLUMN_VEHICLE_TYPE + " TEXT)";
    // Drop Customer Table
    private final String DROP_CUSTOMER_TABLE = "DROP TABLE IF EXISTS " + TABLE_CUSTOMER;

    public customerDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_CUSTOMER_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Drop User Table if exist
        db.execSQL(DROP_CUSTOMER_TABLE);
        // Create tables again
        onCreate(db);
    }

    //Add customer
    public void addCustomer(String name, String email, String password, String vehicleType) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_CUSTOMER_NAME, name);
        values.put(COLUMN_CUSTOMER_EMAIL, email);
        values.put(COLUMN_CUSTOMER_PASSWORD, password);
        values.put(COLUMN_VEHICLE_TYPE, vehicleType);
        // Inserting Row
        db.insert(TABLE_CUSTOMER, null, values);
        db.close();
    }

    //Check if email exists
    public boolean checkEmailExists(String email) {
        String[] columns = {
                COLUMN_CUSTOMER_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COLUMN_CUSTOMER_EMAIL + " = ?";
        String[] selectionArgs = {email};

        Cursor cursor = db.query(TABLE_CUSTOMER,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();
        return cursorCount > 0;
    }


    //Login
    public boolean login(String email, String password) {
        String[] columns = {
                COLUMN_CUSTOMER_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COLUMN_CUSTOMER_EMAIL + " = ?" + " AND " + COLUMN_CUSTOMER_PASSWORD + " = ?";
        String[] selectionArgs = {email, password};
        Cursor cursor = db.query(TABLE_CUSTOMER,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();
        return cursorCount > 0;
    }
}







//    /**
//     * This method is to create user record
//     */
//    public void addUser(ParentsModel user) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put(COLUMN_USER_NAME, user.getName());
//        values.put(COLUMN_USER_EMAIL, user.getEmail());
//        values.put(COLUMN_USER_PASSWORD, user.getPassword());
//        values.put(UPLOADED, user.getUploaded());
//        // Inserting Row
//        db.insert(TABLE_USER, null, values);
//        db.close();
//    }



//
//    /**
//     * This method is to fetch all user and return the list of user records
//     */
//    public List<ParentsModel> getAllUsers() {
//        // array of columns to fetch
//        String[] columns = {
//                COLUMN_USER_ID,
//                COLUMN_USER_EMAIL,
//                COLUMN_USER_NAME,
//                COLUMN_USER_PASSWORD,
//                UPLOADED
//        };
//        // sorting orders
//        String sortOrder =
//                COLUMN_USER_NAME + " ASC";
//        List<ParentsModel> userList = new ArrayList<ParentsModel>();
//        SQLiteDatabase db = this.getReadableDatabase();
//        // query the user table
//        /**
//         * Here query function is used to fetch records from user table this function works like we use sql query.
//         * SQL query equivalent to this query function is
//         * SELECT user_id,user_name,user_email,user_password FROM user ORDER BY user_name;
//         */
//        Cursor cursor = db.query(TABLE_USER, //Table to query
//                columns,    //columns to return
//                null,        //columns for the WHERE clause
//                null,        //The values for the WHERE clause
//                null,       //group the rows
//                null,       //filter by row groups
//                sortOrder); //The sort order
//        // Traversing through all rows and adding to list
//        if (cursor.moveToFirst()) {
//            do {
//                ParentsModel user = new ParentsModel();
//                user.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID))));
//                user.setName(cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME)));
//                user.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_USER_EMAIL)));
//                user.setPassword(cursor.getString(cursor.getColumnIndex(COLUMN_USER_PASSWORD)));
//                user.setUploaded(cursor.getString(cursor.getColumnIndex(UPLOADED)));
//                // Adding user record to list
//                userList.add(user);
//            } while (cursor.moveToNext());
//        }
//        cursor.close();
//        db.close();
//        // return user list
//        return userList;
//    }

//    /****************************************************************************************************************************************/
//
//    /**
//     * This method to update user record
//     */
//    public void updateUser(ParentsModel user) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put(COLUMN_USER_PASSWORD, user.getPassword());
//        // updating row
//        db.update(TABLE_USER, values, COLUMN_USER_EMAIL + " = ?",
//                new String[]{String.valueOf(user.getEmail())});
//        db.close();
//    }

    /****************************************************************************************************************************************/



    /****************************************************************************************************************************************/

    /**
     * This method to check user exist or not
     */



    /****************************************************************************************************************************************/

    /****************************************************************************************************************************************/



    /****************************************************************************************************************************************/

//    public boolean checkIfRecordExists(String email){
//        String query = "SELECT * FROM " + TABLE_USER +  " WHERE "  + COLUMN_USER_EMAIL + " Like '%" + email + "%'";
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor c = db.rawQuery(query, null);
//        int count =0;
//        if(c != null){
//            count = c.getCount();
//        }
//        return count > 0;
//    }
//
//}
