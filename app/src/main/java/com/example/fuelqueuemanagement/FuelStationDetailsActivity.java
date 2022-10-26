/****************************************************************************
 * Author 1 - Jayawardena W. A. M. (IT19123882)
 * Author 2 - Imbulana Liyanage D. S. I. (IT19134772)
 * Course - Enterprise Application Development (SE4040)
 * Last Modified On- 25.10.2022
 *****************************************************************************/

package com.example.fuelqueuemanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.fuelqueuemanagement.database.StationOnlineDBHelper;
import com.example.fuelqueuemanagement.database.stationModel;

import java.util.Timer;
import java.util.TimerTask;

public class FuelStationDetailsActivity extends AppCompatActivity {

    TextView stationName, address, petrolFuelStatus, dieselFuelStatus, pqlength, dqlength;
    Button beforepump;
    Handler handler = new Handler();
    Runnable runnable;
    int delay = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fuel_station_details);
        Log.e("Station", SessionHandler.station.getStationName());

        stationModel stationModel = SessionHandler.station;

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String stationId = extras.getString("stationId");
            StationOnlineDBHelper dbHelper = new StationOnlineDBHelper();
            dbHelper.getStationById(stationId, FuelStationDetailsActivity.this);
        }

        stationName = findViewById(R.id.stationName);
        address = findViewById(R.id.address);
        pqlength = findViewById(R.id.pqlength);
        dqlength = findViewById(R.id.dqlength);
        beforepump = findViewById(R.id.beforePump);
        petrolFuelStatus = findViewById(R.id.petrolFuelStatus);
        dieselFuelStatus = findViewById(R.id.dieselFuelStatus);

        stationName.setText(stationModel.getStationName());
        address.setText(stationModel.getAddress());
        petrolFuelStatus.setText(stationModel.getPetrolAvailability());
        dieselFuelStatus.setText(stationModel.getDieselAvailability());
        pqlength.setText(Integer.toString(stationModel.getPetrolQueueLength()));
        dqlength.setText(Integer.toString(stationModel.getDieselQueueLength()));

        beforepump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // timer.cancel();
                Intent i = new Intent(FuelStationDetailsActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });
    }

    //Use handlers to refresh activity - https://www.tutorialspoint.com/how-to-run-a-method-every-10-seconds-in-android
    @Override
    protected void onResume() {
        handler.postDelayed(runnable = new Runnable() {
            public void run() {
                handler.postDelayed(runnable, delay);
                Intent intent = getIntent();
                         finish();
                           startActivity(intent);
            }
        }, delay);
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable); //stop handler when activity not visible super.onPause();
    }
}