package com.tcp.thisable;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MypageActivity extends AppCompatActivity {

    ListView listView;
    LinearLayout searchlayout;
    String comment;
    ArrayList<ListVO> listarray = new ArrayList<>();


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


        Call<ArrayList<JsonObject>> res = NetRetrofit.getInstance().getService().getListRepos("user");
        res.enqueue(new Callback<ArrayList<JsonObject>>() {
            @Override
            public void onResponse(Call<ArrayList<JsonObject>> call, Response<ArrayList<JsonObject>> response) {
                Log.d("Retrofit", response.toString());
                if (response.body() != null) {

                    for (int i = 0; i < response.body().size(); i++) {
                        comment = response.body().get(i).get("content").toString();
                        ListVO listVO = new ListVO();
                        listVO.place_name = "a";
                        listVO.rating = (float) 3;
                        listVO.comment = comment;

                        listarray.add(listVO);

                    }
                }
                ListViewAdapter adapter = new ListViewAdapter(listarray);
                listView.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<ArrayList<JsonObject>> call, Throwable t) {

            }
        });

    }
}

