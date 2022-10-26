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
import android.view.View;
import android.widget.ImageButton;

//Manage customer home UI
public class CustomerHome extends AppCompatActivity {

    ImageButton joinTOQueue, findNearStations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_home);

        joinTOQueue = findViewById(R.id.joinToQueue);
        findNearStations = findViewById(R.id.findNearStations);

        //Navigate to SearchFuelStationActivity
        joinTOQueue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CustomerHome.this, SearchFuelStationsActivity.class);
                startActivity(i);
            }
        });

        //Navigate to FindNearFuelStations activity
        findNearStations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CustomerHome.this, FindNearFuelStations.class);
                startActivity(i);
            }
        });
    }
}