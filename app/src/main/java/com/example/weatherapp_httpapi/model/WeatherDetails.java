package com.example.weatherapp_httpapi.model;

public class WeatherDetails {
    String cityName;
    String description;
    String temperature;
    String iconLink;


//    public WeatherDetails (String cityName,String description,String temperature){
//        this.cityName = cityName;
//        this.description = description;
//        this.temperature = temperature;
//    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getIconLink() {
        return iconLink;
    }

    public void setIconLink(String iconLink) {
        this.iconLink = iconLink;
    }
}
