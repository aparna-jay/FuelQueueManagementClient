/****************************************************************************
 * Author 1 - Jayawardena W. A. M. (IT19123882)
 * Author 2 - Imbulana Liyanage D. S. I. (IT19134772)
 * Course - Enterprise Application Development (SE4040)
 * Last Modified On- 25.10.2022
 *****************************************************************************/

package com.example.fuelqueuemanagement.database;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.fuelqueuemanagement.MainActivity;
import com.example.fuelqueuemanagement.SessionHandler;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

//Manage customer database operations
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

    //Set IIS base url
    private String BASE_URL = "http://192.168.43.140:8080/api";


    /* Resources
    POST request with okhttp- https://www.baeldung.com/okhttp-post
    Handling NetworkOnMainThreadException using threads - https://stackoverflow.com/questions/6343166/how-can-i-fix-android-os-networkonmainthreadexception
    */

    //Insert customer details to online database
    public void createCustomer(String customerId, String customerName, String email, String password, String vehicleType,
                               int arrivalTime, int departureTime){
        //Create new thread to prevent network operations on main thread
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try  {
                    //Using OkHttp library
                    OkHttpClient client = new OkHttpClient();
                    //Create string of customer data
                    String json = "{" +
                            "\"customerId\":" + "\"" + customerId + "\"" + ", " +
                            "\"customerName\":" + "\"" + customerName + "\"" + ", " +
                            "\"email\":" + "\"" + email + "\"" + "," +
                            "\"password\":" + "\"" + password  + "\"" + ", " +
                            "\"vehicleType\":" + "\"" + vehicleType + "\"" + "," +
                            "\"arrivalTime\":" + "\"" + arrivalTime + "\"" + "," +
                            "\"departureTime\":" + departureTime+ "}";
                    //Create JSON Object
                    RequestBody body = RequestBody.create(
                            MediaType.parse("application/json"), json);
                    //Create HTTP POST request
                    Request request = new Request.Builder()
                            .url(BASE_URL + "/Station/CreateCustomer")
                            .post(body)
                            .build();
                    Call call = client.newCall(request);
                    try {
                        //Get response
                        Response response = call.execute();
                        Log.i("Response", response.toString());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    //Resource - https://www.geeksforgeeks.org/crud-operation-in-mysql-using-php-volley-android-read-data/
    //Customer login
    public void customerLogin(String email, String password, Context context) {
        String URL = BASE_URL + "/Station/GetCustomerByEmail/" + email;

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        try {
            JSONObject object = new JSONObject();
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(com.android.volley.Request.Method.GET, URL, null,
                    new com.android.volley.Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.i("Response", response.toString());
                            try {
                                //Extract email and password from response
                                String responseEmail = response.getString("email");
                                String responsePassword = response.getString("password");

                                //validate email and password
                                if(responseEmail.equals(email) && responsePassword.equals(password)){
                                    //Store logged user's id
                                    SessionHandler.currentUser = response.getString("customerId");
                                    //start activity after login
                                    Intent intent = new Intent(context, MainActivity.class);
                                    context.startActivity(intent);
                                    Log.i("current User",SessionHandler.currentUser);
                                }
                                else{
                                    Log.e("Error", "User not verified");
                                    Toast.makeText(context, "Incorrect password", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new com.android.volley.Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, "User not found", Toast.LENGTH_LONG).show();
                    Log.i("Error", error.toString());
                }
            });
            requestQueue.add(jsonObjectRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}