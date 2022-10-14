package com.example.fuelqueuemanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.fuelqueuemanagement.database.customerDBHelper;

import java.util.ArrayList;
import java.util.List;

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

        //Set Spinner elements
        usersSpinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
        List<String> users = new ArrayList<String>();
        users.add("Customer");
        users.add("Station Owner");
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapterType = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, users);
        // Drop down layout style - list view with radio button
        dataAdapterType.setDropDownViewResource(R.layout.spinner_dropdown_item);
        // attaching data adapter to spinner
        usersSpinner.setAdapter(dataAdapterType);

        //Login on button click
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Login();
            }
        });

    }

        private void Login() {
            if (DbHelper.login(email.getText().toString().trim()
                    , password.getText().toString().trim())) {
                user = "customer";
                Intent accountsIntent = new Intent(LoginActivity.this, MainActivity.class);
                accountsIntent.putExtra("EMAIL", email.getText().toString().trim());
                emptyInputEditText();
                loggedUser = "customer";
                startActivity(accountsIntent);
            } else {
                Toast.makeText(LoginActivity.this, "Invalid Username/Password", Toast.LENGTH_SHORT).show();
            }
        }

        private void emptyInputEditText() {
            email.setText(null);
            password.setText(null);
        }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        userType = parent.getItemAtPosition(position).toString();
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

    }