package com.tcp.thisable;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class IndexActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);

        ImageButton lodgment_button = (ImageButton) findViewById(R.id.lodgment);
        lodgment_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(),testActivity.class);
                startActivity(intent);
            }
        });

        ImageButton tour_button = (ImageButton) findViewById(R.id.tour);
        tour_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(),testActivity.class);
                startActivity(intent);
            }
        });

        ImageButton restaurant_button = (ImageButton) findViewById(R.id.restaurant);
        restaurant_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(),testActivity.class);
                startActivity(intent);
            }
        });

        ImageButton profile_button = (ImageButton) findViewById(R.id.profile);
        profile_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(),testActivity.class);
                startActivity(intent);
            }
        });

        ImageButton emergency_button= (ImageButton) findViewById(R.id.emergency);
        profile_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(),testActivity.class);
                startActivity(intent);
            }
        });

        ImageButton convenience_button = (ImageButton) findViewById(R.id.convenience);
        convenience_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(),testActivity.class);
                startActivity(intent);
            }
        });


    }
    private long time= 0;
    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - time >= 2000) {
            time = System.currentTimeMillis();
            Toast.makeText(getApplicationContext(), "뒤로 버튼을 한번 더 누르면 종료합니다.", Toast.LENGTH_SHORT).show();
        } else if (System.currentTimeMillis() - time < 2000) {
            finish();
        }
    }
}
