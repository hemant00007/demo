package com.example.demo.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity
public  class MyModel implements Serializable {

//    @PrimaryKey(autoGenerate = true)
//    @SerializedName("id")
//    @Expose
//    private Integer id;
@PrimaryKey(autoGenerate = true)
        @SerializedName("userId")
        @Expose
        private Integer userId;

    @ColumnInfo(name = "title")
        @SerializedName("title")
        @Expose
        private String title;

    @ColumnInfo(name = "body")
        @SerializedName("body")
        @Expose
        private String body;

    public MyModel() {
    }

    public MyModel( String title, String body) {
        this.title = title;
        this.body = body;
    }

    public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }


        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }


}