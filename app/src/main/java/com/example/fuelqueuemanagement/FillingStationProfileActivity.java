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
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.fuelqueuemanagement.database.StationOnlineDBHelper;
import com.example.fuelqueuemanagement.database.stationModel;

//Manage filling station profile UI
public class FillingStationProfileActivity extends AppCompatActivity {

    stationModel stationModel;
    TextView stationName, stationID, address, email, petrolAvailability, dieselAvailability;
    Button dsl_changeavailability, ptrl_changeavailability;
    String petrolStatus, dieselStatus;
    StationOnlineDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filling_station_profile);

        //Get logged station data
        stationModel = SessionHandler.station;

        dbHelper = new StationOnlineDBHelper();



        stationName = findViewById(R.id.stationName);
        stationID = findViewById(R.id.stationID);
        address = findViewById(R.id.address);
        email = findViewById(R.id.email);
        petrolAvailability = findViewById(R.id.petrol_availability);
        dieselAvailability = findViewById(R.id.diesel_availability);
        dsl_changeavailability = findViewById(R.id.dsl_changeavailability);
        ptrl_changeavailability = findViewById(R.id.ptrl_changeavailability);

        //set station data
        stationName.setText(stationModel.getStationName());
        stationID.setText(stationModel.getStationId());
        address.setText(stationModel.getAddress());
        email.setText(stationModel.getEmail());
        petrolAvailability.setText(stationModel.getPetrolAvailability());
        dieselAvailability.setText(stationModel.getDieselAvailability());

        petrolStatus = stationModel.getPetrolAvailability();
        dieselStatus = stationModel.getDieselAvailability();

        if(petrolStatus.equals("Available")){
            ptrl_changeavailability.setText("Mark as Finished");
        }
        else if(petrolStatus.equals("Not Available")){
            ptrl_changeavailability.setText("Mark as Available");
        }

        if(dieselStatus.equals("Available")){
            dsl_changeavailability.setText("Mark as Finished");
        }
        else if(dieselStatus.equals("Not Available")){
            dsl_changeavailability.setText("Mark as Available");
        }


        ptrl_changeavailability.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(petrolStatus.equals("Available")){
                    dbHelper.UpdatePetrolStatus(stationModel.getStationId(), "Not Available", FillingStationProfileActivity.this);

                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            dbHelper.getStationById(stationModel.getStationId(), FillingStationProfileActivity.this);
                            final Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent = getIntent();
                                    finish();
                                    startActivity(intent);
                                    ptrl_changeavailability.setText("Mark as Available");
                                }
                            }, 1000);
                        }
                    }, 1000);
                }
                else if(petrolStatus.equals("Not Available")){
                    dbHelper.UpdatePetrolStatus(stationModel.getStationId(), "Available", FillingStationProfileActivity.this);

                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            dbHelper.getStationById(stationModel.getStationId(), FillingStationProfileActivity.this);
                            final Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent = getIntent();
                                    finish();
                                    startActivity(intent);
                                    ptrl_changeavailability.setText("Mark as Finished");
                                }
                            }, 1000);
                        }
                    }, 1000);
                }
            }
        });

        dsl_changeavailability.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(dieselStatus.equals("Available")){
                    dbHelper.UpdateDieselStatus(stationModel.getStationId(), "Not Available", FillingStationProfileActivity.this);

                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            dbHelper.getStationById(stationModel.getStationId(), FillingStationProfileActivity.this);
                            final Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent = getIntent();
                                    finish();
                                    startActivity(intent);
                                    dsl_changeavailability.setText("Mark as Available");
                                }
                            }, 1000);
                        }
                    }, 1000);
                }
                else if(dieselStatus.equals("Not Available")){
                    dbHelper.UpdateDieselStatus(stationModel.getStationId(), "Available", FillingStationProfileActivity.this);

                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            dbHelper.getStationById(stationModel.getStationId(), FillingStationProfileActivity.this);
                            final Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent = getIntent();
                                    finish();
                                    startActivity(intent);
                                    dsl_changeavailability.setText("Mark as Finished");
                                }
                            }, 1000);
                        }
                    }, 1000);
                }
            }
        });

    }
}