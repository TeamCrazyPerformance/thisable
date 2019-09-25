package com.tcp.thisable;

import android.app.ActionBar;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class MypageActivity extends AppCompatActivity {

    ListView listView;
    ListViewAdapter adapter;
    DBHelper dbHelper;
    SQLiteDatabase sqLiteDatabase;
    int customsearch_size;
    TableLayout customlayout;
    TableRow tableRow;
    int j;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);

        Intent intent = new Intent(getApplicationContext(), MypageActivity.class);
        startActivity(intent);

        new JSONTask().execute("http://.../user/review");
        dbHelper = new DBHelper(this,"customsearch.db");
        sqLiteDatabase = dbHelper.getReadableDatabase();
        customsearch_size = dbHelper.getSize();
        customlayout = findViewById(R.id.customlayout);


        for(int i=0; i< customsearch_size; i++){
            if (i%2 == 0) tableRow = new TableRow(this);

            Button button = new Button(this);
            button.setBackgroundColor(Color.parseColor("#ffd633"));

            if (i == customsearch_size-1) {
                button.setText("+");
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent2 = new Intent(getApplicationContext(),CustomActivity.class);
                        intent2.putExtra("index", -1);
                        startActivity(intent2);

                    }
                });
            }

            else {
                j=i;
                button.setText("맞춤검색 " + (i + 1));
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent2 = new Intent(getApplicationContext(), CustomActivity.class);
                        intent2.putExtra("index", j);
                        startActivity(intent2);
                    }
                });

            }

            tableRow.addView(button);
            if (i % 2 == 1) customlayout.addView(tableRow);


        }


    }


    class JSONTask extends AsyncTask<String, String, String>{

        @Override
        protected String doInBackground(String... urls) {
            try {
                HttpURLConnection con = null;
                BufferedReader reader = null;

                try{
                    //URL url = new URL("http://.../user/review");
                    URL url = new URL(urls[0]);//url을 가져온다.
                    con = (HttpURLConnection) url.openConnection();
                    con.connect();//연결 수행

                    //입력 스트림 생성
                    InputStream stream = con.getInputStream();

                    //속도를 향상시키고 부하를 줄이기 위한 버퍼를 선언한다.
                    reader = new BufferedReader(new InputStreamReader(stream));

                    //실제 데이터를 받는곳
                    StringBuffer buffer = new StringBuffer();

                    //line별 스트링을 받기 위한 temp 변수
                    String line = "";

                    //아래라인은 실제 reader에서 데이터를 가져오는 부분이다. 즉 node.js서버로부터 데이터를 가져온다.
                    while((line = reader.readLine()) != null){
                        buffer.append(line);
                    }

                    //다 가져오면 String 형변환을 수행한다. 이유는 protected String doInBackground(String... urls) 니까
                    return buffer.toString();

                    //아래는 예외처리 부분이다.
                } catch (MalformedURLException e){
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    //종료가 되면 disconnect메소드를 호출한다.
                    if(con != null){
                        con.disconnect();
                    }
                    try {
                        //버퍼를 닫아준다.
                        if(reader != null){
                            reader.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }//finally 부분
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        //doInBackground메소드가 끝나면 여기로 와서 텍스트뷰의 값을 바꿔준다.
        @Override
        protected void onPostExecute(String result) {

            super.onPostExecute(result);
            adapter = new ListViewAdapter();
            listView = findViewById(R.id.review_list);
            listView.setAdapter(adapter);
            String place_id = null;
            String place_name = null;
            String rating = null;
            String comment = null;

            try {
                JSONArray jarray = new JSONObject(result).getJSONArray("reviews");
                for (int i = 0; i < jarray.length(); i++) {
                    HashMap map = new HashMap<>();
                    JSONObject jObject = jarray.getJSONObject(i);

                    //place_id = jObject.optString("place_id");
                    place_name = jObject.optString("place_name");
                    rating = jObject.optString("rating");
                    comment = jObject.optString("comment");

                    adapter.addVO(place_name,Float.parseFloat(rating),comment);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }


    }


}
