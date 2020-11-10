package com.example.weatherapphttp;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.weatherapphttp.http.HttpResponseListener;
import com.example.weatherapphttp.model.WeatherDetails;

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
        HttpResponseListener requestTask = new HttpResponseListener();
        requestTask.execute("Kuala Lumpur");
        requestTask.setResultListener(new ResultListener() {
            @Override
            public void onSuccess(WeatherDetails weatherDetails) {
                weatherLocTv.setText(weatherDetails.getCityName());
                weatherDescTv.setText(weatherDetails.getDescription());
                weatherTempTv.setText(weatherDetails.getTemperature());
                Glide.with(getApplicationContext()).load(weatherDetails.getIconLink()).into(weatherIconIv);
                weatherAPIStatusTv.setVisibility(View.GONE);
            }

            @Override
            public void onFailure() {
                weatherAPIStatusTv.setVisibility(View.VISIBLE);
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