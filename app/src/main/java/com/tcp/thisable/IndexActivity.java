package com.tcp.thisable;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

public class IndexActivity extends AppCompatActivity {

    Animation ani;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);

        final ImageView mark = (ImageView)findViewById(R.id.mark);
        ani = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.animation);
        mark.startAnimation(ani);

        ImageButton btn = (ImageButton) findViewById(R.id.emergency);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup= new PopupMenu(getApplicationContext(), v);

                getMenuInflater().inflate(R.menu.emergency_menu, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.hospital:
                                Intent hospitalIntent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(hospitalIntent);
                            case R.id.pharmacy:
                                Intent pharmacyIntent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(pharmacyIntent);
                            case R.id.electronic_wheelchair:
                                Intent wheelchairIntent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(wheelchairIntent);
                            default:
                                break;
                        }
                        return false;
                    }
                });

                popup.show();
            }
        });

        ImageButton lodgment_button = (ImageButton) findViewById(R.id.lodgment);
        lodgment_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });

        ImageButton tour_button = (ImageButton) findViewById(R.id.tour);
        tour_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });

        ImageButton restaurant_button = (ImageButton) findViewById(R.id.restaurant);
        restaurant_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });

        ImageButton profile_button = (ImageButton) findViewById(R.id.profile);
        profile_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(),MypageActivity.class);
                startActivity(intent);
            }
        });


        ImageButton convenience_button = (ImageButton) findViewById(R.id.convenience);
        convenience_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
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
