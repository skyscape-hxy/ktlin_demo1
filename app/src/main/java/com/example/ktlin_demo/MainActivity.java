package com.example.ktlin_demo;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.ImageView;

import com.example.ktlin_demo.w.ScaleView;

public class MainActivity extends AppCompatActivity {
ServiceConnection  serviceConnection =new ServiceConnection() {
    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        UsbService.UsbBinder usbBinder  = (UsbService.UsbBinder) service;
        usbBinder.setCallBack(new UsbService.CallBack() {

            @Override
            public void mounted() {

            }

            @Override
            public void unmounted() {

            }
        });
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {

    }
};
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ScaleView iv = findViewById(R.id.iv);
        findViewById(R.id.b).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv.bigger();
            }
        });
       findViewById(R.id.s).setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               iv.smaller();
           }
       });
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.t5);
        iv.setImageBitmap(bitmap,false);
    }
}