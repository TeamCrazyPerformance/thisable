package com.tcp.thisable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import com.google.android.gms.maps.SupportMapFragment;

public class MainActivity extends AppCompatActivity {
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    ToggleButton toggleButton;

    MainMapFragment mainMapFragment;
    MainListFragment mainListFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainMapFragment = new MainMapFragment();
        mainListFragment = new MainListFragment();

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
