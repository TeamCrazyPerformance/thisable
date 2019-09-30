package com.tcp.thisable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.tcp.thisable.Dao.Data;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
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

    String type = null;

    ArrayList<Data> listarray = new ArrayList<>();
    ArrayList<Integer> allList = new ArrayList<>();

    ArrayList<ToggleButton> advtoggle = new ArrayList<>();

    boolean data2Flag = true;

    DBHelper dbHelper;

    Data data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DBHelper(getApplicationContext(), "Search.db", null, 1);

        type = getIntent().getStringExtra("type");

        if(type != null && (type.equals("hosp") || type.equals("pharm") || type.equals("wheel"))) {
            data2Flag = false;
        }

        else {
            data2Flag = true;
            search_button_layout = findViewById(R.id.search_button_layout);

            allList.clear();
            allList.addAll(dbHelper.getAllList());

            int fakeind = 1;
            for(int i: allList) {
                ToggleButton toggleButton = new ToggleButton(this);
                toggleButton.setTextOn("맞춤검색" + fakeind);
                toggleButton.setTextOff("맞춤검색" + fakeind);
                toggleButton.setText("맞춤검색" + fakeind);

                LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                layoutParams.rightMargin = 30;
                toggleButton.setMinimumHeight(0);
                toggleButton.setMinHeight(0);
                toggleButton.setBackgroundResource(R.drawable.custom_togglebutton_advsearch);

                //toggleButton.setChecked(false);
                search_button_layout.addView(toggleButton, layoutParams);

                advtoggle.add(toggleButton);

                final int index = fakeind;

                toggleButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int ind = 1;
                        for (ToggleButton toggle : advtoggle) {
                            if (index == ind && toggle.isChecked())
                                toggle.setChecked(true);
                            else
                                toggle.setChecked(false);

                            ind++;
                        }
                    }
                });

                fakeind++;
            }
        }

        PermissionListener permissionListener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                if(mainMapFragment != null)
                    mainMapFragment.updatePermission();
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
        searchEdittext.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int io, KeyEvent keyEvent) {
                if(io == EditorInfo.IME_ACTION_SEARCH){
                    requestData();
                    return true;
                }
                return false;
            }
        });
        searchButton = findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestData();
            }
        });

        mainMapFragment = new MainMapFragment();

        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_frame, mainMapFragment, "MAP").commit();
    }

    public void requestData() {
        InputMethodManager mInputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        mInputMethodManager.hideSoftInputFromWindow(searchEdittext.getWindowToken(), 0);

        double longitude = 126.97;
        double latitude = 37.56;
        if (mainMapFragment != null) {
            if (mainMapFragment.currentLocation != null) {
                longitude = mainMapFragment.currentLocation.longitude;
                latitude = mainMapFragment.currentLocation.latitude;
            }
        }

        if(data2Flag) {
            String query = "";

            list = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

            int i = 1;
            for (ToggleButton toggle : advtoggle) {
                if (toggle.isChecked()) {
                    list = dbHelper.getList(allList.get(i-1));
                }
                i++;
            }

            JSONObject jsonObject = new JSONObject();
            String[] keys = {"mainroad", "parking", "mainflat", "elevator", "toilet", "room", "seat", "ticket", "blind", "deaf", "guide", "wheelchair"};

            try {
                for (int j = 0; j < 12; j++) {
                    if (list[j] == 1) {
                        jsonObject.put(keys[j], true);
                    }
                }

                //Log.d("json", jsonObject.toString());
                query = jsonObject.toString();

            } catch (Exception e) {
                query = "{}";
            }

            Call<ArrayList<Data>> res = NetRetrofit.getInstance().getService().getSearchData(type, longitude, latitude, searchEdittext.getText().toString(), query);
            res.enqueue(new Callback<ArrayList<Data>>() {
                @Override
                public void onResponse(Call<ArrayList<Data>> call, Response<ArrayList<Data>> response) {
                    if(response.isSuccessful()) {
                        if (response.body() != null) {
                            listarray.clear();
                            for (int i = 0; i < response.body().size(); i++) {
                                data = response.body().get(i);
                                listarray.add(data);
                            }

                            mainMapFragment = (MainMapFragment) getSupportFragmentManager().findFragmentByTag("MAP");
                            mainListFragment = (MainListFragment) getSupportFragmentManager().findFragmentByTag("LIST");


                            if (mainMapFragment != null) mainMapFragment.updateUi(listarray);

                            if (mainListFragment != null) {
                                if (mainMapFragment != null) {
                                    if (mainMapFragment.currentLocation != null) {
                                        mainListFragment.updateUi(listarray, mainMapFragment.currentLocation);
                                    }
                                } else
                                    mainListFragment.updateUi(listarray, null);
                            }
                        }
                    }

                    else {
                        Toast.makeText(getApplicationContext(), "데이터를 가져오지 못했습니다.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<Data>> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "서버가 응답하지 않습니다.", Toast.LENGTH_SHORT).show();
                }
            });
        }

        else {
            Call<ArrayList<Data>> res = NetRetrofit.getInstance().getService().getSearchData2(type, longitude, latitude, searchEdittext.getText().toString());
            res.enqueue(new Callback<ArrayList<Data>>() {
                @Override
                public void onResponse(Call<ArrayList<Data>> call, Response<ArrayList<Data>> response) {
                    //Log.d("Retrofit", response.toString());
                    if(response.isSuccessful()) {
                        if (response.body() != null) {
                            listarray.clear();
                            for (int i = 0; i < response.body().size(); i++) {
                                data = response.body().get(i);
                                listarray.add(data);
                            }

                            mainMapFragment = (MainMapFragment) getSupportFragmentManager().findFragmentByTag("MAP");
                            mainListFragment = (MainListFragment) getSupportFragmentManager().findFragmentByTag("LIST");

                            if (mainMapFragment != null) mainMapFragment.updateUi(listarray);

                            if (mainListFragment != null) {
                                if (mainMapFragment != null) {
                                    if (mainMapFragment.currentLocation != null) {
                                        mainListFragment.updateUi(listarray, mainMapFragment.currentLocation);
                                    }
                                } else
                                    mainListFragment.updateUi(listarray, null);
                            }
                        }
                    }

                    else {
                        Toast.makeText(getApplicationContext(), "데이터를 가져오지 못했습니다.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<Data>> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "서버가 응답하지 않습니다.", Toast.LENGTH_SHORT).show();
                }
            });
        }
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
