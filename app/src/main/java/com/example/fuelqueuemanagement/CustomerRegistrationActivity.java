/****************************************************************************
 * Author 1 - Jayawardena W. A. M. (IT19123882)
 * Author 2 - Imbulana Liyanage D. S. I. (IT19134772)
 * Course - Enterprise Application Development (SE4040)
 * Last Modified On- 25.10.2022
 *****************************************************************************/

package com.example.fuelqueuemanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.RadialGradient;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fuelqueuemanagement.database.StationOnlineDBHelper;
import com.example.fuelqueuemanagement.database.customerDBHelper;
import com.example.fuelqueuemanagement.database.customerModel;

public class CustomerRegistrationActivity extends AppCompatActivity {

    Button register, login;
    EditText username, customerEmail, password, confirmPassword;
    private customerDBHelper cDbHelper;
    private customerModel customermodel;
    int initialTime = 0;
    RadioGroup radioGroup;
    String customerId, customerName, email, password1, vehicleType;
    RadioButton RadioButtonPetrol, RadioButtonDiesel, radioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_registration);

        //Initialize customerDB Helper class
        register= (Button) findViewById(R.id.addUser);
        login= (Button) findViewById(R.id.login);
        cDbHelper = new customerDBHelper(CustomerRegistrationActivity.this);
        customermodel = new customerModel();

        username = (EditText) findViewById(R.id.username);
        customerEmail = (EditText) findViewById(R.id.customerEmail);
        password = (EditText) findViewById(R.id.password);
        confirmPassword = (EditText) findViewById(R.id.confirmPassword);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);

        RadioButtonPetrol=(RadioButton)findViewById(R.id.RadioButtonPetrol);
        RadioButtonDiesel=(RadioButton)findViewById(R.id.RadioButtonDiesel);


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
                customerName = username.getText().toString();
                email = customerEmail.getText().toString();
                password1 = password.getText().toString();
                int selectedId = radioGroup.getCheckedRadioButtonId();
                radioButton = (RadioButton) findViewById(selectedId);
                vehicleType= radioButton.getText().toString();

                //Add station owner data
                cDbHelper.createCustomer("111111112222222233333399", customerName, email, password1,
                        vehicleType, initialTime, initialTime);

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
//            emptyInputEditText();
        } else {
            // Snack Bar to show error message that record already exists
            Toast.makeText(CustomerRegistrationActivity.this, "Email already exists", Toast.LENGTH_SHORT).show();
        }
    }

//    private void emptyInputEditText() {
//        username.setText(null);
//        customerEmail.setText(null);
//        password.setText(null);
//        confirmPassword.setText(null);
//    }


}