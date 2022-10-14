package com.example.fuelqueuemanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fuelqueuemanagement.database.customerDBHelper;
import com.example.fuelqueuemanagement.database.customerModel;

public class CustomerRegistrationActivity extends AppCompatActivity {

    Button register, login;
    EditText username, customerEmail, password, confirmPassword;
    private customerDBHelper cDbHelper;
    private customerModel customermodel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_registration);

        register= (Button) findViewById(R.id.addUser);
        login= (Button) findViewById(R.id.login);
        cDbHelper = new customerDBHelper(CustomerRegistrationActivity.this);
        customermodel = new customerModel();

        username = (EditText) findViewById(R.id.username);
        customerEmail = (EditText) findViewById(R.id.customerEmail);
        password = (EditText) findViewById(R.id.password);
        confirmPassword = (EditText) findViewById(R.id.confirmPassword);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddUser();
            }
        });
    }

    private void AddUser() {
        String value1 = password.getText().toString().trim();
        String value2 = confirmPassword.getText().toString().trim();
        if (!value1.contentEquals(value2)) {
            Toast.makeText(CustomerRegistrationActivity.this, "Passwords Do Not Match", Toast.LENGTH_SHORT).show();
        }
        else if (!cDbHelper.checkEmailExists(customerEmail.getText().toString().trim())) {
            String vehicleType = "Petrol";
            customermodel.setName(username.getText().toString().trim());
            customermodel.setEmail(customerEmail.getText().toString().trim());
            customermodel.setPassword(password.getText().toString().trim());
            customermodel.setVehicleType(vehicleType);
            cDbHelper.addCustomer(customermodel);
            // Snack Bar to show success message that record saved successfully
            Toast.makeText(CustomerRegistrationActivity.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
            emptyInputEditText();
        } else {
            // Snack Bar to show error message that record already exists
            Toast.makeText(CustomerRegistrationActivity.this, "Email already exists", Toast.LENGTH_SHORT).show();
        }
    }

    private void emptyInputEditText() {
        username.setText(null);
        customerEmail.setText(null);
        password.setText(null);
        confirmPassword.setText(null);
    }

}