package com.wolfedendevs.okhttpweather;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    public Button weatherLA;
    public Button weatherCLT;

    private String location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        weatherLA = (Button) findViewById(R.id.weatherLA);
        weatherCLT = (Button) findViewById(R.id.weatherCLT);

        weatherLA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToWeather(v);
            }
        });

        weatherCLT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToWeather(v);
            }
        });
    }

    private void goToWeather(View v) {

        switch (v.getId()) {
            case R.id.weatherLA:
                location = "Los Angeles, CA";
                break;
            case R.id.weatherCLT:
                location = "Charlotte, NC";
                break;
        }
        Intent intent = new Intent(getApplicationContext(), com.wolfedendevs.okhttpweather.Weather.class);
        intent.putExtra("Location", location);
        startActivity(intent);
    }
}
