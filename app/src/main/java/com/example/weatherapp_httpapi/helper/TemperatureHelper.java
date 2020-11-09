package com.example.weatherapp_httpapi.helper;

public class TemperatureHelper {
    // static variable single_instance of type Singleton
    private static TemperatureHelper single_instance = null;


    // private constructor restricted to this class itself
    private TemperatureHelper()
    {
    }

    // static method to create instance of Singleton class
    public static TemperatureHelper getInstance()
    {
        if (single_instance == null)
            single_instance = new TemperatureHelper();

        return single_instance;
    }

    public  double convertKToCelsius(double kelvinTemp){
        return (kelvinTemp - 273.15);
    }
}
