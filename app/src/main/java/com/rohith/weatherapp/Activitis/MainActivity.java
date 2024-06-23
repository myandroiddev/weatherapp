package com.rohith.weatherapp.Activitis;

import android.os.Build;
import android.os.Bundle;
import android.os.DropBoxManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.rohith.weatherapp.Adapter.HourlyAdapter;
import com.rohith.weatherapp.Domains.Hourly;
import com.rohith.weatherapp.Forecast.Forecastday;
import com.rohith.weatherapp.ForecastDay;
import com.rohith.weatherapp.Hour;
import com.rohith.weatherapp.R;
import com.rohith.weatherapp.Result;
import com.rohith.weatherapp.RetrofitClient;
import com.rohith.weatherapp.WeatherApiServices;
import com.rohith.weatherapp.WeatherWrapper;
import com.rohith.weatherapp.databinding.ActivityMainBinding;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private TextView temp,chanceOfRain,windSpeed,humidity,airquality,airqualityCondition,visibility,windPressure,wind,humidtypercent,feelslike,uv,tempConditions,welcomeText,date;
    private RecyclerView.Adapter adapterHourly;
    private RecyclerView recyclerView;
    private ImageView weatherImg;
    private ProgressBar progressBar;
    private ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.view1);
        temp = findViewById(R.id.temp);
        welcomeText = findViewById(R.id.welcomeText);
        scrollView = findViewById(R.id.scrollView2);
        tempConditions = findViewById(R.id.tempconditions);
        weatherImg = findViewById(R.id.weatherimg);
        chanceOfRain = findViewById(R.id.chanceOfRain);
        windSpeed = findViewById(R.id.windSpeed);
        humidity = findViewById(R.id.humidity);
        airquality = findViewById(R.id.airquality);
        airqualityCondition = findViewById(R.id.airqualitycondition);
        uv = findViewById(R.id.uv);
        feelslike = findViewById(R.id.feelslike);
        humidtypercent = findViewById(R.id.humidity);
        wind = findViewById(R.id.wind);
        progressBar = findViewById(R.id.progress_circular);
        windPressure = findViewById(R.id.windpressure);
        visibility = findViewById(R.id.visibility);
        date = findViewById(R.id.date);


        WeatherApiServices apiService = RetrofitClient.getWeatherApiServices();
        progressBar.setVisibility(View.VISIBLE);
        welcomeText.setVisibility(View.VISIBLE);

        Call<Result> call = apiService.getWeather();
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                progressBar.setVisibility(View.GONE);
                welcomeText.setVisibility(View.GONE);
                scrollView.setVisibility(View.VISIBLE);
                if (response.isSuccessful() && response.body() != null) {
                    Result result = response.body();
                    WeatherWrapper weatherWrapper = (WeatherWrapper) result.getWeather();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        LocalDateTime currentDateTime = LocalDateTime.now();
                    }

                    // Get just the hour from LocalDateTime
                    LocalDateTime currentDateTime = null;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        currentDateTime = LocalDateTime.now();
                    }

                    // Define the desired format
                    DateTimeFormatter formatter = null;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:00");
                    }

                    // Format the date-time using the formatter
                    String formattedDateTime = "";
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        formattedDateTime = currentDateTime.format(formatter);
                    }

                    HashMap<Integer,Integer> airQuality = new HashMap<>();
                    airQuality.put(0,11);
                    airQuality.put(12,23);
                    airQuality.put(24,35);
                    airQuality.put(36,41);
                    airQuality.put(42,47);
                    airQuality.put(48,53);
                    airQuality.put(54,58);
                    airQuality.put(59,64);
                    airQuality.put(60,70);
                    airQuality.put(70,200);
                    List<Map.Entry<Integer, Integer>> entryList = new ArrayList<>(airQuality.entrySet());


                    if (weatherWrapper != null && weatherWrapper.getForecastday() != null && !weatherWrapper.getForecastday().isEmpty()) {
                        Forecastday forecastday = weatherWrapper.getForecastday().get(0);
                        List<Hour> hours = forecastday.getHour();
                        int index = 0;
                        for (int i = 0;i < hours.size();i++) {
                            if (formattedDateTime.compareTo(hours.get(i).getTime().toString()) == 0) {
                                index = i;
                                break;
                            }
                        }
                        String url = "https:" + hours.get(index).getCondition().getIcon();
                        Glide.with(MainActivity.this)
                                .load(url)
                                .error(R.drawable.sun) // optional error image
                                .into(weatherImg);
                        tempConditions.setText(hours.get(index).getCondition().getText());
                        double tempC = hours.get(index).getTempC();
                        int intValue = (int) Math.floor(tempC);
                        chanceOfRain.setText(hours.get(index).getChanceOfRain().toString() + "%");
                        temp.setText(String.valueOf(intValue) + "°");
                        windSpeed.setText(hours.get(index).getWindMph().toString() + " mph");
                        humidity.setText(hours.get(index).getHumidity().toString() + "%");

                        Calendar calendar = Calendar.getInstance();
                        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
                        int year = calendar.get(Calendar.YEAR);
                        int month = calendar.get(Calendar.MONTH) + 1;
                        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                        LocalDate currentDate = null;
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                            currentDate = LocalDate.now();
                        }

                        // Define the desired format
                        DateTimeFormatter formatter1 = null;
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                            formatter1 = DateTimeFormatter.ofPattern("EEEE MMMM d", Locale.ENGLISH);
                        }

                        // Format the current date
                        String formattedDate = "";
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                            formattedDate = currentDate.format(formatter1);
                        }
                        date.setText(formattedDate);
                        ArrayList<Hourly> items = new ArrayList<>();

                        // Loop through the hours list
                        for (int i = 0; i < hours.size() && items.size() < 7; i++) {
                            String hour = hours.get(i).getTime();
                            LocalTime time = null;
                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                                time = LocalTime.parse(hour, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                            }

                            // Define the output format (12-hour format with AM/PM)
                            DateTimeFormatter outputFormatter = null;
                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                                outputFormatter = DateTimeFormatter.ofPattern("hh:mm a");
                            }
                            String time12HourFormat = null;
                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                                time12HourFormat = time.format(outputFormatter);
                            }
                            items.add(new Hourly(time12HourFormat, String.valueOf(Math.floor(hours.get(i).getTempC())), "https:" + hours.get(i).getCondition().getIcon()));
                        }

                        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false));
                        adapterHourly = new HourlyAdapter(items);
                        recyclerView.setAdapter(adapterHourly);

                        int airIndex = hours.get(index).getAirQuality().getGbDefraIndex();
                        Map.Entry<Integer, Integer> entry = entryList.get(airIndex-1);
                        Integer key = entry.getKey();
                        Integer value = entry.getValue();
                        int airQualityIndex = key + (int) (Math.random() * (value-key));
                        if (airIndex == 1 || airIndex == 2 || airIndex == 3) {

                            airquality.setText("Good "+airQualityIndex);
                            airqualityCondition.setText("Air quality is excellent for outdoor activities");
                        }
                        else if (airIndex == 4 || airIndex == 5 || airIndex == 6) {
                            airquality.setText("Moderate "+airQualityIndex);
                            airqualityCondition.setText("Air quality is generally good for outdoor activities, but sensitive individuals should take caution.");
                        }
                        else if (airIndex == 7 || airIndex == 8 || airIndex == 9) {
                            airquality.setText("High "+airQualityIndex);
                            airqualityCondition.setText("Air quality is fair, and outdoor activities may be affected for sensitive groups.");
                        }
                        else {
                            airquality.setText("Very High "+airQualityIndex);
                            airqualityCondition.setText("Air quality is poor, and outdoor activities should be limited for everyone");
                        }

                        if (hours.get(index).getUv() < 3) {
                            uv.setText(hours.get(index).getUv().toString() + " very weak");
                        }
                        else if (hours.get(index).getUv() > 2 && hours.get(index).getUv() < 6) {
                            uv.setText(hours.get(index).getUv().toString() + " moderate");
                        }
                        else if (hours.get(index).getUv() > 5 && hours.get(index).getUv() < 8) {
                            uv.setText(hours.get(index).getUv().toString() + " high");
                        }
                        else if (hours.get(index).getUv() > 8 && hours.get(index).getUv() < 11) {
                            uv.setText(hours.get(index).getUv().toString() + " very high");
                        }
                        else {
                            uv.setText(hours.get(index).getUv().toString() + " extreme");
                        }

                        feelslike.setText(hours.get(index).getFeelslikeC().toString() + "°");
                        humidtypercent.setText(hours.get(index).getHumidity().toString() + "%");
                        wind.setText(hours.get(index).getWindMph() + " mph");
                        windPressure.setText(hours.get(index).getPressureMb().toString() + " hpa");
                        visibility.setText(hours.get(index).getVisMiles() + " mi");

                    } else {
                        Log.e("Response Error", "Empty or null forecast data");
                    }
                } else {
                    Log.e("Response Error", "Unsuccessful response: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                Log.e("API Error", "Failed to fetch weather data", t);
            }
        });




    }
}