package com.rohith.weatherapp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {
    @SerializedName("weather")
    @Expose
    private WeatherWrapper weather;

    public WeatherWrapper getWeather() {
        return weather;
    }

    public void setWeather(WeatherWrapper weather) {
        this.weather = weather;
    }
}
