package com.everday.module_compress.activity.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.everday.module_compress.R;
import com.everday.module_compress.activity.bean.Album;
import com.everday.module_compress.activity.bean.Image;

import java.util.ArrayList;

public class AdapterAlbumSelect extends RecyclerView.Adapter<AdapterAlbumSelect.ViewHolder> {

    private ArrayList<?> albums;
    private Context mContext;
    private RequestOptions requestOptions;
    private OnItemListener onItemListener;
    public AdapterAlbumSelect(ArrayList<?> albums, Context mContext) {
        this.albums = albums;
        this.mContext = mContext;
        requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.compress_image_placeholder);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.compress_item_album_select, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Object object = albums.get(i);
        if(object instanceof Album) {
            Album album = (Album) object;
            viewHolder.textView.setText(album.name);
            Glide.with(mContext)
                    .load(album.cover)
                    .apply(requestOptions)
                    .into(viewHolder.imageView);
        }else{
            Image image = (Image) object;
            Glide.with(mContext)
                    .load(image.path)
                    .apply(requestOptions)
                    .into(viewHolder.imageView);
        }
    }

    @Override
    public int getItemCount() {
        return albums.size();
    }

    public OnItemListener getOnItemListener() {
        return onItemListener;
    }

    public void setOnItemListener(OnItemListener onItemListener) {
        this.onItemListener = onItemListener;
    }

    public interface OnItemListener{
        void onItemListener(View view,int position);
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        FrameLayout frameLayout;
        ImageView imageView;
        TextView textView;
        public ViewHolder(View itemView) {
            super(itemView);
            frameLayout = itemView.findViewById(R.id.frame_layout_album_select);
            imageView = itemView.findViewById(R.id.image_view_album_image);
            textView = itemView.findViewById(R.id.text_view_album_name);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(onItemListener == null){return;}
                    onItemListener.onItemListener(view,getLayoutPosition());
                }
            });
        }
    }
}
