package com.everday.module_compress.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.everday.module_compress.R;
import com.everday.module_compress.activity.adapter.AdapterAlbumSelect;
import com.everday.module_compress.activity.bean.Image;
import com.everday.module_compress.utils.Constants;

import java.io.File;
import java.util.ArrayList;

public class ImageSelectActivity extends AppCompatActivity implements AdapterAlbumSelect.OnItemListener {
    private String[] requiredPermissions = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE};
    private Handler handler;
    private RecyclerView recyclerView;
    private AdapterAlbumSelect mAdater;
    private ActionBar actionBar;
    private ArrayList<Image> images;
    private String album;
    private Thread thread;
    //当前已选中的数量
    private int countSelected;
    private final String[] projection = new String[]{ MediaStore.Images.Media._ID, MediaStore.Images.Media.DISPLAY_NAME, MediaStore.Images.Media.DATA };
    private ActionMode actionMode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.compress_activity_image_select);

        initView();
    }

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
        mAdater = new AdapterAlbumSelect(images,this);
        mAdater.setOnItemListener(this);
        recyclerView.setAdapter(mAdater);
        Intent intent = getIntent();
        if(intent == null){
            finish();
        }
        album = intent.getStringExtra(Constants.INTENT_EXTRA_ALBUM);
        permission();
    }

    private void permission(){
        if(ActivityCompat.checkSelfPermission(this,requiredPermissions[0])==PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this,requiredPermissions,Constants.READ_EXTERNAL_STORAGE);
            return;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == Constants.READ_EXTERNAL_STORAGE){
            if(grantResults.length> 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                loadAlbums();
            }
        }
    }

    private void loadAlbums() {
        ImageLoadRunable runable = new ImageLoadRunable();
        if(thread == null){
            thread = new Thread(runable);
        }
        thread.start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

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

    @Override
    public void onItemListener(View view, int position) {
        if(actionMode == null){
            actionMode = this.startActionMode(callback);
        }
        toggleSelection(position);
        actionMode.setTitle(String.format(getString(R.string.compress_selected),countSelected+""));
    }

    private class ImageLoadRunable implements Runnable{
        @Override
        public void run() {

            Cursor cursor = getApplicationContext().getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection
                    , MediaStore.Images.Media.BUCKET_DISPLAY_NAME + "=?"
                    , new String[]{album}
                    , MediaStore.Images.Media.DATE_ADDED);
            ArrayList<Image> temp = new ArrayList<>(cursor.getCount());
            File file;
            if(cursor!=null&&cursor.moveToLast()){
                do {
                    long id = cursor.getLong(cursor.getColumnIndex(projection[0]));
                    String name = cursor.getString(cursor.getColumnIndex(projection[1]));
                    String path = cursor.getString(cursor.getColumnIndex(projection[2]));

                    file = new File(path);
                    if(file.exists()){
                        temp.add(new Image(id,name,path));
                    }
                }while (cursor.moveToPrevious());
            }
            cursor.close();
            if(images == null){
                images = new ArrayList<>();
            }
            images.clear();
            images.addAll(temp);
            Message message = handler.obtainMessage();
            message.what = Constants.FETCH_COMPLETED;
            message.sendToTarget();
        }
    }

    private ActionMode.Callback callback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            MenuInflater menuInflater = mode.getMenuInflater();
            menuInflater.inflate(R.menu.compress_menu_contextual_action_bar,menu);
            actionMode = mode;
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            int i = item.getItemId();
            if(i == R.id.menu_item_add_image){
                sendIntent();
            }
            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            if(countSelected>0){
                deselectAll();
            }
        }
    };

    private void sendIntent() {
        Intent intent = new Intent();
        intent.putParcelableArrayListExtra(Constants.INTENT_EXTRA_IMAGES, getSelected());
        setResult(RESULT_OK, intent);
        finish();
    }

    /**
     * 获取当前选中的图片
     * @return
     */
    private ArrayList<Image> getSelected() {
        ArrayList<Image> selectedImages = new ArrayList<>();
        for (int i = 0, l = images.size(); i < l; i++) {
            if (images.get(i).isSelected) {
                selectedImages.add(images.get(i));
            }
        }
        return selectedImages;
    }
    /**
     * 选中当前点击图片
     * @param position
     */
    private void toggleSelection(int position){
        if(!images.get(position).isSelected && countSelected >= Constants.limit){
            Toast.makeText(getApplicationContext(), String.format(getString(R.string.compress_limit_exceeded),Constants.limit), Toast.LENGTH_SHORT).show();
        }
        images.get(position).isSelected = !images.get(position).isSelected;
        countSelected = images.get(position).isSelected == true ? countSelected++:countSelected--;
        mAdater.notifyDataSetChanged();
    }

    /**
     * 清空选中图片
     */
    private void deselectAll(){
        for (int i = 0, l = images.size(); i < l; i++) {
            images.get(i).isSelected = false;
        }
        countSelected = 0;
        mAdater.notifyDataSetChanged();
    }
}
