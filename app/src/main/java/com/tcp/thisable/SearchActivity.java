package com.tcp.thisable;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class SearchActivity extends AppCompatActivity {

    SQLiteDatabase sqLiteDatabase;
    int[] list = new int[12];
    int index;
    CheckBox checkBox[] = new CheckBox[12];
    Button update, delete;
    ConstraintLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_search);
        Intent intent = getIntent();
        index = intent.getIntExtra("index",-1);
        final DBHelper dbHelper = new DBHelper(getApplicationContext(),"Search.db",null,1);
        update = findViewById(R.id.update);
        delete = findViewById(R.id.delete);
        layout = findViewById(R.id.buttonlayout);
        checkBox[0] = findViewById(R.id.c1);
        checkBox[1] = findViewById(R.id.c2);
        checkBox[2] = findViewById(R.id.c3);
        checkBox[3] = findViewById(R.id.c4);
        checkBox[4] = findViewById(R.id.c5);
        checkBox[5] = findViewById(R.id.c6);
        checkBox[6] = findViewById(R.id.c7);
        checkBox[7] = findViewById(R.id.c8);
        checkBox[8] = findViewById(R.id.c9);
        checkBox[9] = findViewById(R.id.c10);
        checkBox[10] = findViewById(R.id.c11);
        checkBox[11] = findViewById(R.id.c12);

        if (index == -1) {
            layout.removeView(update);
            delete.setText("CREATE");
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    list = new int[12];
                    for(int i = 0; i<12;i ++){
                        list[i] = checkBox[i].isChecked()? 1 : 0;
                    }
                    dbHelper.insert(list);
                    finish();

                }
            });

        }

        else{
            list = dbHelper.getList(index);
            for(int i=0;i<12;i++){
                checkBox[i].setChecked(list[i]!=0);
            }

            update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for(int i=0; i<12;i++){
                        list[i] = checkBox[i].isChecked()? 1 : 0;
                    }
                    dbHelper.update(index,list);
                    finish();
                }
            });

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dbHelper.delete(index);
                    finish();
                }
            });


        }

    }
}
