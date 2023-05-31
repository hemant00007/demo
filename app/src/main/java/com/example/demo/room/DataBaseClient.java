package com.example.demo.room;

import android.content.Context;

import androidx.room.Room;

public class DataBaseClient {
    private Context mCtx;
    private static DataBaseClient mInstance;

    //our app database object
    private AppDatabase appDatabase;

    private DataBaseClient(Context mCtx) {
        this.mCtx = mCtx;

        //creating the app database with Room database builder
        //alldata is the name of the database
        appDatabase = Room.databaseBuilder(mCtx, AppDatabase.class, "lucky").build();
    }

    public static synchronized DataBaseClient getInstance(Context mCtx) {
        if (mInstance == null) {
            mInstance = new DataBaseClient(mCtx);
        }
        return mInstance;
    }

    public AppDatabase getAppDatabase() {
        return appDatabase;
    }
}
