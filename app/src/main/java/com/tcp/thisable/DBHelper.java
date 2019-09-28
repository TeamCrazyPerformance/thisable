package com.tcp.thisable;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE SEARCH (id INTEGER PRIMARY KEY AUTOINCREMENT,mainroad INTEGER, parking INTEGER,mainflat INTEGER, elevator INTEGER, " +
                "toilet INTEGER, room INTEGER, seat INTEGER, ticket INTEGER, blind INTEGER, deaf INTEGER, guide INTEGER, wheelchair INTEGER);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public int getsize(){
        SQLiteDatabase db = getReadableDatabase();
        int size = 0;
        Cursor cursor = db.rawQuery("SELECT * FROM SEARCH", null);
        while (cursor.moveToNext()) size++;
        return size;
        }


    public void insert(int condition[]) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO SEARCH (mainroad, parking, mainflat, elevator, toilet, room, seat, ticket, blind, deaf, guide, wheelchair) VALUES('" + condition[0]+ "', '" + condition[1] + "', '" + condition[2]+ "', '"+condition[3]+"', '"+condition[4]+"', '"+condition[5]+"'" +
                ", '"+condition[6]+"', '"+condition[7]+"', '"+condition[8]+"', '"+condition[9]+"', '"+condition[10]+"', '"+condition[11]+"');");
        db.close();
    }

    public void delete(int index){
        SQLiteDatabase db = getWritableDatabase();
        // 입력한 항목과 일치하는 행 삭제
        db.execSQL("DELETE FROM SEARCH WHERE id='" + index + "';");
        db.close();
    }

    public void update(int index, int condition[]){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE SEARCH SET mainroad = '"+condition[0]+"', parking = '"+condition[1]+"', mainflat = '"+condition[2]+"', elevator = '"+condition[3]+"', toilet = '"+condition[4]+"', room = '"+condition[5]+"', seat" +
                " = '"+condition[6]+"', ticket = '"+condition[7]+"', blind = '"+condition[8]+"', deaf = '"+condition[9]+"', guide = '"+condition[10]+"', wheelchair = '"+condition[11]+"' WHERE id = '"+index+"';");

    }

    public int[] getList(int id) {
        // 읽기가 가능하게 DB 열기
        SQLiteDatabase db = getReadableDatabase();
        int result[] = new int[12];
        Cursor cursor = db.rawQuery("SELECT * FROM SEARCH", null);
        while (cursor.moveToNext()) {
            if (cursor.getInt(0) == id){
                for (int i =1 ; i < 13 ; i++){
                    result[i-1] = cursor.getInt(i);
                }
                break;
            }
        }
        return result;
    }

}

