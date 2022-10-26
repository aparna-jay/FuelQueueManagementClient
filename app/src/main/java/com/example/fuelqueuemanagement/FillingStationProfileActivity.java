/****************************************************************************
 * Author 1 - Jayawardena W. A. M. (IT19123882)
 * Author 2 - Imbulana Liyanage D. S. I. (IT19134772)
 * Course - Enterprise Application Development (SE4040)
 * Last Modified On- 25.10.2022
 *****************************************************************************/

package com.example.fuelqueuemanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.fuelqueuemanagement.database.stationModel;

//Manage filling station profile UI
public class FillingStationProfileActivity extends AppCompatActivity {

    stationModel stationModel;
    TextView stationName, stationID, address, email, petrolAvailability, dieselAvailability;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filling_station_profile);

        //Get logged station data
        stationModel = SessionHandler.station;

        stationName = findViewById(R.id.stationName);
        stationID = findViewById(R.id.stationID);
        address = findViewById(R.id.address);
        email = findViewById(R.id.email);
        petrolAvailability = findViewById(R.id.petrol_availability);
        dieselAvailability = findViewById(R.id.diesel_availability);

        //set station data
        stationName.setText(stationModel.getStationName());
        stationID.setText(stationModel.getStationId());
        address.setText(stationModel.getAddress());
        email.setText(stationModel.getEmail());
        petrolAvailability.setText(stationModel.getPetrolAvailability());
        dieselAvailability.setText(stationModel.getDieselAvailability());




    }
}