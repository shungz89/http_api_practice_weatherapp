package com.example.weatherapp_httpapi.helper;

public class TemperatureConverter {
    //TODO:Remove this later.
//    // static variable single_instance of type TemperatureConverter
//    private static TemperatureConverter single_instance = null;
//
//
//    // private constructor restricted to this class itself
//    private TemperatureConverter()
//    {
//    }
//
//    // static method to create instance of Singleton class
//    public static TemperatureConverter getInstance()
//    {
//        if (single_instance == null)
//            single_instance = new TemperatureConverter();
//
//        return single_instance;
//    }

    public  static double convertKToCelsius(double kelvinTemp){
        return (kelvinTemp - 273.15);
    }
}
