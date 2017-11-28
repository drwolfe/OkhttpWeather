package com.wolfedendevs.okhttpweather.data;


import org.json.JSONObject;

/**
 * Created by Douglas on 10/12/2017.
 */

public class Units implements JSONPopulater {
    private String temperature;

    public String getTemperature() {
        return temperature;
    }

    @Override
    public void populate(JSONObject data) {
        temperature = data.optString("temperature");
    }
}
