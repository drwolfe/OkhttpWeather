package com.wolfedendevs.okhttpweather;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wolfedendevs.okhttpweather.data.Channel;
import com.wolfedendevs.okhttpweather.data.Forecast;
import com.wolfedendevs.okhttpweather.data.Item;
import com.wolfedendevs.okhttpweather.service.WeatherServiceCallback;
import com.wolfedendevs.okhttpweather.service.YahooWeatherService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Douglas on 11/16/2017.
 */

public class Weather extends AppCompatActivity implements WeatherServiceCallback {

    private String location;
    private YahooWeatherService service;

    private TextView locationTextView;
    private ImageView weatherIconImageView;
    private TextView temperatureTextView;
    private TextView conditionTextView;
    private TextView dayOneDay;
    private TextView dayTwoDay;
    private TextView dayThreeDay;
    private TextView dayFourDay;
    private TextView dayFiveDay;
    private ImageView dayOneImage;
    private ImageView dayTwoImage;
    private ImageView dayThreeImage;
    private ImageView dayFourImage;
    private ImageView dayFiveImage;
    private TextView dayOneHigh;
    private TextView dayTwoHigh;
    private TextView dayThreeHigh;
    private TextView dayFourHigh;
    private TextView dayFiveHigh;
    private TextView dayOneLow;
    private TextView dayTwoLow;
    private TextView dayThreeLow;
    private TextView dayFourLow;
    private TextView dayFiveLow;

    private Button otherCityButton;
    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather);
        location = getIntent().getStringExtra("Location");

        locationTextView = (TextView) findViewById(R.id.locationTextView);
        weatherIconImageView = (ImageView) findViewById(R.id.weatherIconImageView);
        temperatureTextView = (TextView) findViewById(R.id.temperatureTextView);
        conditionTextView = (TextView) findViewById(R.id.conditionTextView);
        dayOneDay = (TextView) findViewById(R.id.dayOneDay);
        dayTwoDay = (TextView) findViewById(R.id.dayTwoDay);
        dayThreeDay = (TextView) findViewById(R.id.dayThreeDay);
        dayFourDay = (TextView) findViewById(R.id.dayFourDay);
        dayFiveDay = (TextView) findViewById(R.id.dayFiveDay);
        dayOneImage = (ImageView) findViewById(R.id.dayOneImage);
        dayTwoImage = (ImageView) findViewById(R.id.dayTwoImage);
        dayThreeImage = (ImageView) findViewById(R.id.dayThreeImage);
        dayFourImage = (ImageView) findViewById(R.id.dayFourImage);
        dayFiveImage = (ImageView) findViewById(R.id.dayFiveImage);
        dayOneHigh = (TextView) findViewById(R.id.dayOneHigh);
        dayTwoHigh = (TextView) findViewById(R.id.dayTwoHigh);
        dayThreeHigh = (TextView) findViewById(R.id.dayThreeHigh);
        dayFourHigh = (TextView) findViewById(R.id.dayFourHigh);
        dayFiveHigh = (TextView) findViewById(R.id.dayFiveHigh);
        dayOneLow = (TextView) findViewById(R.id.dayOneLow);
        dayTwoLow = (TextView) findViewById(R.id.dayTwoLow);
        dayThreeLow = (TextView) findViewById(R.id.dayThreeLow);
        dayFourLow = (TextView) findViewById(R.id.dayFourLow);
        dayFiveLow = (TextView) findViewById(R.id.dayFiveLow);

        otherCityButton = (Button) findViewById(R.id.otherCityButton);
        backButton = (Button) findViewById(R.id.backButton);

        setOtherCityButton();
        otherCityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (location) {
                    case "Los Angeles, CA":
                        location = "Charlotte, NC";
                        break;
                    case "Charlotte, NC":
                        location = "Los Angeles, CA";
                        break;
                }
                service.refreshWeather(location, 'f');
                setOtherCityButton();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        service = new YahooWeatherService(this);

        service.refreshWeather(location, 'f');
    }

    private void setOtherCityButton() {
        switch (location) {
            case "Los Angeles, CA":
                otherCityButton.setText("Conditions in Charlotte, NC");
                break;
            case "Charlotte, NC":
                otherCityButton.setText("Conditions in Los Angeles, CA");
                break;
        }
    }

    @Override
    public void serviceSuccess(Channel channel) {
        Item item = channel.getItem();

        int resourceId = getResources().getIdentifier("drawable/icon_" + item.getCondition().getCode(), null, getPackageName());
        @SuppressWarnings("deprecation")
        Drawable weatherIconDrawable = getResources().getDrawable(resourceId);
        weatherIconImageView.setImageDrawable(weatherIconDrawable);

        temperatureTextView.setText(item.getCondition().getTemperature() + "\u00B0" + channel.getUnits().getTemperature());

        conditionTextView.setText(item.getCondition().getDescription());

        locationTextView.setText(service.getLocation());

        List<Forecast> fiveDayForecast = new ArrayList<>(item.getForecast());
        dayOneDay.setText(fiveDayForecast.get(0).getDay());
        dayTwoDay.setText(fiveDayForecast.get(1).getDay());
        dayThreeDay.setText(fiveDayForecast.get(2).getDay());
        dayFourDay.setText(fiveDayForecast.get(3).getDay());
        dayFiveDay.setText(fiveDayForecast.get(4).getDay());

        resourceId = getResources().getIdentifier("drawable/icon_" + fiveDayForecast.get(0).getCode(), null, getPackageName());
        weatherIconDrawable = getResources().getDrawable(resourceId);
        dayOneImage.setImageDrawable(weatherIconDrawable);
        resourceId = getResources().getIdentifier("drawable/icon_" + fiveDayForecast.get(1).getCode(), null, getPackageName());
        weatherIconDrawable = getResources().getDrawable(resourceId);
        dayTwoImage.setImageDrawable(weatherIconDrawable);
        resourceId = getResources().getIdentifier("drawable/icon_" + fiveDayForecast.get(2).getCode(), null, getPackageName());
        weatherIconDrawable = getResources().getDrawable(resourceId);
        dayThreeImage.setImageDrawable(weatherIconDrawable);
        resourceId = getResources().getIdentifier("drawable/icon_" + fiveDayForecast.get(3).getCode(), null, getPackageName());
        weatherIconDrawable = getResources().getDrawable(resourceId);
        dayFourImage.setImageDrawable(weatherIconDrawable);
        resourceId = getResources().getIdentifier("drawable/icon_" + fiveDayForecast.get(4).getCode(), null, getPackageName());
        weatherIconDrawable = getResources().getDrawable(resourceId);
        dayFiveImage.setImageDrawable(weatherIconDrawable);

        dayOneHigh.setText(fiveDayForecast.get(0).getHigh() + "\u00B0" + channel.getUnits().getTemperature());
        dayTwoHigh.setText(fiveDayForecast.get(1).getHigh() + "\u00B0" + channel.getUnits().getTemperature());
        dayThreeHigh.setText(fiveDayForecast.get(2).getHigh() + "\u00B0" + channel.getUnits().getTemperature());
        dayFourHigh.setText(fiveDayForecast.get(3).getHigh() + "\u00B0" + channel.getUnits().getTemperature());
        dayFiveHigh.setText(fiveDayForecast.get(4).getHigh() + "\u00B0" + channel.getUnits().getTemperature());

        dayOneLow.setText(fiveDayForecast.get(0).getLow() + "\u00B0" + channel.getUnits().getTemperature());
        dayTwoLow.setText(fiveDayForecast.get(1).getLow() + "\u00B0" + channel.getUnits().getTemperature());
        dayThreeLow.setText(fiveDayForecast.get(2).getLow() + "\u00B0" + channel.getUnits().getTemperature());
        dayFourLow.setText(fiveDayForecast.get(3).getLow() + "\u00B0" + channel.getUnits().getTemperature());
        dayFiveLow.setText(fiveDayForecast.get(4).getLow() + "\u00B0" + channel.getUnits().getTemperature());

    }

    @Override
    public void serviceFailure(Exception exception) {
        Toast.makeText(this, exception.getMessage(), Toast.LENGTH_LONG).show();
    }

}
