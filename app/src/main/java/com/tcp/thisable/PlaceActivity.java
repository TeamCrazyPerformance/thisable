package com.tcp.thisable;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.tcp.thisable.Dao.Data;
import com.tcp.thisable.Dao.Review;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlaceActivity extends AppCompatActivity {

    TextView place_name;
    TextView address;
    TextView tel;
    TextView homepage;
    RatingBar place_rating;
    RecyclerView place_review;
    CheckBox checkBox[] = new CheckBox[12];
    Review review = new Review();
    Button button;
    View dialogView;
    EditText edit_review;
    RatingBar edit_rating;
    RetrofitService service;

    ListAdapter adapter;
    ArrayList<Review> reviewArray = new ArrayList<>();

    Data place = new Data();
    String userid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);

        Intent intent = getIntent();
        Bundle data = intent.getExtras();

        place_name = findViewById(R.id.place_name2);
        address = findViewById(R.id.address);
        place_rating = findViewById(R.id.place_rating);
        place_review = findViewById(R.id.place_review);
        button = findViewById(R.id.create);
        tel = findViewById(R.id.tel);
        homepage = findViewById(R.id.homepage);


        place_review.setLayoutManager(new LinearLayoutManager(this));

        SharedPreferences shared = getSharedPreferences("MYPREFRENCE", MODE_PRIVATE);
        userid = shared.getString("userid",null);

        place.uniqueid = data.getInt("uniqueid");
        place.type = data.getString("type");
        place.name = data.getString("name");
        place.address = data.getString("address");
        place.tel = data.getString("tel");
        place.homepage = data.getString("homepage");

        place_name.setText(place.name);
        tel.setText(place.tel);
        homepage.setText(Html.fromHtml("<a href=\"" + place.homepage + "\">" + place.homepage +"</a>"));


        homepage.setMovementMethod(LinkMovementMethod.getInstance());

        place_rating.setStepSize(0.5f);
        place_rating.setRating(data.getFloat("rating_sum")/(float)data.getInt("rating_count"));

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
        (checkBox[11] = findViewById(R.id.p12)).setChecked(data.getBoolean("wheelchair"));

        for(int i =0; i<12;i++) checkBox[i].setClickable(false);

        Call<ArrayList<Review>> res = NetRetrofit.getInstance().getService().getReviewList(place.type,place.uniqueid);
        res.enqueue(new Callback<ArrayList<Review>>() {
            @Override
            public void onResponse(Call<ArrayList<Review>> call, Response<ArrayList<Review>> response) {
                if(response.isSuccessful()) {
                    reviewArray = new ArrayList<>();
                    if (response.body() != null) {
                        for (int i = 0; i < response.body().size(); i++) {
                            review = response.body().get(i);
                            review.name = review.username;
                            reviewArray.add(review);
                        }
                    }
                    adapter = new ListAdapter(reviewArray, 1);
                    place_review.setAdapter(adapter);
                }
                else {
                    Toast.makeText(getApplicationContext(), "리뷰를 가져오지 못했습니다.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Review>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "서버가 응답하지 않습니다.", Toast.LENGTH_SHORT).show();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogView = View.inflate(PlaceActivity.this,R.layout.create_reivew,null);
                AlertDialog.Builder dlg = new AlertDialog.Builder(PlaceActivity.this);
                dlg.setView(dialogView);
                dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        edit_review = dialogView.findViewById(R.id.create_review);
                        edit_rating = dialogView.findViewById(R.id.create_rating);
                        Call<Review> res2 = NetRetrofit.getInstance().getService().writeReview(place.type,place.uniqueid,edit_review.getText().toString(),userid,place.name,edit_rating.getRating());
                        res2.enqueue(new Callback<Review>() {
                            @Override
                            public void onResponse(Call<Review> call, Response<Review> response) {
                                if(response.isSuccessful()) {
                                    //Log.d("err1", response.body().content);
                                    reviewArray.add(0, response.body());
                                    adapter.notifyItemInserted(0);
                                }
                                else {
                                    Toast.makeText(getApplicationContext(), "리뷰가 작성되지 않았습니다.", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Review> call, Throwable t) {
                                Toast.makeText(getApplicationContext(), "서버가 응답하지 않습니다.", Toast.LENGTH_SHORT).show();
                            }
                        });


                    }
                });
                dlg.setNegativeButton("취소",null);
                dlg.create().show();
            }
        });

    }
}
