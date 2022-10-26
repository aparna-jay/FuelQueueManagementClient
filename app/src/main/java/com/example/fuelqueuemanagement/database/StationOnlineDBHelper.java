/****************************************************************************
 * Author 1 - Jayawardena W. A. M. (IT19123882)
 * Author 2 - Imbulana Liyanage D. S. I. (IT19134772)
 * Course - Enterprise Application Development (SE4040)
 * Last Modified On- 25.10.2022
*****************************************************************************/

package com.example.fuelqueuemanagement.database;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.fuelqueuemanagement.FillingStationProfileActivity;
import com.example.fuelqueuemanagement.MainActivity;
import com.example.fuelqueuemanagement.SessionHandler;
import com.example.fuelqueuemanagement.StationOwnerHomeActivity;
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
//    private String BASE_URL = "http://192.168.43.140:8080/api";
    private String BASE_URL = "http://192.168.1.3:8080/api";


    /* Resources
    POST request with okhttp- https://www.baeldung.com/okhttp-post
    Handling NetworkOnMainThreadException using threads - https://stackoverflow.com/questions/6343166/how-can-i-fix-android-os-networkonmainthreadexception
    */

    //Insert station details to online database
    public void createStationOwner(String stationId, String stationName, String email, String address, String password,
                                   String petrolAvailability, String dieselAvailability, int petrolQueueLength, int dieselQueueLength) {
        //Create new thread to prevent network operations on main thread
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //Using OkHttp library
                    OkHttpClient client = new OkHttpClient();
                    //Create string of station data
                    String json = "{" +
                            "\"stationId\":" + "\"" + stationId + "\"" + ", " +
                            "\"stationName\":" + "\"" + stationName + "\"" + ", " +
                            "\"email\":" + "\"" + email + "\"" + "," +
                            "\"address\":" + "\"" + address + "\"" + "," +
                            "\"password\":" + "\"" + password + "\"" + ", " +
                            "\"petrolAvailability\":" + "\"" + petrolAvailability + "\"" + "," +
                            "\"dieselAvailability\":" + "\"" + dieselAvailability + "\"" + "," +
                            "\"petrolQueueLength\":" + petrolQueueLength + "," +
                            "\"dieselQueueLength\":" + dieselQueueLength + "}";
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
                                        password, petrolAvailability, dieselAvailability, petrolQueueLength, dieselQueueLength);
                                stationModelList.add(stationModel);
                                SessionHandler.allStations = stationModelList;
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
    public void IncreasePetrolQueueLength(String id) {
        //Create new thread to prevent network operations on main thread
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
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
    public void IncreaseDieselQueueLength(String id) {
        //Create new thread to prevent network operations on main thread
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
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

    //Resources - Make okhttp request without body - https://stackoverflow.com/questions/35743516/how-to-make-okhttp-post-request-without-a-request-body
    //Decrease petrol queue length
    public void DecreasePetrolQueueLength(String id) {
        //Create new thread to prevent network operations on main thread
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    //create empty request body
                    RequestBody body = RequestBody.create(null, new byte[]{});
                    //build http request
                    Request request = new Request.Builder()
                            .url(BASE_URL + "/Station/DecrementPetrolQueue/" + id)
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
    //Decrease diesel queue length
    public void DecreaseDieselQueueLength(String id) {
        //Create new thread to prevent network operations on main thread
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    //create empty request body
                    RequestBody body = RequestBody.create(null, new byte[]{});
                    //build http request
                    Request request = new Request.Builder()
                            .url(BASE_URL + "/Station/DecrementDieselQueue/" + id)
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
    //Update petrol fuel status
    public void UpdatePetrolStatus(String id, String availability, Context context) {
        //Create new thread to prevent network operations on main thread
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    //create empty request body
                    RequestBody body = RequestBody.create(null, new byte[]{});
                    //build http request
                    Request request = new Request.Builder()
                            .url(BASE_URL + "/Station/UpdatePetrolFuelStatus/" + id + "?availability=" + availability)
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
    //Update diesel fuel status
    public void UpdateDieselStatus(String id, String availability, Context context) {
        //Create new thread to prevent network operations on main thread
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    //create empty request body
                    RequestBody body = RequestBody.create(null, new byte[]{});
                    //build http request
                    Request request = new Request.Builder()
                            .url(BASE_URL + "/Station/UpdateDieselFuelStatus/" + id + "?availability=" + availability)
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

    //Resource - https://www.geeksforgeeks.org/crud-operation-in-mysql-using-php-volley-android-read-data/
    //Station owner login
    public void stationOwnerLogin(String email, String password, Context context) {
        String URL = BASE_URL + "/Station/GetStationByEmail/" + email;

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
                                    SessionHandler.currentUser = response.getString("stationId");
                                    //Get station data
                                    getStationById(SessionHandler.currentUser, context);

                                    //start activity after login
                                    Intent intent = new Intent(context, StationOwnerHomeActivity.class);
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

    //Resource - https://www.geeksforgeeks.org/crud-operation-in-mysql-using-php-volley-android-read-data/
    //Get station by id
    public void getStationById(String id, Context context) {
        String URL = BASE_URL + "/Station/GetStationById/" + id;

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        try {
            JSONObject object = new JSONObject();
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(com.android.volley.Request.Method.GET, URL, null,
                    new com.android.volley.Response.Listener<JSONObject>() {
                        String responseId, responsePassword, responseName,responseEmail, responseAddress, responsePetrolAvailability, responseDieselAvailability;
                        int responsePetrolQueueLength, responseDieselQueueLength;
                        stationModel sModel;
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.i("Response", response.toString());
                            try {
                                //Extract data from response
                                responseId = response.getString("stationId");
                                responsePassword = response.getString("password");
                                responseEmail = response.getString("email");
                                responseName = response.getString("stationName");
                                responseAddress = response.getString("address");
                                responsePetrolAvailability = response.getString("petrolAvailability");
                                responseDieselAvailability = response.getString("dieselAvailability");
                                responsePetrolQueueLength = response.getInt("petrolQueueLength");
                                responseDieselQueueLength = response.getInt("dieselQueueLength");

                                //create new station object
                                 sModel = new stationModel(responseId, responseName,responseEmail, responseAddress, responsePassword, responsePetrolAvailability,
                                         responseDieselAvailability, responsePetrolQueueLength, responseDieselQueueLength);
                                 SessionHandler.station = sModel;

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new com.android.volley.Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.i("Error", error.toString());
                }
            });
            requestQueue.add(jsonObjectRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
