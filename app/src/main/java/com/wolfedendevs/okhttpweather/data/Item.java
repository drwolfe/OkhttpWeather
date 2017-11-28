package com.wolfedendevs.okhttpweather.data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Douglas on 10/12/2017.
 */

public class Item implements JSONPopulater {
    private Condition condition;
    private List<Forecast> forecast;

    public Condition getCondition() {
        return condition;
    }

    public List<Forecast> getForecast() {
        return forecast;
    }

    @Override
    public void populate(JSONObject data) {
        condition = new Condition();
        condition.populate(data.optJSONObject("condition"));

        forecast = new ArrayList<>();
        JSONArray fiveDay = data.optJSONArray("forecast");
        try {
            for (int i = 0; i < fiveDay.length(); i++) {
                JSONObject dayForecastJSON = fiveDay.getJSONObject(i);
                Forecast dayForecast = new Forecast();
                dayForecast.populate(dayForecastJSON);
                forecast.add(dayForecast);
            }
        }catch(JSONException e){
            e.printStackTrace();
        }

    }
}
