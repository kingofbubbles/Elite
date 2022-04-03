package com.example.weatherapp;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class searchpage extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.example.weatherapp.MESSAGE";
    private Button favCities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_searchpage);

    }

    public void sendMessage(View view) {
        Intent intent = new Intent(this, weatherpage.class);
        EditText editText = (EditText) findViewById(R.id.cityNameSearchBar);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }


}
