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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.fuelqueuemanagement.database.StationOnlineDBHelper;

import java.util.Random;

//Manage frontend for station owner registration activity
public class StationOwnerRegistrationActivity extends AppCompatActivity {

    EditText txt_stationId, txt_stationName, txt_email, txt_address, txt_password;
    Button register, login;
    String stationId, stationName, email, address, password;
    String FuelAvailability = "Available";
    int initialQueueLength = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station_owner_registration);

        //Initialize stationOnlineDB Helper class
        StationOnlineDBHelper stationOnlineDBHelper = new StationOnlineDBHelper();

        //Get UI elements by Id
        txt_stationId = (EditText) findViewById(R.id.stationId);
        txt_stationName = (EditText) findViewById(R.id.stationName);
        txt_email = (EditText) findViewById(R.id.email);
        txt_address = (EditText) findViewById(R.id.address);
        txt_password = (EditText) findViewById(R.id.password);
        register = (Button) findViewById(R.id.register);
        login = (Button) findViewById(R.id.login);

        //Add user to database
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                //Get data as user inputs
                stationId = txt_stationId.getText().toString();
                stationName = txt_stationName.getText().toString();
                email = txt_email.getText().toString();
                address = txt_address.getText().toString();
                password = txt_password.getText().toString();

                //Add station owner data
                stationId = generateInt();
                stationOnlineDBHelper.createStationOwner(stationId, stationName, email, address, password,
                        FuelAvailability, FuelAvailability, initialQueueLength, initialQueueLength);
            }
        });

        //Navigate to login activity
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    //Generate 24 digit random numbers - https://stackoverflow.com/questions/17306475/java-random-numbers-generator-which-generate-twenty-four-numbers
    private String generateInt() {
        StringBuilder str = new StringBuilder();
        Random random = new Random();
        for(int i = 0; i < 24; i++) {
            str.append(random.nextInt(10));
        }
        return str.toString();
    }
}