package com.example.weatherapp.service;

import com.example.weatherapp.data.Channel;

public interface WeatherServiceCallback {
    void serviceSucess(Channel channel);
    void serviceFailure(Exception exception);
}
