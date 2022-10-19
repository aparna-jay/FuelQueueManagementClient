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
    public void addCustomer(customerModel customerModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_CUSTOMER_NAME, customerModel.getName());
        values.put(COLUMN_CUSTOMER_EMAIL, customerModel.getEmail());
        values.put(COLUMN_CUSTOMER_PASSWORD, customerModel.getPassword());
        values.put(COLUMN_VEHICLE_TYPE, customerModel.getVehicleType());
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