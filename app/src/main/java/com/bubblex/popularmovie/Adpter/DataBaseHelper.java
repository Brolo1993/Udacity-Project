package com.bubblex.popularmovie.Adpter;

/**
 * Created by Amr ElBrolosy on 14/03/15.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.bubblex.popularmovie.Model.Movie;

import java.util.ArrayList;

public class DataBaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "MyDBName.db";

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table movie "
                + "(id INTEGER PRIMARY KEY  , name text, review text, img text,avg text ,realse text,pos text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS movie");

        onCreate(db);
    }

    public long insertMovie(String id ,String name, String review, String img, String avg, String realse, String pos) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("id", id);
        contentValues.put("review", review);
        contentValues.put("img", img);
        contentValues.put("avg", avg);
        contentValues.put("realse", realse);
        contentValues.put("pos", pos);
        return db.insert("movie", null, contentValues);
    }


    public ArrayList<Movie> GetAllMovies() {
        ArrayList<Movie> array_list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from movie", null);
        res.moveToFirst();
        while (!res.isAfterLast()) {
            Movie contents = new Movie();
            contents.setTitle(res.getString(res
                    .getColumnIndex("name")));
            contents.setOverview(res.getString(res
                    .getColumnIndex("review")));
            contents.setID(res.getString(res.getColumnIndex("id")));
            contents.setImgUrl(res.getString(res
                    .getColumnIndex("img")));
            contents.setAvgRate(res.getString(res
                    .getColumnIndex("avg")));
            contents.setReleaseDate(res.getString(res
                    .getColumnIndex("realse")));
            contents.setIsFav(true);
            array_list.add(contents);
            res.moveToNext();
        }
        return array_list;
    }

    public Movie GetMovie(String Name) {
        Movie movie = new Movie();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from movie where name = " + Name, null);
        res.moveToFirst();
        while (!res.isAfterLast()) {
            movie.setTitle(res.getString(res
                    .getColumnIndex("name")));
            movie.setOverview(res.getString(res
                    .getColumnIndex("review")));
            movie.setID(res.getString(res.getColumnIndex("id")));
            movie.setImgUrl(res.getString(res
                    .getColumnIndex("img")));
            movie.setAvgRate(res.getString(res
                    .getColumnIndex("avg")));
            movie.setReleaseDate(res.getString(res
                    .getColumnIndex("realse")));
            movie.setIsFav(true);
        }
        return movie;
    }


    public ArrayList<String> GetAllMoviesName() {
        ArrayList<String> array_list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from movie", null);
        res.moveToFirst();
        while (!res.isAfterLast()) {
            Movie movie = new Movie();
            movie.setIsFav(true);
            array_list.add(res.getString(res
                    .getColumnIndex("name")));
            res.moveToNext();
        }
        return array_list;
    }

    public int getlastid() {
        int id = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM movie WHERE id = (SELECT MAX(id)  FROM movie)", null);
//        res.moveToFirst();
//        id = res.getInt(res.getColumnIndex("id"));
        while (res.moveToNext()) {
            id = res.getInt(res.getColumnIndex("id"));
        }
        return id;
    }

    public boolean deleterow(String number) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("movie", "name" + "= '" + number + "'", null) > 0;
    }

    public boolean ISExistt(String Name) {
        boolean result = false;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from movie where name = '" + Name+"'", null);
        return res.moveToFirst() || result;

    }
}
