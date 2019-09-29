package com.tcp.thisable;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity {

    EditText email_view;
    EditText name_view;
    EditText pass_view;
    String email;
    String name;
    String pass;
    Button sign_up;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        email_view = findViewById(R.id.email);
        name_view = findViewById(R.id.name);
        pass_view = findViewById(R.id.password);
        sign_up = findViewById(R.id.sign_up);

        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                email = email_view.getText().toString();
                name = name_view.getText().toString();
                pass = pass_view.getText().toString();

                Call<String> res = NetRetrofit.getInstance().getService().signUp(email,pass,name);
                res.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if(response.isSuccessful()){
                            finish();
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"이미 이메일이 존재합니다.",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t){

                    }
                });

            }
        });



    }
}
