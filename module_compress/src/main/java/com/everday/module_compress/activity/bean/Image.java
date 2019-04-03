package com.everday.module_compress.activity.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
* @author Everday
* @emil wangtaohandsome@gmail.com
* create at 2019/3/21
* description: 图片实体类
*/

public class Image implements Parcelable {
    public long id;
    public String name;
    public String path;
    public boolean isSelected;

    public Image(long id, String name, String path) {
        this.id = id;
        this.name = name;
        this.path = path;
    }


    protected Image(Parcel in) {
        id = in.readLong();
        name = in.readString();
        path = in.readString();
    }

    public static final Creator<Image> CREATOR = new Creator<Image>() {
        @Override
        public Image createFromParcel(Parcel in) {
            return new Image(in);
        }

        @Override
        public Image[] newArray(int size) {
            return new Image[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(name);
        dest.writeString(path);
    }
}
