package com.tcp.thisable;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.tcp.thisable.Dao.Review;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MypageActivity extends AppCompatActivity {

    ListView listView;
    LinearLayout searchlayout;
    Review review;
    ArrayList<Review> listarray = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);
        final DBHelper dbHelper = new DBHelper(getApplicationContext(), "Search.db", null, 1);
        searchlayout = findViewById(R.id.searchlayout);
        listView = findViewById(R.id.review_list);
        int size = dbHelper.getsize();
        Toast.makeText(getApplicationContext(), Integer.toString(size), Toast.LENGTH_LONG).show();
        for (int i = 1; i < size + 2; i++) {

            Button button = new Button(this);
            button.setBackgroundColor(Color.parseColor("#ffd633"));
            //LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) button.getLayoutParams();
            // params.setMargins(20,20,20,20);
            //button.setLayoutParams(params);
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


        Call<ArrayList<Review>> res = NetRetrofit.getInstance().getService().getListRepos("5d8f29b5c69ca87bf20404f7");
        res.enqueue(new Callback<ArrayList<Review>>() {
            @Override
            public void onResponse(Call<ArrayList<Review>> call, Response<ArrayList<Review>> response) {
                Log.d("Retrofit", response.toString());
                if (response.body() != null) {

                    for (int i = 0; i < response.body().size(); i++) {
                        review = response.body().get(i);
                        listarray.add(review);
                    }
                }
                ListViewAdapter adapter = new ListViewAdapter(listarray);
                listView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<ArrayList<Review>> call, Throwable t) {
                Log.d("Retrofit", t.toString());
            }
        });

    }
}

