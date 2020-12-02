package com.example.weatherapphttp.retofithttp;

import com.example.weatherapphttp.config.AppConstants;
import com.example.weatherapphttp.model.WeatherDetails;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetrofitService {
    @GET("data/2.5/weather")
    Call<WeatherDetails> getWeather(@Query("q") String location, @Query("appid") String AppId);
}
