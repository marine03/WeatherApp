package com.example.weatherapp.service;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import com.example.weatherapp.data.Channel;
public class WeatherService {

    private WeatherServiceCallback callback;
    private String location;
    private Exception error;
    private StringBuilder resultWithJson;

    public WeatherService(WeatherServiceCallback callback){
        this.callback = callback;
    }

    public String getLocation(){
        return location;
    }
    public void refreshWeather(final String l){
        this.location = l;

        new AsyncTask<String, Void, String>() {

            @Override
            protected String doInBackground(String... strings) {
               // String YQL = String.format("select * from weather.forcast where woeid in (select woeid from geo.places(1) where text=\'%s\')",strings[0]);
               // String endpoint = String.format("https://social.yahooapis.com/v1/user/abcdef123/profile?format=json", Uri.encode(YQL) );

                String endpoint = String.format("https://api.darksky.net/forecast/c9fb15032388d7de439b1160e643246a/" + l);
                try {
                    URL url = new URL(endpoint);

                    URLConnection connection = url.openConnection();

                    InputStream inputStream = connection.getInputStream();
                    BufferedReader reader =   new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder result = new StringBuilder();
                    String line;
                    while((line = reader.readLine()) != null){
                        result.append(line);
                        setResultWithJson(result);
                    }


                } catch (Exception e) {
                    error = e;
                    //e.printStackTrace();
                }


                return null;
            }
            @Override
            protected void onPostExecute(String s){
                super.onPostExecute(s);

                s= getResultWithJson().toString();

                if(s== null &&error!= null){
                    callback.serviceFailure(error);
                    return;
                }

                try {
                    JSONObject data = new JSONObject(s);

                    String timezone =  data.optString("timezone");
                    String time = data.optJSONObject("currently").optString("time");
                    String summary =  data.optJSONObject("hourly").optString("summary");
                    String lat = data.optString("latitude");
                    String longi = data.optString("latitude");

                    int count = 1 ; //queryResults.optInt("count");

                    Channel channel = new Channel();
                    channel.populate(data);

                    if(count == 0){
                        callback.serviceFailure(new LocationWeatherException("No weather information found for "+ location ));
                        return;


                    } else{
                        callback.serviceSucess(channel);

                    }




                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            class LocationWeatherException extends Exception {
                public LocationWeatherException(String detailMessage) {
                    super(detailMessage);
                }
            }

        }.execute(location);



    }

    public StringBuilder getResultWithJson() {
        return resultWithJson;
    }

    public void setResultWithJson(StringBuilder resultWithJson) {
        this.resultWithJson = resultWithJson;
    }
}
