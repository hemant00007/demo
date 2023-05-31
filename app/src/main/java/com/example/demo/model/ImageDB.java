package com.example.demo.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity
public class ImageDB implements Serializable {
    @PrimaryKey()
    @SerializedName("id")
    @Expose
    private Integer userId;

    @ColumnInfo(name = "imagePath")
    @SerializedName("Image")
    @Expose
    private String imagePath;

    public ImageDB() {
    }

    public ImageDB(Integer userId, String imagePath) {
        this.userId = userId;
        this.imagePath = imagePath;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
