package com.wolfedendevs.okhttpweather.data;

import org.json.JSONObject;

/**
 * Created by Douglas on 10/12/2017.
 */

public class Condition implements JSONPopulater {
    private int code;
    private int temperature;
    private String description;

    public int getCode() {
        return code;
    }

    public int getTemperature() {
        return temperature;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public void populate(JSONObject data) {
        code = data.optInt("code");
        temperature = data.optInt("temp");
        description = data.optString("text");
    }
}
