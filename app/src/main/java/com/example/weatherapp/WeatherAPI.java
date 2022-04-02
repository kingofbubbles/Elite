package com.example.weatherapp;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class WeatherAPI {
    private float lat;
    private float lon;
    private String APIKey;
    private static HttpURLConnection connection;


    public WeatherAPI(float lat, float lon) {
        this.lat = lat;
        this.lon = lon;
        this.APIKey = "fa90c1653b5aa10932b4082764d7d677";
    }

    public void startAPI() {
        // Method 1: java.net.HttpURLConnection
        BufferedReader reader;
        String line;
        StringBuilder responseContent = new StringBuilder();
        try {
            String strUrl = String.format("https://api.openweathermap.org/data/2.5/onecall?lat=%s&lon=%s&exclude=hourly,daily&units=metric&appid=%s", this.lat, this.lon, this.APIKey);
            URL url = new URL(strUrl);
            connection = (HttpURLConnection) url.openConnection();

            // Request setup
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000); // 5 seconds
            connection.setReadTimeout(5000);

            int status = connection.getResponseCode();

            if (status > 299) {
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));

            } else {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            }
            while ((line = reader.readLine()) != null) {
                responseContent.append(line);
            }
            reader.close();


            // System.out.println(responseContent.toString());
            parse(responseContent.toString());

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            connection.disconnect();
        }
    }

    public static String parse(String responseBody) {
        try {
            JSONArray albums = new JSONArray(responseBody);
            for (int i = 0; i < albums.length(); i++) {
                JSONObject album = albums.getJSONObject(i);
                int main = album.getInt("main");
                int description = album.getInt("description");
                int timezone = album.getInt("timezone");
                int current = album.getInt("current");
                System.out.println(main + description + timezone + current);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;

    }



}

