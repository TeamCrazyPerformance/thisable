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
import android.widget.Toast;

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

    ArrayList<Integer> allList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);

        searchlayout = findViewById(R.id.searchlayout);
        listView = findViewById(R.id.review_list);

        listView.setLayoutManager(new LinearLayoutManager(this));
        SharedPreferences shared = getSharedPreferences("MYPREFRENCE",  MODE_PRIVATE);
        userid = shared.getString("userid",null);


        Call<ArrayList<Review>> res = NetRetrofit.getInstance().getService().getListRepos(userid);
        res.enqueue(new Callback<ArrayList<Review>>() {
            @Override
            public void onResponse(Call<ArrayList<Review>> call, Response<ArrayList<Review>> response) {
                //Log.d("Retrofit", response.toString());
                if(response.isSuccessful()) {
                    if (response.body() != null) {

                        //listarray.addAll(response.body());
                        for (int i = 0; i < response.body().size(); i++) {
                            review = response.body().get(i);
                            review.userid = userid;
                            listarray.add(review);
                        }
                        adapter = new ListAdapter(listarray, 0);
                        listView.setAdapter(adapter);
                    }
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
    }

    @Override
    protected void onResume() {
        super.onResume();

        searchlayout.removeAllViews();
        final DBHelper dbHelper = new DBHelper(getApplicationContext(), "Search.db", null, 1);

        allList.clear();
        allList.addAll(dbHelper.getAllList());

        int ind = 1;
        for(int i: allList) {
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

            button.setText("맞춤조건" + ind);
            final int finalI = i;
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent2 = new Intent(getApplicationContext(), SearchActivity.class);
                    intent2.putExtra("index", finalI);
                    startActivity(intent2);

                }
            });

            searchlayout.addView(button);

            ind++;
        }

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

        button.setText("+");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(getApplicationContext(), SearchActivity.class);
                intent2.putExtra("index", -1);
                startActivity(intent2);

            }
        });

        searchlayout.addView(button);
    }
}

