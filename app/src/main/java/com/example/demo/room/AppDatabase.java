package com.example.demo.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.demo.model.ImageDB;
import com.example.demo.model.MyModel;


@Database(entities = {MyModel.class, ImageDB.class}, version = 2)
    public abstract class AppDatabase extends RoomDatabase {
        public abstract AppDio mydio();
    }

