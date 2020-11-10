package com.example.weatherapp_httpapi.http;

import android.os.AsyncTask;
import android.util.Log;

import com.example.weatherapp_httpapi.ResultListener;
import com.example.weatherapp_httpapi.config.AppConstants;
import com.example.weatherapp_httpapi.helper.TemperatureConverter;
import com.example.weatherapp_httpapi.model.WeatherDetails;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;

//                                         AsyncTask<Params, Progress, Result>
public class HttpResponseListener extends AsyncTask<String, Void, String> {

    private ResultListener responseListener;

    // Assign the listener implementing events interface that will receive the events
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



}