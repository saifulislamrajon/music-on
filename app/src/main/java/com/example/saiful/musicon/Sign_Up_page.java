package com.example.saiful.musicon;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

public class Sign_Up_page extends AppCompatActivity {
    EditText etName, etEmail, etPass, etConfirmPass;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign__up_page);

        etName = (EditText) findViewById(R.id.etName);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPass = (EditText) findViewById(R.id.etPass);
        etConfirmPass = (EditText) findViewById(R.id.etConfirmPass);
        spinner = (Spinner) findViewById(R.id.spinner);
        String[] gender = {"Male", "Female"};
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, gender);
        spinner.setAdapter(adapter);

    }

    public void registration(View view) {

        String name = etName.getText().toString();
        etName.setText(null);
        String email = etEmail.getText().toString();
        etEmail.setText(null);
        String password = etPass.getText().toString();
        etPass.setText(null);
        String confirmpassword = etConfirmPass.getText().toString();
        etConfirmPass.setText(null);
        String sex = spinner.getSelectedItem().toString();
        if (password.equals(confirmpassword)) {
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = connectivityManager.getActiveNetworkInfo();
            if (info != null && info.isConnected()) {
                String type = "registration";
                BackgroundTask backgroundTask = new BackgroundTask(this);
                backgroundTask.execute(type, name, email, password, sex);
            } else {
                Toast.makeText(getApplicationContext(), "Please make sure your Network Connection is ON", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(getApplicationContext(), "password don't match", Toast.LENGTH_SHORT).show();
        }


    }
}
