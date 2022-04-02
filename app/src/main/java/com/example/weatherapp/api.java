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

public class api {
    private float latitude;
    private float longitude;
    private String location;
    private int[][] tempInfo;
    private String[][] descriptionInfo;


    private String openWeatherAPIKey;
    private String geoCodeAPIKey;
    private static HttpURLConnection connection;

    public api() {
        this.openWeatherAPIKey = "fa90c1653b5aa10932b4082764d7d677";
        this.geoCodeAPIKey = "BAQ16YmbralBAnfqBGf4js7p66ADUOQX";

        this.tempInfo = new int[8][2];
        //  formatted like this:
        //  [0] current temp [temp, feels like]
        //  [1] day 1 temp [temp, feels like]
        //  [2] day 2 temp [temp, feels like]
        //  [3] day 3 temp [temp, feels like]
        //  [4] day 4 temp [temp, feels like]
        //  [5] day 5 temp [temp, feels like]
        //  [6] day 6 temp [temp, feels like]
        //  [7] day 7 temp [temp, feels like]



        this.descriptionInfo = new String[8][2];
        //  formatted like this:
        //  [0] current temp [temp, feels like]
        //  [1] day 1 description [main description, sub description]
        //  [2] day 2 description [main description, sub description]
        //  [3] day 3 description [main description, sub description]
        //  [4] day 4 description [main description, sub description]
        //  [5] day 5 description [main description, sub description]
        //  [6] day 6 description [main description, sub description]
        //  [7] day 7 description [main description, sub description]
    }


    public void geoCode(String location) {
        // Method 1: java.net.HttpURLConnection
        BufferedReader reader;
        String line;
        StringBuilder responseContent = new StringBuilder();
        try {
            String strUrl = String.format("http://open.mapquestapi.com/geocoding/v1/address?key=%s&location=%s", this.location, this.geoCodeAPIKey);
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
            float[] result = parseGeoCode(responseContent.toString());
            this.latitude = result[0];
            this.longitude = result[1];


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            connection.disconnect();
        }
    }

    public void openWeather() {
        // Method 1: java.net.HttpURLConnection
        BufferedReader reader;
        String line;
        StringBuilder responseContent = new StringBuilder();
        try {
            String strUrl = String.format("https://api.openweathermap.org/data/2.5/onecall?lat=%s&lon=%s&exclude=hourly,daily&units=metric&appid=%s", this.latitude, this.longitude, this.openWeatherAPIKey);
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
            parseOpenWeather(responseContent.toString());

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            connection.disconnect();
        }
    }


    // parsing the api response
    public float[] parseGeoCode(String responseBody) {
        // Obtaining Geocode api data
        float[] resultant = new float[2];

        try {
            JSONObject obj = new JSONObject(responseBody);
            JSONObject results = obj.getJSONArray("results").getJSONArray(0).getJSONObject(1);
            resultant[0] = results.getInt("lat");
            resultant[1] = results.getInt("lng");

            } catch (JSONException e) {
            e.printStackTrace();
            }

        return resultant;
    }

    public void parseOpenWeather(String responseBody) {
        // Obtaining OpenWeather api data

        try {
            // api call response
            JSONObject obj = new JSONObject(responseBody);


            // current weather & info
            int currentTemp = obj.getJSONObject("current").getInt("temp");
            int currentFeelsLike = obj.getJSONObject("current").getInt("feels_like");
            String currentMain = null;
            String currentDescription = null;
            JSONArray currentWeather = obj.getJSONObject("current").getJSONArray("weather");
            for (int i = 0; i < currentWeather.length(); i++) {
                JSONObject mainObject = currentWeather.getJSONObject(i);
                currentMain = mainObject.getString("main");
                currentDescription = mainObject.getString("description");
            }


            // daily weather & info
            JSONArray objDaily = obj.getJSONArray("daily");
            String dayMain;
            String dayDescription;

            for (int i = 0; i < objDaily.length(); i++) {
                JSONObject day = objDaily.getJSONObject(i);

                // temperature calculations
                JSONObject temp = day.getJSONObject("temp");

                // Calculating avg temperature
                int tempDay = temp.getInt("day");
                int tempNight = temp.getInt("night");
                int tempEve = temp.getInt("eve");
                int tempMorn = temp.getInt("morn");
                int tempAverage = (tempDay + tempNight + tempEve + tempMorn) / 4;

                // feels like calculations
                JSONObject feelsLike = day.getJSONObject("feels_like");

                // Calculating avg feels like
                int feelsLikeDay = feelsLike.getInt("day");
                int feelsLikeNight = feelsLike.getInt("night");
                int feelsLikeEve = feelsLike.getInt("eve");
                int feelsLikeMorn = feelsLike.getInt("morn");
                int feelsLikeAverage = (feelsLikeDay + feelsLikeNight + feelsLikeEve + feelsLikeMorn) / 4;


                JSONObject weather = day.getJSONArray("weather").getJSONObject(0);
                dayMain = weather.getString("main");
                dayDescription = weather.getString("description");

                if (i == 0) {
                    this.tempInfo[0][0] = currentTemp;
                    this.tempInfo[0][1] = currentFeelsLike;

                    this.descriptionInfo[0][0] = currentMain;
                    this.descriptionInfo[0][1] = currentDescription;
                } else {
                    this.tempInfo[i][0] = tempAverage;
                    this.tempInfo[i][1] = feelsLikeAverage;

                    this.descriptionInfo[i][0] = dayMain;
                    this.descriptionInfo[i][1] = dayDescription;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



    // getters
    public float getLatitude() { return this.latitude; }
    public float getLongitude() { return this.longitude; }
    public int[][] getTempInfo() { return this.tempInfo; }
    public String[][] getDescriptionInfo() { return this.descriptionInfo; }
    public String getLocation() { return this.location; }

}
