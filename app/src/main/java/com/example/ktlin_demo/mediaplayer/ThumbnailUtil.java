package com.example.ktlin_demo.mediaplayer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;

/**
 * 缩略图工具类
 * Created by Holmofy on 2016/12/10.
 */

public class ThumbnailUtil {

    private ThumbnailUtil() {
    }

    /**
     * 获取视频缩略图
     *
     * @param videoPath 视频路径
     * @return 返回缩略图
     */
    public static Bitmap getVideoThumbnail(String videoPath) {
        MediaMetadataRetriever media = new MediaMetadataRetriever();
        media.setDataSource(videoPath);
        Bitmap bitmap = media.getFrameAtTime(0);

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds= true;
        byte[] bytes = bitmap2Bytes(bitmap);
         BitmapFactory.decodeByteArray(bytes, 0, bytes.length - 1,options);
        options.inSampleSize = calculateInSampleSize(options);
        options.inJustDecodeBounds =false;
        Bitmap bitmap1 = BitmapFactory.decodeByteArray(bytes, 0, bytes.length - 1, options);
//        BitmapFactory.decodeByteArray()
        media.release();
        return bitmap1;
    }
    public static int dip2px(float dip, Context context) {
        float density = context.getResources().getDisplayMetrics().density;
        int px = (int) (dip * density + 0.5f);// 4.9->4, 4.1->4, 四舍五入
        return px;
    }
    public static byte[] bitmap2Bytes( Bitmap bitmap ){
        if( null == bitmap ){
            return null;
        }

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream( );
        bitmap.compress( Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream );

        return byteArrayOutputStream.toByteArray( );
    }


    public static int calculateInSampleSize(BitmapFactory.Options options){
        int width = options.outWidth;
        int height = options.outHeight;
        int inSampleSize = 1;
        if (height> 200 || width >200){
            int halfHeight = height / 2;
            int halfWidth = width / 2;
            while ((halfHeight/inSampleSize)>=200 && (halfWidth/inSampleSize)>=200){
                inSampleSize *=2;
            }
        }
        return inSampleSize;
    }


    public static final int MINI_KIND = MediaStore.Video.Thumbnails.MINI_KIND;

    public static final int MICRO_KIND = MediaStore.Video.Thumbnails.MICRO_KIND;

    /**
     * 获取视频缩略图
     *
     * @param videoPath 视频路径
     *                  //@param width     缩略图宽度
     *                  //@param height    缩略图高度
     * @param kind      类型 MediaStore.Images.Thumbnails.MINI_KIND ,MICRO_KIND,
     * @return 缩略图
     */
    public static Bitmap getVideoThumbnail(String videoPath, /*int width, int height,*/ int kind) {
        return ThumbnailUtils.createVideoThumbnail(videoPath, kind);
//        bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height, ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
    }

}
