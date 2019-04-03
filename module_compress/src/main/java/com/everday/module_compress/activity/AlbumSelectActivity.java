package com.everday.module_compress.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.everday.lib_base.base.BaseActivity;
import com.everday.lib_base.base.BaseViewModel;
import com.everday.lib_base.router.RouterActivityPath;
import com.everday.module_compress.R;
import com.everday.module_compress.activity.adapter.AdapterAlbumSelect;
import com.everday.module_compress.activity.bean.Album;
import com.everday.module_compress.databinding.CompressActivityAlbumSelectBinding;
import com.everday.module_compress.utils.Constants;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;

/**
* @author Everday
* @emil wangtaohandsome@gmail.com
* create at 2019/3/20
* description: 图片多选
*/
@Route(path = RouterActivityPath.MODULE_COMPRESS.MODULE_ALBUMSELECTACTIVITY)
public class AlbumSelectActivity extends AppCompatActivity implements AdapterAlbumSelect.OnItemListener {
    private ActionBar actionBar;
    private String[] requiredPermissions = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE};

    private Thread thread;
    private Handler handler;
    private ArrayList<Album> albums;
    private String[] projection = new String[]{
            MediaStore.Images.Media.BUCKET_DISPLAY_NAME,MediaStore.Images.Media.DATA
    };
    private RecyclerView recyclerView;
    private AdapterAlbumSelect mAdater;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.compress_activity_album_select);
        initView();

    }

    /**
     * 初始化视图
     */
    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.recyleView);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.compress_ic_arrow_back);
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setTitle(R.string.compress_album_view);
        }
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(gridLayoutManager);
        mAdater = new AdapterAlbumSelect(albums,this);
        mAdater.setOnItemListener(this);
        recyclerView.setAdapter(mAdater);

        Intent intent = getIntent();
        if (intent == null) {
            finish();
        }
        Constants.limit = intent.getIntExtra(Constants.INTENT_EXTRA_LIMIT, Constants.DEFAULT_LIMIT);
        if(ActivityCompat.checkSelfPermission(this,requiredPermissions[0])==PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this,requiredPermissions,Constants.READ_EXTERNAL_STORAGE);
            return;
        }
        loadAlbums();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == Constants.READ_EXTERNAL_STORAGE){

           if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                loadAlbums();
           }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Constants.REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            setResult(RESULT_OK, data);
            finish();
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(RESULT_CANCELED);
        finish();
    }

    public void loadAlbums(){
        AlbumLoaderRunnable runnable = new AlbumLoaderRunnable();
        thread = new Thread(runnable);
        thread.start();
    }

    @Override
    public void onItemListener(View view, int position) {
        Intent intent = new Intent(getApplicationContext(), ImageSelectActivity.class);
        intent.putExtra(Constants.INTENT_EXTRA_ALBUM, albums.get(position).name);
        startActivityForResult(intent, Constants.REQUEST_CODE);
    }

    private class AlbumLoaderRunnable implements Runnable{
        @Override
        public void run() {

           Cursor cursor = getApplicationContext().getContentResolver()
                    .query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,projection,
                            null,null,MediaStore.Images.Media.DATE_ADDED);

            ArrayList<Album> temp = new ArrayList<>(cursor.getCount());
            HashSet<String> albumSet = new HashSet<>();
            File file;
            if(cursor.moveToLast()){
                do {

                    String album = cursor.getString(cursor.getColumnIndex(projection[0]));
                    String image = cursor.getString(cursor.getColumnIndex(projection[1]));
                    file = new File(image);
                    if(file.exists() && !albumSet.contains(album)){
                        temp.add(new Album(album,image));
                        albumSet.add(album);
                    }
                }while (cursor.moveToPrevious());
            }

            cursor.close();

            if(albums == null){
                albums = new ArrayList<>();
            }
            albums.addAll(temp);
            Message message = handler.obtainMessage();
            message.what = Constants.FETCH_COMPLETED;
            message.sendToTarget();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case Constants.FETCH_COMPLETED:
                        mAdater.notifyDataSetChanged();
                        break;
                }
            }
        };
    }
}
