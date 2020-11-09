package com.example.weatherapp_httpapi;

import com.example.weatherapp_httpapi.model.WeatherDetails;

public interface ResultListener {
      void onSuccess(WeatherDetails weatherDetails);
      void onFailure();
}
