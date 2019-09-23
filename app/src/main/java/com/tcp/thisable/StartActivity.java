package com.tcp.thisable;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.TextView;

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
        int action = event.getAction();

        if(action == MotionEvent.ACTION_DOWN){
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onTouchEvent(event);
    }
}
