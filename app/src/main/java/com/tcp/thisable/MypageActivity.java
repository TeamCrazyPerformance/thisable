package com.tcp.thisable;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.tcp.thisable.Dao.Review;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MypageActivity extends AppCompatActivity {
    ListAdapter adapter;
    RecyclerView listView;
    LinearLayout searchlayout;
    Review review;
    ArrayList<Review> listarray = new ArrayList<>();
    String userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);

        final DBHelper dbHelper = new DBHelper(getApplicationContext(), "Search.db", null, 1);
        searchlayout = findViewById(R.id.searchlayout);
        listView = findViewById(R.id.review_list);

        listView.setLayoutManager(new LinearLayoutManager(this));
        SharedPreferences shared = getSharedPreferences("MYPREFRENCE",  MODE_PRIVATE);
        userid = shared.getString("userid",null);

        int size = dbHelper.getsize();
        for (int i = 1; i < size + 2; i++) {

            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(80, 20, 80, 20);

            Button button = new Button(this);
            button.setBackgroundResource(R.drawable.round_button);
            button.setBackgroundColor(Color.parseColor("#ffd633"));
            button.setLayoutParams(params);
            button.setTextColor(Color.WHITE);

            if (i == size + 1) {
                button.setText("+");
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent2 = new Intent(getApplicationContext(), SearchActivity.class);
                        intent2.putExtra("index", -1);
                        startActivity(intent2);
                        finish();

                    }
                });
            } else {
                button.setText("맞춤조건" + i);
                final int finalI = i;
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent2 = new Intent(getApplicationContext(), SearchActivity.class);
                        intent2.putExtra("index", finalI);
                        startActivity(intent2);
                        finish();

                    }
                });
            }

            searchlayout.addView(button);

        }


        Call<ArrayList<Review>> res = NetRetrofit.getInstance().getService().getListRepos(userid);
        res.enqueue(new Callback<ArrayList<Review>>() {
            @Override
            public void onResponse(Call<ArrayList<Review>> call, Response<ArrayList<Review>> response) {
                Log.d("Retrofit", response.toString());
                if (response.body() != null) {

                    //listarray.addAll(response.body());
                    for (int i = 0; i < response.body().size(); i++) {
                        review = response.body().get(i);
                        review.userid = userid;
                        listarray.add(review);
                    }
                    adapter = new ListAdapter(listarray, 0, userid);
                    listView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Review>> call, Throwable t) {
                Log.d("Retrofit", t.toString());
            }
        });

    }

}

