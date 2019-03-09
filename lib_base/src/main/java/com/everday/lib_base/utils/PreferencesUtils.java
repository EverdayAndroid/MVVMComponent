package com.everday.lib_base.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;

/**
* @author Everday
* @emil wangtaohandsome@gmail.com
* create at 2019/3/5
* description: 文件存储
*/

public class PreferencesUtils {
    private static String SHARED_PREF_NAME = "assistant_preferences";
    private Context mContext;
    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;
    public static void initConext(Context mContext){
//        Context.MODE_PRIVATE：为默认操作模式,代表该文件是私有数据,只能被应用本身访问,在该模式下,写入的内容会覆盖原文件的内容
//        Context.MODE_APPEND：模式会检查文件是否存在,存在就往文件追加内容,否则就创建新文件.
//        Context.MODE_WORLD_READABLE和Context.MODE_WORLD_WRITEABLE用来控制其他应用是否有权限读写该文件.
//        MODE_WORLD_READABLE：表示当前文件可以被其他应用读取. 4.2废除
//        MODE_WORLD_WRITEABLE：表示当前文件可以被其他应用写入 4.2废除
//        MODE_MULTI_PROCESS  6.0废除
        sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    /**
     * 获取文件数据
     * @param key
     * @param defaultValue
     * @return
     */
    public static Object get(String key,Object defaultValue){
        if(defaultValue instanceof String){
            return sharedPreferences.getString(key,(String) defaultValue);
        }else if(defaultValue instanceof Integer){
            return sharedPreferences.getInt(key, (Integer) defaultValue);
        }else if(defaultValue instanceof Boolean){
            return sharedPreferences.getBoolean(key, (Boolean) defaultValue);
        }else if(defaultValue instanceof  Long){
            return sharedPreferences.getLong(key, (Long) defaultValue);
        }else if(defaultValue instanceof Float){
            return sharedPreferences.getFloat(key, (Float) defaultValue);
        }else if(defaultValue instanceof Set){
            return sharedPreferences.getStringSet(key, (Set<String>) defaultValue);
        }
        return null;
    }

    /**
     * 保存文件数据
     * @param key
     * @param defaultValue
     * @param aysn   异步进行还是同步进行
     */
    public static void put(String key,Object defaultValue,Boolean aysn){
        if(defaultValue instanceof String){
            editor.putString(key, (String) defaultValue);
        }else if(defaultValue instanceof Integer){
            editor.putInt(key, (Integer) defaultValue);
        }else if(defaultValue instanceof Boolean){
            editor.putBoolean(key, (Boolean) defaultValue);
        }else if(defaultValue instanceof  Long){
            editor.putLong(key, (Long) defaultValue);
        }else if(defaultValue instanceof Float){
            editor.putFloat(key, (Float) defaultValue);
        }else if(defaultValue instanceof Set){
            editor.putStringSet(key, (Set<String>) defaultValue);
        }
        if(aysn){
            editor.apply();
        }else {
            editor.commit();
        }
    }

    /**
     * 清除文件存储
     */
    public static void clearPreferences(){
        editor.clear().apply();
    }

    /**
     * 删除指定key值本地文件
     * @param key
     */
    public static void remove(String... key){
        for (String str:key){
            editor.remove(str);
        }
        editor.commit();
    }
}
