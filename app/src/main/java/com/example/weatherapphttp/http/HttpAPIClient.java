package com.example.weatherapphttp.http;

import android.os.AsyncTask;
import android.util.Log;

import com.example.weatherapphttp.ResultListener;
import com.example.weatherapphttp.config.AppConstants;
import com.example.weatherapphttp.helper.TemperatureConverter;
import com.example.weatherapphttp.model.WeatherDetails;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;

public class HttpAPIClient {

        // static variable single_instance of type HttpAPIClient
    private static HttpAPIClient single_instance = null;


    // private constructor restricted to this class itself
    private HttpAPIClient()
    {
    }

    // static method to create instance of Singleton class
    public static HttpAPIClient getInstance()
    {
        if (single_instance == null)
            single_instance = new HttpAPIClient();

        return single_instance;
    }

    public static void getWeatherAPI(){
        AsyncTask asyncTask = new AsyncTask<String, Void, String> (){
            ResultListener responseListener;

//            // Assign the listener implementing events interface that will receive the events
            public void setResultListener(ResultListener listener) {
                this.responseListener = listener;
            }

            @Override
            protected String doInBackground(String... params) {
                StringBuilder content = new StringBuilder();

                try {
                    URL url = new URL(AppConstants.BASE_URL + String.format("data/2.5/weather?q=%s&appid=%s", params[0], AppConstants.API_KEY));

                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setDoInput(true);
                    connection.connect();

                    if (connection.getResponseCode() == 200) {
                        InputStream is = connection.getInputStream();
                        BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
                        String line;
                        while ((line = br.readLine()) != null) {
                            content.append(line).append("\n");
                        }

                    }
                    Log.w("httpz", content.toString());
                } catch (Exception e) {
                    Log.w("httpz", e.getMessage());
                }
                return content.toString();
            }

            @Override
            protected void onPostExecute(String result) {

                WeatherDetails weatherDetails = new WeatherDetails();
                try {
                    JSONObject jsonResult = new JSONObject(result);
                    String cityName = jsonResult.getString("name");
                    JSONArray weatherDetailsArray = jsonResult.getJSONArray("weather");
                    JSONObject weatherDetailsObject = weatherDetailsArray.getJSONObject(0);
                    String weatherIcon = weatherDetailsObject.getString("icon");
                    weatherDetails.setCityName(cityName);

                    String weatherMainDesc = weatherDetailsObject.getString("main");
                    weatherDetails.setDescription(weatherMainDesc);

                    JSONObject weatherMainObject = jsonResult.getJSONObject("main");
                    double temp = weatherMainObject.getDouble("temp");
                    DecimalFormat twoDecimals = new DecimalFormat("#.##");
                    String tempString = twoDecimals.format(TemperatureConverter.convertKToCelsius(temp)) + "°C";
                    weatherDetails.setTemperature(tempString);

                    weatherDetails.setIconLink(AppConstants.BASE_URL + String.format("img/w/%s.png", weatherIcon));
                    responseListener.onSuccess(weatherDetails);

                } catch (Exception e) {
                    responseListener.onFailure();
                }

            }
        }.execute();
    }
}
