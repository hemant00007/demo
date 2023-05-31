package com.example.demo.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.demo.R;
import com.example.demo.adapters.MyAdapter;
import com.example.demo.model.MyModel;
import com.example.demo.network.RetrofitBase;
import com.example.demo.network.Retrofitinterface;
import com.example.demo.room.DataBaseClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    public RecyclerView recyclerView;
    List<MyModel> models = new ArrayList<>();
    MyAdapter adapter;
    ProgressBar progressBar;
    List<MyModel> localmodels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycler);
        progressBar = findViewById(R.id.progress);
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        if (RetrofitBase.hasNetwork(MainActivity.this)) {
            getData();
        } else  {
            load_from_localDB();
            Toast.makeText(MainActivity.this,"NO Internet available",Toast.LENGTH_SHORT).show();
        }

    }

    private void load_from_localDB() {
      Thread thread = new Thread(new Runnable() {
          @Override
          public void run() {

              List<MyModel> mydata = DataBaseClient.getInstance(MainActivity.this).getAppDatabase().mydio().getAll();
                     //   models.clear();
              System.out.println(mydata);

//              for (MyModel mm: mydata) {
//                  MyModel repo = new  MyModel(
//                          mm.getTitle(),
//                          mm.getBody()
//                  );
//                  models.add(repo);
//              }

              runOnUiThread(new Runnable() {
                  @Override
                  public void run() {
                     // adapter.notifyDataSetChanged();
                 //     System.out.println(models.size());
                      progressBar.setVisibility(View.GONE);
                      recyclerView.setAdapter(new MyAdapter(mydata,MainActivity.this));

                  }
              });
          }
      }
      );
      thread.start();
    }

    private void getData() {

        Retrofitinterface retrofitinterface = RetrofitBase.getCacheEnabledRetrofit(MainActivity.this).create(Retrofitinterface.class);
        Call<List<MyModel>> call = retrofitinterface.userList();
        call.enqueue(new Callback<List<MyModel>>() {
            @Override
            public void onResponse(Call<List<MyModel>> call, Response<List<MyModel>> response) {
                if(response.body()!=null){
                    progressBar.setVisibility(View.GONE);
                    models = response.body();
                    localmodels = response.body();
                    adapter = new MyAdapter(models,MainActivity.this);
                    recyclerView.setAdapter(adapter);
                    saveData();
                }

            }

            @Override
            public void onFailure(Call<List<MyModel>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);

                Toast.makeText(MainActivity.this,t.getLocalizedMessage().toString(),Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void saveData() {
        class SaveTask extends AsyncTask<Void, Void, Void> {


            @Override
            protected Void doInBackground(Void... voids) {
                DataBaseClient.getInstance(MainActivity.this).getAppDatabase().mydio().deleteTable();

                   for(int i=0; i<localmodels.size(); i++){
                       MyModel model = new MyModel();
                      // model.setUserId(localmodels.get(i).getUserId());
                       model.setTitle(localmodels.get(i).getTitle());
                       model.setBody(localmodels.get(i).getBody());

                       DataBaseClient.getInstance(MainActivity.this).getAppDatabase().mydio().insert(model);

                   }
                return null;
            }

            @Override
            protected void onPostExecute(Void unused) {
                super.onPostExecute(unused);
                //Toast.makeText(MainActivity.this,"Data saved",Toast.LENGTH_SHORT).show();
            }
        }

        SaveTask saveTask = new SaveTask();
        saveTask.execute();
    }
}