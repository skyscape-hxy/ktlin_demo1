package com.example.ktlin_demo.mediaplayer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentResolver;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ktlin_demo.R;

import java.util.ArrayList;
import java.util.List;

public class MediaPlayerActivity extends AppCompatActivity {
    private Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            if (musicItems != null && musicItems.size() != 0) {
                rv.setAdapter(new MediaplayerAdatper());
            }
        }
    };
    RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_player);
        rv = findViewById(R.id.rv);
        loadLocalFile();
    }

    private List<LocalMediaItem> musicItems = new ArrayList();

    private void loadLocalFile() {
        ContentResolver resolver = getContentResolver();
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String[] obj = new String[]{                    //查询的内容
                MediaStore.Video.Media.DISPLAY_NAME,    //文件名
                MediaStore.Video.Media.DATA,            //数据地址
        };
        Cursor cursor = resolver.query(uri, obj, null, null, null);
        if (cursor == null) {
            throw new RuntimeException("未找到数据");
        }
        while (cursor.moveToNext()) {
            LocalMediaItem item = new LocalMediaItem();
            musicItems.add(item);      //将查询到的内容放入列表中
            item.setFileName(cursor.getString(0));
//
            item.setData(cursor.getString(1));
        }
        handler.sendEmptyMessage(0x0001);
        cursor.close();
    }

    class MediaplayerAdatper extends RecyclerView.Adapter<MediaplayerAdatper.ViewHolder> {



        public MediaplayerAdatper() {

        }

        @NonNull
        @Override
        public MediaplayerAdatper.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(MediaPlayerActivity.this).
                    inflate(R.layout.item_video, parent, false);

            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MediaplayerAdatper.ViewHolder holder, int position) {
            LocalMediaItem mediaItem = musicItems.get(position);
            holder.tvTitle.setText(mediaItem.getFileName());
            //setThumbnail(holder.ivCover, mediaItem);
            MediaMetadataRetriever mmr = new MediaMetadataRetriever();
            mmr.setDataSource(mediaItem.getData());
            byte[] picture = mmr.getEmbeddedPicture();
//            Bitmap bitmap = BitmapFactory.decodeByteArray(picture, 0, picture.length);
//            holder.ivCover.setImageBitmap(bitmap);
        }

        private void setThumbnail(ImageView imgView, LocalMediaItem mediaItem) {

            Bitmap bmp;

            bmp = ThumbnailUtil.getVideoThumbnail(mediaItem.getData());


            //获取视频缩略图后设置上去
            imgView.setImageBitmap(bmp);

        }

        @Override
        public int getItemCount() {
            return musicItems.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView tvTitle;
            ImageView ivCover;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                tvTitle = itemView.findViewById(R.id.tv_title);
                ivCover =itemView. findViewById(R.id.iv_cover);
            }
        }
    }


}