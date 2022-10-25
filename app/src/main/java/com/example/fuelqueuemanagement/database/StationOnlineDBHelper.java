/****************************************************************************
 * Author 1 - Jayawardena W. A. M. (IT19123882)
 * Author 2 - Imbulana Liyanage D. S. I. (IT19134772)
 * Course - Enterprise Application Development (SE4040)
 * Last Modified On- 25.10.2022
*****************************************************************************/

package com.example.fuelqueuemanagement.database;

import android.util.Log;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import java.io.IOException;

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



}
