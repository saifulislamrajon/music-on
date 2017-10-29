package com.example.saiful.musicon;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText name, password;
    //    RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name = (EditText) findViewById(R.id.name);
        password = (EditText) findViewById(R.id.password);
//        radioGroup= (RadioGroup) findViewById(R.id.radioGroup);
    }

    public void forgotPassword(View view) {
        Toast.makeText(getApplicationContext(), "forgot password", Toast.LENGTH_SHORT).show();
    }

    public void logIn(View view) {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        if (info != null && info.isConnected()) {
            String namee = name.getText().toString();

//            SharedPreferences
            SharedPreferences sharedPreferences = getSharedPreferences("fileName", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("name", namee);
            editor.commit();

            name.setText(null);
            String passwordd = password.getText().toString();
            password.setText(null);
            String type = "login";
            BackgroundTask backgroundTask = new BackgroundTask(this);
            backgroundTask.execute(type, namee, passwordd);
        } else {
            Toast.makeText(getApplicationContext(), "Please make sure your Network Connection is ON", Toast.LENGTH_SHORT).show();
        }

    }

    public void signUp(View view) {
        Intent intent = new Intent(this, Sign_Up_page.class);
        startActivity(intent);
    }

}
