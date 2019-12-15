package com.example.billbook2;



import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.LinkedList;

public class DBManager {
    private DBHelper helper;
    private SQLiteDatabase db;
    private static final String TABLE_NAME = "count";

    public DBManager(Context context) {
        helper = new DBHelper(context);
         db = helper.getWritableDatabase();
    }



    public void insert(Count count) {
        db.beginTransaction();  // 开始事务
        try {
            ContentValues cv = new ContentValues();
            cv.put("count",count.getMoney());
            cv.put("type",count.getType());
            cv.put("date", count.getDate());
            cv.put("describe",count.getDescribe());
            db.insert( TABLE_NAME ,"id",cv);
            db.setTransactionSuccessful();  // 设置事务成功完成
        }finally {
            db.endTransaction();    // 结束事务
        }
    }

    public Double getResult(int type)
    {
        Double result = 0.0;
        Cursor c = db.rawQuery("select id,count,type,date,describe from "+ TABLE_NAME ,null);
        for (c.moveToFirst();!c.isAfterLast();c.moveToNext()) {
            if (c.getInt(2) == type)
                result += c.getFloat(1);
        }
        c.close();
        return result;
    }
    public LinkedList<Integer> getids(){
        int id = 0;
        LinkedList<Integer> list = new LinkedList<Integer>();
        Cursor c = db.rawQuery("select id,count,type,date,describe from "+ TABLE_NAME,null);
        for (c.moveToFirst();!c.isAfterLast();c.moveToNext()){
            id=c.getInt(c.getColumnIndex("id"));
            list.add(id);
        }c.close();
        return list;
    }
    public String Look(int ID){
        ID ++;
        String pass = "";
        Cursor c = db.rawQuery("select id,count,type,date,describe from "+ TABLE_NAME + " where id = ID",null);
        for (c.move(ID);!c.isAfterLast();c.moveToNext()){
            //for (;!c.isAfterLast();c.moveToNext()){
            pass = "id: " + c.getString(0)+"/" + "count: " +c.getString(1) +"/"+ "type: " +c.getString(2) +"/"+ "date: " +c.getString(3);
            break;
        }
        return pass;
    }

    public void delete(int ID){
        ID++;
       db.delete(TABLE_NAME,"id=?",new String[]{ID + ""});
    }


    public void Alter(int ID,int Count,int Type,String Date,String describle){
        ID ++;
        ContentValues values = new ContentValues();
        values.put("count",Count);
        values.put("type",Type);
        values.put("date",Date);
        db.update(TABLE_NAME,values,"id=?",new String[]{ID + ""});
        db.close();
    }

    public void closeDB(){
        db.close();
    }
}


