package com.example.weatherapphttp.retofithttp;

import com.example.weatherapphttp.config.AppConstants;
import com.example.weatherapphttp.model.WeatherDetails;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    RetrofitService service;

    public RetrofitClient(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppConstants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
         service = retrofit.create(RetrofitService.class);
    }

    public Call<WeatherDetails> getWeather(String location){
        return service.getWeather(location, AppConstants.API_KEY);
    }
}
