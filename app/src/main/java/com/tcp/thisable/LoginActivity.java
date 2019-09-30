package com.tcp.thisable;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {
    EditText email_view;
    EditText password_view;
    Button sign_in;
    Button sign_up;
    String email;
    String password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email_view = findViewById(R.id.email);
        password_view = findViewById(R.id.password);
        sign_in = findViewById(R.id.sign_in);
        sign_up = findViewById(R.id.sign_up);


        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = email_view.getText().toString().trim();
                password = password_view.getText().toString().trim();
                NetRetrofit.getInstance().getService().signIn(email, password).enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {

                        if(response.isSuccessful()) {
                            SharedPreferences shared = getSharedPreferences("MYPREFRENCE", MODE_PRIVATE);
                            SharedPreferences.Editor editor = shared.edit();
                            editor.putString("userid", response.body());
                            editor.commit();
                            Intent intent = new Intent(getApplicationContext(), IndexActivity.class);
                            startActivity(intent);
                            finish();
                        }

                        else{
                            Toast.makeText(getApplicationContext(),"이메일 또는 비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "서버가 응답하지 않습니다.", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });


        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),SignupActivity.class);
                startActivity(intent);
            }
        });




    }
}
