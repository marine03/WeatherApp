package com.example.weatherapp.data;

import org.json.JSONObject;

public class Unit implements JSONPopulator {


    private String temperature;


    public String getTemperature() {
        return temperature;
    }

    @Override
    public void populate(JSONObject data) {
        temperature = data.optJSONObject("currently").optString("temperature");


    }
}
