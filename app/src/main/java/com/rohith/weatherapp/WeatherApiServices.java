package com.rohith.weatherapp;


import retrofit2.Call;
import retrofit2.http.GET;

public interface WeatherApiServices {
    @GET("/")
    Call<Result> getWeather();
}
