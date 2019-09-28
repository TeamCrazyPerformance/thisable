package com.tcp.thisable;

import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class PlaceActivity extends AppCompatActivity {

    TextView place_name2;
    TextView address;
    RatingBar place_rating;
    int list[] = new int[12];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);



    }
}