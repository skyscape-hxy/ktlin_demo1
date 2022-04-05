package com.example.ktlin_demo.mediaplayer;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.util.LruCache;
import android.widget.ImageView;

import com.example.ktlin_demo.R;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class ImageLoader {
    private static final int MESSAGE_POST_RESULT = 1;
    private static final String TAG = ImageLoader.class.getName();
    private Handler mMainHandler = new Handler(Looper.getMainLooper()) {

    };
    private Context mContext;
    private LruCache<String, Bitmap> mMemoryCache;

    ExecutorService mExecutorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    Handler mUiHandler = new Handler(Looper.getMainLooper());

    public ImageLoader() {
        initImageCache();
    }

    private void initImageCache() {
        int maxMemory = (int) ((Runtime.getRuntime().maxMemory()) / 1024);
        int cacheSize = maxMemory / 8;
        mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes() * value.getHeight() / 1024;
            }
        };
    }

    public void displayImage(final  String url ,final ImageView imageView){
        Bitmap bitmap = mMemoryCache.get(url);
        if (bitmap != null) {
            Log.e(TAG, "displayImage: 缓冲--》"+bitmap );
            imageView.setImageBitmap(bitmap);
            return;
        }
        imageView.setTag(url);
        mExecutorService.submit(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = loadBitmap(url);
                if (bitmap == null){
                    return;
                }
                if (imageView.getTag().equals(url)) {
                    updateImageView(imageView,bitmap);
                }
                mMemoryCache.put(url,bitmap);
            }
        });

}

    private void updateImageView(ImageView imageView, Bitmap bitmap) {
        mUiHandler.post(new Runnable() {
            @Override
            public void run() {
                imageView.setImageBitmap(bitmap);
            }
        });
    }

    private Bitmap loadBitmap(String url) {
        Bitmap bitmap = ThumbnailUtil.getVideoThumbnail(url);
        return bitmap;
    }


}
