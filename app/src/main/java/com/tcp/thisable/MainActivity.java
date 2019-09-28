package com.tcp.thisable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ToggleButton;

import com.google.android.gms.maps.SupportMapFragment;
import com.google.gson.JsonArray;
import com.tcp.thisable.Dao.Data;
import com.tcp.thisable.Dao.Review;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    ToggleButton toggleButton;
    MainMapFragment mainMapFragment;
    MainListFragment mainListFragment;
    LinearLayout search_button_layout;

    Button searchButton;
    EditText searchEdittext;

    int search_size;
    int list[];

    ArrayList<Data> listarray = new ArrayList<>();

    Data data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final DBHelper dbHelper = new DBHelper(getApplicationContext(),"Search.db", null,1);
        search_size = dbHelper.getsize();
        search_button_layout = findViewById(R.id.search_button_layout);

        for(int i=1; i<search_size+1; i++){
            Button button = new Button(this);
            button.setText("맞춤검색"+i);
            final int index = i;
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    list = dbHelper.getList(index);
                }
            });

            search_button_layout.addView(button);

        }



        toggleButton = findViewById(R.id.toggleButton_main);

        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(!b)
                    setFragment(0);
                else
                    setFragment(1);
            }
        });

        searchEdittext = findViewById(R.id.search_editText);
        searchButton = findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Call<ArrayList<Data>> res = NetRetrofit.getInstance().getService().getSearchData("hotel", 127f, 30f, searchEdittext.getText().toString(), "{}");
                res.enqueue(new Callback<ArrayList<Data>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Data>> call, Response<ArrayList<Data>> response) {
                        Log.d("Retrofit", response.toString());
                        if (response.body() != null) {
                            listarray.clear();
                            for (int i = 0; i < response.body().size(); i++) {
                                data = response.body().get(i);
                                listarray.add(data);
                            }

                            mainMapFragment = (MainMapFragment) getSupportFragmentManager().findFragmentByTag("MAP");
                            mainListFragment = (MainListFragment) getSupportFragmentManager().findFragmentByTag("LIST");

                            if(mainMapFragment != null) mainMapFragment.updateUi(listarray);
                            if(mainListFragment != null) mainListFragment.updateUi(listarray);
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<Data>> call, Throwable t) {
                        Log.d("Retrofit", t.toString());
                    }
                });
            }
        });

        mainMapFragment = new MainMapFragment();

        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_frame, mainMapFragment, "MAP").commit();
    }

    public void setFragment(int n) {
        fragmentManager = getSupportFragmentManager();

        if(n == 0) {
            if(mainMapFragment == null) {
                mainMapFragment = new MainMapFragment();
                fragmentManager.beginTransaction().add(R.id.fragment_frame, mainMapFragment, "MAP").commit();
                mainMapFragment.updateUi(listarray);
            }

            if(mainMapFragment != null) fragmentManager.beginTransaction().show(mainMapFragment).commit();
            if(mainListFragment != null) fragmentManager.beginTransaction().hide(mainListFragment).commit();
        }
        else if(n == 1) {
            if(mainListFragment == null) {
                mainListFragment = new MainListFragment();
                fragmentManager.beginTransaction().add(R.id.fragment_frame, mainListFragment, "LIST").commit();
                mainListFragment.updateUi(listarray);
            }

            if(mainMapFragment != null) fragmentManager.beginTransaction().hide(mainMapFragment).commit();
            if(mainListFragment != null) fragmentManager.beginTransaction().show(mainListFragment).commit();
        }
    }
}
