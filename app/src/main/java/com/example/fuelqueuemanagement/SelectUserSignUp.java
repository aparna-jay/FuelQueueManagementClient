package com.example.fuelqueuemanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class SelectUserSignUp extends AppCompatActivity {

    ImageButton signUpCustomer, signUpOwner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_user_sign_up);

        signUpCustomer = findViewById(R.id.signUpCustomer);
        signUpOwner = findViewById(R.id.signUpOwner);

        //Navigate to SearchFuelStationActivity
        signUpCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SelectUserSignUp.this, CustomerRegistrationActivity.class);
                startActivity(i);
            }
        });

        //Navigate to FindNearFuelStations activity
        signUpOwner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SelectUserSignUp.this, StationOwnerRegistrationActivity.class);
                startActivity(i);
            }
        });
    }
}