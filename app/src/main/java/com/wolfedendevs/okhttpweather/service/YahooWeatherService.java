package com.wolfedendevs.okhttpweather.service;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.wolfedendevs.okhttpweather.data.Channel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.content.ContentValues.TAG;

/**
 * Created by Douglas on 10/12/2017.
 */

public class YahooWeatherService {
    private WeatherServiceCallback callback;
    private String location;
    private char units;
    private static Response response;

    public YahooWeatherService(WeatherServiceCallback callback) {
        this.callback = callback;
    }

    public String getLocation() {
        return location;
    }

    public void refreshWeather(String l, char u) {
        this.location = l;
        this.units = u;

        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... strings) {
                String YQL = String.format("select * from weather.forecast where woeid in (select woeid from geo.places(1) where text=\"%s\") and u='" + units + "'", strings[0]);
                String endpoint = String.format("https://query.yahooapis.com/v1/public/yql?q=%s&format=json", Uri.encode(YQL));

                return getDataFromWeb(endpoint).toString();
            }

            @Override
            protected void onPostExecute(String s) {
                try {
                    JSONObject data = new JSONObject(s);

                    if (data != null) {
                        if (data.length() > 0) {
                            JSONObject queryResults = data.getJSONObject("query");
                            int count = queryResults.getInt("count");
                            if (count == 0) {
                                callback.serviceFailure(new LocationWeatherException("No weather information found for " + location));
                                return;
                            }

                            Channel channel = new Channel();
                            channel.populate(queryResults.optJSONObject("results").optJSONObject("channel"));
                            callback.serviceSuccess(channel);
                        }
                    }else {

                    }
                } catch(JSONException e){
                    callback.serviceFailure(e);
                }
            }
        }.execute(location);
    }

    public static String getDataFromWeb(String url) {
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(url)
                    .build();
            response = client.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            Log.e(TAG, "" + e.getLocalizedMessage());
        }
        return null;
    }

    public class LocationWeatherException extends Exception {
        public LocationWeatherException(String message) {
            super(message);
        }
    }
}
