package com.example.demo.utils;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.example.demo.activity.SecondActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageStore {


    public static  String saveToInternalStorage(SecondActivity secondActivity, Bitmap bitmapImage){
        ContextWrapper cw = new ContextWrapper(secondActivity);
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath=new File(directory,"profile.jpg");
        System.out.println(mypath);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return directory.getAbsolutePath();
    }

  

    public static Bitmap loadImageFromStorage(String path)
    {
        Bitmap b = null;

        try {
         //   File folder = new File(Environment.getExternalStorageDirectory().toString() + "/Folder Name/");

            File f=new File(path,"profile.jpg");
            b = BitmapFactory.decodeStream(new FileInputStream(f));

            System.out.println(b);
           

           // imageView.setImageBitmap(b);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        return  b;
    }
}
