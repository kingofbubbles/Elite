package com.example.weatherapp;

import android.content.Intent;
import android.os.Bundle;

import android.os.StrictMode;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class weatherpage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_weatherpage);
        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String message = intent.getStringExtra(searchpage.EXTRA_MESSAGE);

        // Capture the layout's TextView and set the string as its text
        TextView textView = findViewById(R.id.cityName);
        textView.setText(message);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);


        Thread thread = new Thread(new Runnable() {
            public void run() {
                try  {
                    api openWeather = new api();
                    openWeather.geoCode(message);
                    openWeather.openWeather();
                    TextView currentTemp = (TextView) findViewById(R.id.temperatureCelsius);
                    currentTemp.setText(openWeather.getTempInfo()[0][0]);

                    TextView currentFeelsLike = (TextView) findViewById(R.id.feelsLike);
                    currentFeelsLike.setText(String.format("Feels Like: %s", openWeather.getTempInfo()[0][1]));


                    TextView currentWeatherDetails = (TextView) findViewById(R.id.weatherDetails);
                    currentWeatherDetails.setText(String.format("Weather Details: %s, %s", openWeather.getDescriptionInfo()[0][0], openWeather.getDescriptionInfo()[0][1]));

                    TextView firstDay = (TextView) findViewById(R.id.firstDay);
                    firstDay.setText(String.format("Temp: %s, Feels Like: %s. Description: %s, %s", openWeather.getTempInfo()[1][0], openWeather.getTempInfo()[1][1], openWeather.getDescriptionInfo()[1][0], openWeather.getDescriptionInfo()[1][1]));

                    TextView secondDay = (TextView) findViewById(R.id.secondDay);
                    secondDay.setText(String.format("Temp: %s, Feels Like: %s. Description: %s, %s", openWeather.getTempInfo()[2][0], openWeather.getTempInfo()[2][1], openWeather.getDescriptionInfo()[2][0], openWeather.getDescriptionInfo()[2][1]));

                    TextView thirdDay = (TextView) findViewById(R.id.thirdDay);
                    thirdDay.setText(String.format("Temp: %s, Feels Like: %s. Description: %s, %s", openWeather.getTempInfo()[3][0], openWeather.getTempInfo()[3][1], openWeather.getDescriptionInfo()[3][0], openWeather.getDescriptionInfo()[3][1]));

                    TextView fourthDay = (TextView) findViewById(R.id.fourthDay);
                    fourthDay.setText(String.format("Temp: %s, Feels Like: %s. Description: %s, %s", openWeather.getTempInfo()[4][0], openWeather.getTempInfo()[4][1], openWeather.getDescriptionInfo()[4][0], openWeather.getDescriptionInfo()[4][1]));

                    TextView fifthDay = (TextView) findViewById(R.id.fifthDay);
                    fifthDay.setText(String.format("Temp: %s, Feels Like: %s. Description: %s, %s", openWeather.getTempInfo()[5][0], openWeather.getTempInfo()[5][1], openWeather.getDescriptionInfo()[5][0], openWeather.getDescriptionInfo()[5][1]));

                    TextView sixthDay = (TextView) findViewById(R.id.sixthDay);
                    sixthDay.setText(String.format("Temp: %s, Feels Like: %s. Description: %s, %s", openWeather.getTempInfo()[6][0], openWeather.getTempInfo()[6][1], openWeather.getDescriptionInfo()[6][0], openWeather.getDescriptionInfo()[6][1]));

                    TextView seventhDay = (TextView) findViewById(R.id.seventhDay);
                    seventhDay.setText(String.format("Temp: %s, Feels Like: %s. Description: %s, %s", openWeather.getTempInfo()[7][0], openWeather.getTempInfo()[7][1], openWeather.getDescriptionInfo()[7][0], openWeather.getDescriptionInfo()[7][1]));

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();

    }
}

