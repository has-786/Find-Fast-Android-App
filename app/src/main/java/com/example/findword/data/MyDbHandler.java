package com.example.findword.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.example.findword.Points;
import com.example.findword.params.Params;

import java.util.ArrayList;
import java.util.List;

public class MyDbHandler extends SQLiteOpenHelper {

    public MyDbHandler(Context context) {
        super(context, Params.DB_NAME, null, Params.DB_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        String create = "CREATE TABLE " + Params.TABLE_NAME + "("
                + Params.KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + Params.KEY_POINTS
                + " INTEGER, " + Params.KEY_TIME + " TEXT" + ")";
        Log.d("myapp", "Query being run is : "+ create);
        db.execSQL(create);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addPoints(Points points){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Params.KEY_POINTS, points.getPoints());
        values.put(Params.KEY_TIME, points.getTime());

        db.insert(Params.TABLE_NAME, null, values);
        Log.d("myapp", "Successfully inserted");
        db.close();
    }

    public ArrayList<Points> getAllPoints(){
        ArrayList<Points> pointsList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Generate the query to read from the database
        String select = "SELECT * FROM " + Params.TABLE_NAME;
        Cursor cursor = db.rawQuery(select, null);

        //Loop through now
        if(cursor.moveToFirst()){
            do{
                Points points = new Points();
                points.setId(Integer.parseInt(cursor.getString(0)));
                points.setPoints(Integer.parseInt(cursor.getString(1)));
                points.setTime(cursor.getString(2));

                pointsList.add(points);
            }while(cursor.moveToNext());
        }
        return pointsList;
    }

    public int updateContact(Points points){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Params.KEY_POINTS, points.getPoints());
        values.put(Params.KEY_TIME, points.getTime());

        //Lets update now
        return db.update(Params.TABLE_NAME, values, Params.KEY_ID + "=?",
                new String[]{String.valueOf(points.getId())});


    }




}

