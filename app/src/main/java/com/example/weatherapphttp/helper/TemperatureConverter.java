package com.example.weatherapphttp.helper;

public class TemperatureConverter {


    public  static double convertKToCelsius(double kelvinTemp){
        return (kelvinTemp - 273.15);
    }
}
