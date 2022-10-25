/****************************************************************************
 * Author 1 - Jayawardena W. A. M. (IT19123882)
 * Author 2 - Imbulana Liyanage D. S. I. (IT19134772)
 * Course - Enterprise Application Development (SE4040)
 * Last Modified On- 25.10.2022
*****************************************************************************/

package com.example.fuelqueuemanagement.database;

import android.content.Context;
import android.util.Log;
import android.view.View;

import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//Manage API calls for online database operations
public class StationOnlineDBHelper {

    //Set IIS base url
    private String BASE_URL = "http://192.168.43.140:8080/api";


    /* Resources
    POST request with okhttp- https://www.baeldung.com/okhttp-post
    Handling NetworkOnMainThreadException using threads - https://stackoverflow.com/questions/6343166/how-can-i-fix-android-os-networkonmainthreadexception
    */

    //Insert station details to online database
    public void createStationOwner(String stationId, String stationName, String email, String address, String password,
                                   String petrolAvailability, String dieselAvailability, int petrolQueueLength, int dieselQueueLength){
        //Create new thread to prevent network operations on main thread
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try  {
                    //Using OkHttp library
                    OkHttpClient client = new OkHttpClient();
                    //Create string of station data
                    String json = "{" +
                            "\"stationId\":" + "\"" + stationId + "\"" + ", " +
                            "\"stationName\":" + "\"" + stationName + "\"" + ", " +
                            "\"email\":" + "\"" + email + "\"" + "," +
                            "\"address\":" + "\"" + address  + "\"" + "," +
                            "\"password\":" + "\"" + password  + "\"" + ", " +
                            "\"petrolAvailability\":" + "\"" + petrolAvailability + "\"" + "," +
                            "\"dieselAvailability\":" + "\"" + dieselAvailability + "\"" + "," +
                            "\"petrolQueueLength\":" + petrolQueueLength + "," +
                            "\"dieselQueueLength\":" + dieselQueueLength+ "}";
                    //Create JSON Object
                    RequestBody body = RequestBody.create(
                            MediaType.parse("application/json"), json);
                    //Create HTTP POST request
                    Request request = new Request.Builder()
                            .url(BASE_URL + "/Station/CreateStation")
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
    //Get all station details
    public List<stationModel> getAllStations(Context context) {
        List<stationModel> stationModelList = new ArrayList<>();
        String URL = BASE_URL + "/Station/GetAllStations";
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonArrayRequest objectRequest = new JsonArrayRequest(
                com.android.volley.Request.Method.GET,
                URL,
                null,

                new com.android.volley.Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.e("Response", response.toString());
                        JSONObject ob = null;

                        for (int i = 0; i < response.length(); i++) {
                            try {
                                ob = response.getJSONObject(i);
                                String stationId = ob.getString("stationId");
                                String stationName = ob.getString("stationName");
                                String email = ob.getString("email");
                                String address = ob.getString("address");
                                String password = ob.getString("password");
                                String petrolAvailability = ob.getString("petrolAvailability");
                                String dieselAvailability = ob.getString("dieselAvailability");
                                int petrolQueueLength = ob.getInt("petrolQueueLength");
                                int dieselQueueLength = ob.getInt("dieselQueueLength");
                                stationModel stationModel = new stationModel(stationId, stationName, email, address,
                                        password, petrolAvailability,  dieselAvailability, petrolQueueLength, dieselQueueLength);
                                stationModelList.add(stationModel);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Error", error.toString());
                    }
                }
        );
        requestQueue.add(objectRequest);
        return stationModelList;
    }

    //Resources - Make okhttp request without body - https://stackoverflow.com/questions/35743516/how-to-make-okhttp-post-request-without-a-request-body
    //Increase petrol queue length
    public void IncreasePetrolQueueLength(String id){
        //Create new thread to prevent network operations on main thread
        Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try  {
                        OkHttpClient client = new OkHttpClient();
                        //create empty request body
                        RequestBody body = RequestBody.create(null, new byte[]{});
                        //build http request
                        Request request = new Request.Builder()
                                .url(BASE_URL + "/Station/IncrementPetrolQueue/" + id)
                                .put(body)
                                .build();
                        Call call = client.newCall(request);
                        try {
                            Response response = call.execute();
                            Log.e("Response", response.toString());
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

    //Resources - Make okhttp request without body - https://stackoverflow.com/questions/35743516/how-to-make-okhttp-post-request-without-a-request-body
    //Increase diesel queue length
    public void IncreaseDieselQueueLength(String id){
        //Create new thread to prevent network operations on main thread
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try  {
                    OkHttpClient client = new OkHttpClient();
                    //create empty request body
                    RequestBody body = RequestBody.create(null, new byte[]{});
                    //build http request
                    Request request = new Request.Builder()
                            .url(BASE_URL + "/Station/IncrementDieselQueue/" + id)
                            .put(body)
                            .build();
                    Call call = client.newCall(request);
                    try {
                        Response response = call.execute();
                        Log.e("Response", response.toString());
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
}
