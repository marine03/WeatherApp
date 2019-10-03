package com.example.weatherapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weatherapp.data.Channel;
import com.example.weatherapp.data.Item;
import com.example.weatherapp.service.WeatherService;
import com.example.weatherapp.service.WeatherServiceCallback;

public class MainActivity extends AppCompatActivity implements WeatherServiceCallback {


    private ImageView weatherIconImageView;
    private ImageView ImageView2;
    private TextView temperatureTextView;
    private TextView conditionTextView;
    private TextView locationTextView;

    private WeatherService service;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView2 = (ImageView)findViewById(R.id.imageView2);
        temperatureTextView = (TextView)findViewById(R.id.temperatureTextView);
        conditionTextView = (TextView)findViewById(R.id.conditionTextView);
        locationTextView = (TextView)findViewById(R.id.locationTextView);
        weatherIconImageView =(ImageView)findViewById(R.id.weatherIconImageView);
        service = new WeatherService(this);
        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading...");
        dialog.show();


        //37.8267,-122.4233 Alcatraz Island Ca
        //35.2271,80.8431 Charlotte NC
        //

        service.refreshWeather("37.8267,-122.4233");

    }

  //  @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void serviceSucess(Channel channel) {
        dialog.hide();

        final Item item = channel.getItem();
      //  int resourceId = getResources().getIdentifier("drawable/icon"+ item.getCondition().getCode(),null,getPackageName());

      //  Drawable weatherIconDrawable = getResources().getDrawable(resourceId);

      //  weatherIconImageView.setImageDrawable(weatherIconDrawable);
        temperatureTextView.setText(item.getCondition().getTemperature()+"\u00B0"+"F");
        conditionTextView.setText(item.getCondition().getDescription());
        locationTextView.setText(service.getLocation());
    }

    @Override
    public void serviceFailure(Exception exception) {
        dialog.hide();
        Toast.makeText(this,exception.getMessage(),Toast.LENGTH_LONG ).show();
    }
}
