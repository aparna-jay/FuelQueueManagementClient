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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.fuelqueuemanagement.database.StationOnlineDBHelper;
import com.example.fuelqueuemanagement.database.customerDBHelper;

import java.util.ArrayList;
import java.util.List;

//Manage user login UI
public class LoginActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner usersSpinner;
    Button btnLogin;
    private customerDBHelper DbHelper;
    EditText email, password;
    private String user;
    public static String userType;
    public static String loggedUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin= (Button) findViewById(R.id.login);
        usersSpinner = (Spinner) findViewById(R.id.userSpinner);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);

        //Spinner tutorial resource: https://www.youtube.com/watch?v=zSgrMVt_MFg
        usersSpinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
        List<String> users = new ArrayList<String>();
        users.add("Customer");
        users.add("Station Owner");
        ArrayAdapter<String> dataAdapterType = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, users);
        dataAdapterType.setDropDownViewResource(R.layout.spinner_dropdown_item);
        usersSpinner.setAdapter(dataAdapterType);

        //Login on button click
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //customer login
                if(userType.equals("Customer")){
                    Login();
                }
                //station owner login
                else if (userType.equals("Station Owner")){}
                StationOnlineDBHelper stationOnlineDBHelper = new StationOnlineDBHelper();
                stationOnlineDBHelper.stationOwnerLogin(email.getText().toString(), password.getText().toString(), LoginActivity.this);
            }
        });

    }

        private void Login() {
        //verify user login with sqlite
            if (DbHelper.login(email.getText().toString().trim()
                    , password.getText().toString().trim())) {
                user = "customer";
                Intent accountsIntent = new Intent(LoginActivity.this, MainActivity.class);
                accountsIntent.putExtra("EMAIL", email.getText().toString().trim());
                emptyInputEditText();
                //set logged user type as customer
                loggedUser = "customer";
                startActivity(accountsIntent);
            } else {
                Toast.makeText(LoginActivity.this, "Invalid Username/Password", Toast.LENGTH_SHORT).show();
            }
        }

        //Empty text fields after login
        private void emptyInputEditText() {
            email.setText(null);
            password.setText(null);
        }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // Get user type
        userType = parent.getItemAtPosition(position).toString();
    }
    public void onNothingSelected(AdapterView<?> arg0) {

    }

    }