package com.example.demo.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.demo.model.ImageDB;
import com.example.demo.model.MyModel;

import java.util.List;
@Dao
public interface AppDio {

    @Query("SELECT * FROM MyModel")
    List<MyModel> getAll();

    @Insert
    void insert(MyModel recipe);

    @Query("DELETE FROM MyModel")
    public void deleteTable();




    @Query("SELECT * FROM ImageDB")
    List<ImageDB> getAllImage();

    @Insert
    void insertimage(ImageDB imageDB);

    @Query("DELETE FROM ImageDB")
    public  void deleteImageTable();

}
