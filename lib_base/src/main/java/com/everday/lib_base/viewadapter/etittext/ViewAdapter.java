package com.everday.lib_base.viewadapter.etittext;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
* @author Everday
* @emil wangtaohandsome@gmail.com
* create at 2019/3/22
* description: 编辑框适配器
*/

public class ViewAdapter {
    /**
     * EditText重新获取焦点的事件绑定
     * @param editText
     * @param needRequestFocus
     */
    @BindingAdapter(value = {"requestFocus"},requireAll = false)
    public static void requestFocusCommand(EditText editText,boolean needRequestFocus){
        if(needRequestFocus){
            editText.setSelection(editText.getText().length());
            editText.requestFocus();
            InputMethodManager imm = (InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(editText,InputMethodManager.SHOW_IMPLICIT);
        }
        editText.setFocusableInTouchMode(needRequestFocus);
    }
}
