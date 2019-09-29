package com.tcp.thisable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.JsonArray;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.tcp.thisable.Dao.Data;
import com.tcp.thisable.Dao.Review;

import java.util.ArrayList;
import java.util.List;

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
    int[] list;

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

        PermissionListener permissionListener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {

            }
        };

        TedPermission.with(this)
                .setPermissionListener(permissionListener)
                .setRationaleMessage("현재 위치를 알기 위해서는 접근 권한이 필요합니다.")
                .setDeniedMessage("[설정] -> [권한]에서 권한을 허용할 수 있습니다.")
                .setPermissions(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
                .check();

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
                double longitude = 126.97;
                double latitude = 37.56;
                if(mainMapFragment != null) {
                    if(mainMapFragment.currentLocation != null) {
                        longitude = mainMapFragment.currentLocation.longitude;
                        latitude = mainMapFragment.currentLocation.latitude;
                    }
                }

                Call<ArrayList<Data>> res = NetRetrofit.getInstance().getService().getSearchData("hotel", longitude, latitude, searchEdittext.getText().toString(), "{}");
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

                            if(mainListFragment != null) {
                                if(mainMapFragment != null) {
                                    if(mainMapFragment.currentLocation != null) {
                                        mainListFragment.updateUi(listarray, mainMapFragment.currentLocation);
                                    }
                                }

                                else
                                    mainListFragment.updateUi(listarray, null);
                            }
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

                if(mainMapFragment != null && mainMapFragment.currentLocation != null) {
                    Bundle bundle = new Bundle();
                    bundle.putDouble("longitude", mainMapFragment.currentLocation.longitude);
                    bundle.putDouble("latitude", mainMapFragment.currentLocation.latitude);
                    mainListFragment.setArguments(bundle);
                }

                fragmentManager.beginTransaction().add(R.id.fragment_frame, mainListFragment, "LIST").commit();

                if(mainMapFragment != null && mainMapFragment.currentLocation != null) {
                    mainListFragment.updateUi(listarray, mainMapFragment.currentLocation);
                }
                else {
                    mainListFragment.updateUi(listarray, null);
                }
            }

            if(mainMapFragment != null) fragmentManager.beginTransaction().hide(mainMapFragment).commit();
            if(mainListFragment != null) fragmentManager.beginTransaction().show(mainListFragment).commit();
        }
    }
}
