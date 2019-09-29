package com.tcp.thisable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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

                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);

                        switch (item.getItemId()){
                            case R.id.hospital:
                                intent.putExtra("type", "hosp");
                                break;
                            case R.id.pharmacy:
                                intent.putExtra("type", "pharm");
                                break;
                            case R.id.electronic_wheelchair:
                                intent.putExtra("type", "wheel");
                                break;
                            default:
                                break;
                        }
                        startActivity(intent);

                        return false;
                    }
                });

                popup.show();
            }
        });

        ConstraintLayout lodgment_button = (ConstraintLayout) findViewById(R.id.hotel);
        lodgment_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                intent.putExtra("type", "hotel");
                startActivity(intent);
            }
        });

        ConstraintLayout tour_button = (ConstraintLayout) findViewById(R.id.tour);
        tour_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                intent.putExtra("type", "tour");
                startActivity(intent);
            }
        });

        ConstraintLayout restaurant_button = (ConstraintLayout) findViewById(R.id.restaurant);
        restaurant_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                intent.putExtra("type", "food");
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

        ImageButton logout_button = findViewById(R.id.logout);
        logout_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder temp = new AlertDialog.Builder(IndexActivity.this);
                temp.setMessage("로그아웃 하시겠습니까?");
                temp.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        SharedPreferences shared = getApplication().getSharedPreferences("MYPREFRENCE",  Activity.MODE_PRIVATE);
                        SharedPreferences.Editor editor = shared.edit();
                        editor.remove("userid");
                        editor.commit();
                        finish(); }});
                temp.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                // 취소시 처리 로직
                                Toast.makeText(getApplicationContext(), "취소하였습니다.", Toast.LENGTH_SHORT).show();
                            }});
                AlertDialog alert = temp.create();
                alert.show();
            }
        });


        ConstraintLayout convenience_button = (ConstraintLayout) findViewById(R.id.convinent);
        convenience_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                intent.putExtra("type", "conv");
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
