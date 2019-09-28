package com.tcp.thisable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ToggleButton;

import com.google.android.gms.maps.SupportMapFragment;

public class MainActivity extends AppCompatActivity {
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    ToggleButton toggleButton;
    MainMapFragment mainMapFragment;
    MainListFragment mainListFragment;
    LinearLayout search_button_layout;
    int search_size;
    int list[];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final DBHelper dbHelper = new DBHelper(getApplicationContext(),"Search.db", null,1);
        search_size = dbHelper.getsize();
        mainMapFragment = new MainMapFragment();
        mainListFragment = new MainListFragment();
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

        setFragment(0);
    }

    public void setFragment(int n) {
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        if (n == 0)
            fragmentTransaction.replace(R.id.fragment_frame, mainMapFragment);
        else if (n == 1)
            fragmentTransaction.replace(R.id.fragment_frame, mainListFragment);

        fragmentTransaction.commit();
    }
}
