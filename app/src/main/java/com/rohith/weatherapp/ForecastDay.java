package com.rohith.weatherapp;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.rohith.weatherapp.Forecast.Forecastday;

public class ForecastDay {

    @SerializedName("forecastday")
    @Expose
    private List<Forecastday> forecastday;

    public List<Forecastday> getForecastday() {
        return forecastday;
    }

    public void setForecastday(List<Forecastday> forecastday) {
        this.forecastday = forecastday;
    }

}