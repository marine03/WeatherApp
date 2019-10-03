package com.example.weatherapp.data;

import org.json.JSONObject;

public class Channel implements JSONPopulator {
    private Item item;
    private Unit units;

    public Item getItem() {
        return item;
    }

    public Unit getUnits() {
        return units;
    }

    @Override
    public void populate(JSONObject data) {

        units= new Unit();
        units.populate(data);

        item = new Item();
        item.populate(data);

    }
}
