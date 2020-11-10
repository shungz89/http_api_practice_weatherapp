package com.example.weatherapphttp;

import com.example.weatherapphttp.model.WeatherDetails;

public interface ResultListener {
      void onSuccess(WeatherDetails weatherDetails);
      void onFailure();
}
