package com.rohith.weatherapp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.rohith.weatherapp.Forecast.Forecastday;

import java.util.List;

public class WeatherWrapper {
    @SerializedName("forecastday")
    @Expose
    private List<Forecastday> forecastday = null;

    public List<Forecastday> getForecastday() {
        return forecastday;
    }

    public void setForecastday(List<Forecastday> forecastday) {
        this.forecastday = forecastday;
    }
}
