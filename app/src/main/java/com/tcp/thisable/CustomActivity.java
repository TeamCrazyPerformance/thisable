package com.tcp.thisable;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.appcompat.app.AppCompatActivity;

public class CustomActivity extends AppCompatActivity {

    DBHelper dbHelper;
    SQLiteDatabase sqLiteDatabase;
    boolean[] customlist;
    int index;
    CheckBox checkBox;
    Button update, delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_search);

        Intent intent = new Intent(getApplicationContext(), CustomActivity.class);
        index = intent.getIntExtra("index", -1);

        dbHelper = new DBHelper(this,"customsearch.db");
        sqLiteDatabase = dbHelper.getWritableDatabase();
        customlist = dbHelper.getList(index);

        for(int i =0; i<12 ; i++){
            int resID = getResources().getIdentifier("c"+(i+1), "id", getPackageName());
            checkBox = findViewById(resID);
            checkBox.setChecked(customlist[i]);
        }

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean[] temp = new boolean[12];
                for(int i = 0; i<12;i ++){
                    int resID = getResources().getIdentifier("c"+(i+1), "id", getPackageName());
                    checkBox = findViewById(resID);
                    temp[i] = checkBox.isChecked();
                }
                dbHelper.update(temp,index);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHelper.delete(index);
            }
        });


    }
}
