package com.example.weatherapphttp;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.weatherapphttp.config.AppConstants;
import com.example.weatherapphttp.helper.TemperatureConverter;
import com.example.weatherapphttp.http.HttpTask;
import com.example.weatherapphttp.model.WeatherDetails;
import com.example.weatherapphttp.retofithttp.RetrofitClient;

import java.text.DecimalFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    TextView weatherTitleTv, weatherDescTv, weatherLocTv, weatherTempTv, weatherAPIStatusTv;
    ImageView weatherIconIv;
    LinearLayout weatherDetailsLl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bindUI();

        //Perform task
//        HttpTask requestTask = new HttpTask();
//        requestTask.execute("Kuala Lumpur");
//        requestTask.setResultListener(new ResultListener() {
//            @Override
//            public void onSuccess(WeatherDetails weatherDetails) {
//                weatherLocTv.setText(weatherDetails.getCityName());
//                weatherDescTv.setText(weatherDetails.getDescription());
//                weatherTempTv.setText(weatherDetails.getTemperature());
//                Glide.with(getApplicationContext()).load(weatherDetails.getIconLink()).into(weatherIconIv);
//                weatherAPIStatusTv.setVisibility(View.GONE);
//            }
//
//            @Override
//            public void onFailure() {
//                weatherAPIStatusTv.setVisibility(View.VISIBLE);
//            }
//        });

        RetrofitClient retrofitClient = new RetrofitClient();

        retrofitClient.getWeather("Kuala Lumpur").enqueue(new Callback<WeatherDetails>() {
            @Override
            public void onResponse(Call<WeatherDetails> call, Response<WeatherDetails> response) {

                WeatherDetails weatherDetails = response.body();

                weatherLocTv.setText(weatherDetails.getCityName());
                weatherDescTv.setText(weatherDetails.getWeather().get(0).getDescription());

                DecimalFormat twoDecimals = new DecimalFormat("#.##");
                String tempString = twoDecimals.format(TemperatureConverter.convertKToCelsius(weatherDetails.getMain().getTemp())) + "°C";
                weatherTempTv.setText(tempString);

                weatherDetails.setIconLink(AppConstants.BASE_URL + String.format("img/w/%s.png", weatherDetails.getWeather().get(0).getIcon()));
                Glide.with(getApplicationContext()).load(weatherDetails.getIconLink()).into(weatherIconIv);
                weatherAPIStatusTv.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<WeatherDetails> call, Throwable t) {
                try {
                    weatherAPIStatusTv.setVisibility(View.VISIBLE);
                } catch (Exception e) {

                }
            }
        });
    }

    private void bindUI() {
        weatherTitleTv = findViewById(R.id.weather_title);
        weatherLocTv = findViewById(R.id.weather_loc);
        weatherDescTv = findViewById(R.id.weather_desc);
        weatherTempTv = findViewById(R.id.weather_temp);
        weatherIconIv = findViewById(R.id.weather_icon);
        weatherAPIStatusTv = findViewById(R.id.weather_api_status);
        weatherDetailsLl = findViewById(R.id.weather_details_ll);
    }


}