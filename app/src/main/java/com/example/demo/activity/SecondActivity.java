package com.example.demo.activity;

import static com.example.demo.utils.ImageStore.loadImageFromStorage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.demo.R;
import com.example.demo.adapters.ImageAdapter;
import com.example.demo.adapters.MyAdapter;
import com.example.demo.model.ImageDB;
import com.example.demo.model.MyModel;
import com.example.demo.room.DataBaseClient;
import com.example.demo.utils.ImageStore;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.permissionx.guolindev.PermissionX;


import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class SecondActivity extends AppCompatActivity {

    private static final int CAMERA_REQUEST = 1;
    Button clickbtn,loadimg;
    private ImageView imageView;
    private  double latitude;
    private double longitude;
    private TextView loc;
    private RecyclerView recyclerView_image;
    private  String imagepath;
    public  List<ImageDB> imageDBList = new ArrayList<>();
    List<ImageDB> models = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        permissionAsk();
        showImageonView();
        recyclerView_image = findViewById(R.id.rec_image);
        recyclerView_image.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));

       // recyclerView_image.setLayoutManager(new LinearLayoutManager(SecondActivity.this,LinearLayoutManager.HORIZONTAL));
        recyclerView_image.setHasFixedSize(true);
        clickbtn = findViewById(R.id.btn);
        imageView = findViewById(R.id.img);
        loc = findViewById(R.id.loc);
        loadimg= findViewById(R.id.loadimg);
        loadimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//              Bitmap  bitmap=  loadImageFromStorage(imagepath);
//                imageView.setImageBitmap(bitmap);
                showImageonView();

            }
        });
        clickbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        });


    }

    private void showImageonView() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                List<ImageDB> mydata = DataBaseClient.getInstance(SecondActivity.this).getAppDatabase().mydio().getAllImage();
                models.clear();
                System.out.println(mydata);

                for (ImageDB mm: mydata) {
                    ImageDB repo = new  ImageDB(
                            mm.getUserId(),
                            mm.getImagePath()

                    );
                    models.add(repo);
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // adapter.notifyDataSetChanged();
                        System.out.println(models.size());
                        recyclerView_image.setAdapter(new ImageAdapter(SecondActivity.this,models));

                    }
                });
            }
        }
        );
        thread.start();
    }

    private void permissionAsk() {
        PermissionX.init(SecondActivity.this)
                .permissions(Manifest.permission.READ_CONTACTS, Manifest.permission.CAMERA, Manifest.permission.ACCESS_COARSE_LOCATION)
                .explainReasonBeforeRequest()
                .onExplainRequestReason((scope, deniedList, beforeRequest) -> {
//                    CustomDialog customDialog = new CustomDialog(MainJavaActivity.this, "PermissionX needs following permissions to continue", deniedList);
//                    scope.showRequestReasonDialog(customDialog);
                    scope.showRequestReasonDialog(deniedList, "PermissionX needs following permissions to continue", "Allow");
                })
                .onForwardToSettings((scope, deniedList) -> {
                    scope.showForwardToSettingsDialog(deniedList, "Please allow following permissions in settings", "Allow");
                })
                .request((allGranted, grantedList, deniedList) -> {
                    if (allGranted) {
                        getLocation();
                      //  Toast.makeText(SecondActivity.this, "All permissions are granted", Toast.LENGTH_SHORT).show();
                    } else {
                     //   Toast.makeText(SecondActivity.this, "The following permissions are denied：" + deniedList, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void getLocation() {
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(3000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.getFusedLocationProviderClient(getApplicationContext()).requestLocationUpdates(locationRequest, new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                LocationServices.getFusedLocationProviderClient(getApplicationContext()).removeLocationUpdates(this);
                if (locationResult != null && locationResult.getLocations().size() > 0) {
                    int latestLocationIndex = locationResult.getLocations().size() - 1;
                     latitude = locationResult.getLocations().get(latestLocationIndex).getLatitude();
                    longitude = locationResult.getLocations().get(latestLocationIndex).getLongitude();
                    Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                    try {
                        List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
                        // setUpdata(addresses);


                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    loc.setText("Location "+latitude +" "+longitude);


                  

                }

            }
        }, Looper.getMainLooper());
    }

//    String invoice="http://inv.mgdh.in/invoice/"+orderno+"_invoice.pdf";
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            String time = String.valueOf(System.currentTimeMillis());
            String imageName=time+"profile.jpg";
           imagepath= ImageStore.saveToInternalStorage(SecondActivity.this,photo);
//           imageDBList.add(new ImageDB(im))
            Log.d("Imagepath is",imagepath);
//            Log.d("current time is", String.valueOf(new Timestamp(System.currentTimeMillis())));
            System.out.println(time);
            imageDBList.add(new ImageDB(1,
                    imagepath
                    ));
           saveData();
           // imageView.setImageBitmap(photo);
        }
    }

    private void saveData() {
        class SaveTask extends AsyncTask<Void, Void, Void> {
            @Override
            protected Void doInBackground(Void... voids) {
                DataBaseClient.getInstance(SecondActivity.this).getAppDatabase().mydio().deleteTable();
                for(int i=0; i<imageDBList.size(); i++){
                    ImageDB imageDB = new ImageDB();
                    // model.setUserId(localmodels.get(i).getUserId());
                    imageDB.setImagePath(imageDBList.get(i).getImagePath());
                    DataBaseClient.getInstance(SecondActivity.this).getAppDatabase().mydio().insertimage(imageDB);
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void unused) {
                super.onPostExecute(unused);
                Toast.makeText(SecondActivity.this,"image saved",Toast.LENGTH_SHORT).show();
            }
        }

        SaveTask saveTask = new SaveTask();
        saveTask.execute();
    }





}