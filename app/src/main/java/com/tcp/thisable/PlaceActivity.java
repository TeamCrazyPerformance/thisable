package com.tcp.thisable;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.tcp.thisable.Dao.Review;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlaceActivity extends AppCompatActivity {

    TextView place_name2;
    TextView address;
    TextView tel;
    TextView homepage;
    RatingBar place_rating;
    ListView place_review;
    int uniqueid;
    String type;
    CheckBox checkBox[] = new CheckBox[12];
    Review review = new Review();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);
        Intent intent = getIntent();
        Bundle data = intent.getExtras();
        place_name2 = findViewById(R.id.place_name2);
        address = findViewById(R.id.address);
        place_rating = findViewById(R.id.place_rating);
        place_review = findViewById(R.id.place_review);
        tel = findViewById(R.id.tel);
        homepage = findViewById(R.id.homepage);
        uniqueid = data.getInt("uniqueid");
        type = data.getString("type");


        place_name2.setText(data.getString("name"));
        address.setText(data.getString("address"));
        tel.setText(data.getString("tel"));
        homepage.setText(data.getString("homepage"));



        (checkBox[0] = findViewById(R.id.p1)).setChecked(data.getBoolean("mainroad"));
        (checkBox[1] = findViewById(R.id.p2)).setChecked(data.getBoolean("parking"));
        (checkBox[2] = findViewById(R.id.p3)).setChecked(data.getBoolean("mainflat"));
        (checkBox[3] = findViewById(R.id.p4)).setChecked(data.getBoolean("elevator"));
        (checkBox[4] = findViewById(R.id.p5)).setChecked(data.getBoolean("toilet"));
        (checkBox[5] = findViewById(R.id.p6)).setChecked(data.getBoolean("room"));
        (checkBox[6] = findViewById(R.id.p7)).setChecked(data.getBoolean("seat"));
        (checkBox[7] = findViewById(R.id.p8)).setChecked(data.getBoolean("ticket"));
        (checkBox[8] = findViewById(R.id.p9)).setChecked(data.getBoolean("blind"));
        (checkBox[9] = findViewById(R.id.p10)).setChecked(data.getBoolean("deaf"));
        (checkBox[10] = findViewById(R.id.p11)).setChecked(data.getBoolean("guide"));
        (checkBox[11] = findViewById(R.id.p2)).setChecked(data.getBoolean("wheelchair"));


        Call<ArrayList<Review>> res = NetRetrofit.getInstance().getService().getReviewList(type,uniqueid);
        res.enqueue(new Callback<ArrayList<Review>>() {
            @Override
            public void onResponse(Call<ArrayList<Review>> call, Response<ArrayList<Review>> response) {
                Log.d("Retrofit", response.toString());
                ArrayList<Review> reviewarray = new ArrayList<>();
                if (response.body() != null) {
                    for (int i = 0; i < response.body().size(); i++) {
                        review = response.body().get(i);
                        review.name = review.username;
                        reviewarray.add(review);
                    }
                }
                ListViewAdapter adapter = new ListViewAdapter(reviewarray);
                place_review.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<ArrayList<Review>> call, Throwable t) {
                Log.d("Retrofit", t.toString());
            }
        });

    }
}