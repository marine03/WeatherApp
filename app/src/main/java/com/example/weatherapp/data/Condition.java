package com.example.weatherapp.data;

import org.json.JSONObject;

public class Condition implements JSONPopulator {


    private String code;
    private int temperature;
    private String description;

    public String getCode() {
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
        code = data.optJSONObject("currently").optString("icon");
        temperature = data.optJSONObject("currently").optInt("temperature");
        description = data.optJSONObject("hourly").optString("summary");

    }
}
