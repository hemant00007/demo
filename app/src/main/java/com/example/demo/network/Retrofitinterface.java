package com.example.demo.network;

import com.example.demo.model.MyModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Retrofitinterface {

    @GET("posts")
    Call<List<MyModel>> userList();

}
