package com.example.ktlin_demo;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class UsbService extends Service {
    public UsbService() {
    }
    private UsbBinder mUsbBinder =new UsbBinder();

    class UsbBinder extends Binder{
        public void setCallBack(CallBack callBack){
            mCallBack = callBack;
        }
    }

    public interface CallBack{
        void mounted();
        void unmounted();
    }
    private CallBack mCallBack;
    @Override
    public IBinder onBind(Intent intent) {
      return mUsbBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // TODO: 2022/3/30 注册广播
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // TODO: 2022/3/30 注销广播
    }
    class UsbDiskReceiver extends BroadcastReceiver {
        private final String TAG = UsbDiskReceiver.class.getName();
        private final int USB_DISK_MOUNTED = 1;
        private final int USB_DISK_UNMOUNTED = 2;

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Intent.ACTION_MEDIA_MOUNTED)) {
                if (mCallBack!=null){
                    mCallBack.mounted();
                }

            } else if (intent.getAction().equals(Intent.ACTION_MEDIA_UNMOUNTED)) {
                    mCallBack.unmounted();
            }
        }
    }


}