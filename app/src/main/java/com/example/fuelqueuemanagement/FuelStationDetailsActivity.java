package com.example.fuelqueuemanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class FuelStationDetailsActivity extends AppCompatActivity {

    TextView stationName, address, fuelStatus, pqlength, dqlength;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fuel_station_details);
        Log.e("Station", SessionHandler.station.getStationName());

        stationName = findViewById(R.id.stationName);
        address = findViewById(R.id.address);
        fuelStatus = findViewById(R.id.fuelStatus);
        pqlength = findViewById(R.id.pqlength);
        dqlength = findViewById(R.id.dqlength);

        stationName.setText(SessionHandler.station.getStationName());
        address.setText(SessionHandler.station.getAddress());
        fuelStatus.setText(SessionHandler.station.getPetrolAvailability());
        pqlength.setText(Integer.toString(SessionHandler.station.getPetrolQueueLength()));
        dqlength.setText(Integer.toString(SessionHandler.station.getDieselQueueLength()));

    }
}