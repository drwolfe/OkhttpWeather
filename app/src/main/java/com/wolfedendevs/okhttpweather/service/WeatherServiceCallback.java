package com.wolfedendevs.okhttpweather.service;

import com.wolfedendevs.okhttpweather.data.Channel;

/**
 * Created by Douglas on 10/12/2017.
 */

public interface WeatherServiceCallback {
    void serviceSuccess(Channel channel);
    void serviceFailure(Exception exception);
}
