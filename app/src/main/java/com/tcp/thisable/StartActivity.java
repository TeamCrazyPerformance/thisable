package com.tcp.thisable;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        TextView mytext = (TextView) findViewById(R.id.text1);

        Animation anim=new AlphaAnimation(0.f, 0.9f);
        anim.setDuration(2000);
        anim.setStartOffset(0);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);
        mytext.startAnimation(anim);

    }

    //터치후 화면이동
    public boolean onTouchEvent(MotionEvent event){
        SharedPreferences shared = getApplication().getSharedPreferences("MYPREFRENCE",  Activity.MODE_PRIVATE);
        int action = event.getAction();

        if(action == MotionEvent.ACTION_DOWN){
            if(shared.getString("userid",null)==null) {
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                finish();

            }

            else{
                Intent intent = new Intent(getApplicationContext(),IndexActivity.class);
                startActivity(intent);
                finish();
            }

        }


        return super.onTouchEvent(event);
    }
}
