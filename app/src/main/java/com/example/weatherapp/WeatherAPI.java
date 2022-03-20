package com.example.weatherapp;

import java.io.BufferedInputStream;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;
import org.json.*;

public class WeatherAPI {
    private float lat;
    private float lon;
    private String APIKey;


    public WeatherAPI(float lat, float lon) {
        this.lat = lat;
        this.lon = lon;
        this.APIKey = "fa90c1653b5aa10932b4082764d7d677";
    }

    public void startAPI() throws MalformedURLException, IOException{
        String url = String.format("https://api.openweathermap.org/data/2.5/onecall?lat=%s&lon=%s&exclude=hourly,daily&appid=%s", this.lat, this.lon, this.APIKey);
        HttpURLConnection connection = (HttpURLConnection) ((new URL(url).openConnection()));
        connection.setRequestMethod("GET");
        connection.connect();

        String inline = "";
        // figure out how to write the returned info into the string and then return to json
        // or write it directly into the json file




        connection.disconnect();
    }



}

