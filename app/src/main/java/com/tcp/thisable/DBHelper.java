package com.tcp.thisable;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    int size = 0;

    // DBHelper 생성자로 관리할 DB 이름과 버전 정보를 받음
    public DBHelper(Context context, String name) {
        super(context, name, null, 1);
    }

    // DB를 새로 생성할 때 호출되는 함수
    @Override
    public void onCreate(SQLiteDatabase db) {
        // 새로운 테이블 생성
        /* 이름은 MONEYBOOK이고, 자동으로 값이 증가하는 _id 정수형 기본키 컬럼과
        item 문자열 컬럼, price 정수형 컬럼, create_at 문자열 컬럼으로 구성된 테이블을 생성. */
        db.execSQL("CREATE TABLE CUSTOMSEARCH (_id INTEGER PRIMARY KEY , mainroad BOOLEAN, parking BOOLEAN,mainflat BOOLEAN, elevator BOOLEAN, " +
                "toilet BOOLEAN, room BOOLEAN, seat BOOLEAN, ticket BOOLEAN, blind BOOLEAN, deaf BOOLEAN, guide BOOLEAN, wheelchair BOOLEAN);");
    }

    // DB 업그레이드를 위해 버전이 변경될 때 호출되는 함수
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insert(boolean[] condition) {
        // 읽고 쓰기가 가능하게 DB 열기
        SQLiteDatabase db = getWritableDatabase();
        // DB에 입력한 값으로 행 추가
        db.execSQL("INSERT INTO CUSTOMSEARCH VALUES(" +size+ " " + condition[0] + " " + condition[1] + " " + condition[2] + " " + condition[3] + " " + condition[4] + " " + condition[5] +"" +
                " " + condition[6] + " " + condition[7] + " " + condition[8] +" " + condition[9] +" " + condition[10] + " " + condition[11] + ");");
        size++;
        db.close();
    }

    public void update(boolean[] condition, int _id) {
        SQLiteDatabase db = getWritableDatabase();
        // 입력한 항목과 일치하는 행의 가격 정보 수정
        db.execSQL("UPDATE CUSTOMSEARCH SET mainroad =" + condition[0] +", parking ="+ condition[1] +"maniflat =" + condition[2]+"elevator = " +condition[3]+" toilet = " +condition[4] +" room = "+condition[5]+" " +
                "WHERE _id='" + _id + "';");
        db.close();
    }
//수정은 아직!!!!!!!!
    public void delete(int _id) {
        SQLiteDatabase db = getWritableDatabase();
        // 입력한 항목과 일치하는 행 삭제
        db.execSQL("DELETE FROM CUSTOMSEARCH WHERE _id='" + _id + "';");
        db.close();
    }

    public int getSize(){
        return size;
    }

    public boolean[] getList(int id) {
        // 읽기가 가능하게 DB 열기
        SQLiteDatabase db = getReadableDatabase();
        boolean[] result = new boolean[12];

        // DB에 있는 데이터를 쉽게 처리하기 위해 Cursor를 사용하여 테이블에 있는 모든 데이터 출력
        Cursor cursor = db.rawQuery("SELECT * FROM CUSTOMSEARCH", null);
        while (cursor.moveToNext()) {
            if (cursor.getInt(0) == id){
                for (int i =0 ; i < id ; i++){
                    result[i] = (cursor.getInt(i+1)== 1);
                }
            }
        }

        return result;
    }
}

