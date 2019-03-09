package com.everday.lib_base.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.alibaba.android.arouter.launcher.ARouter;
import com.everday.lib_base.router.RouterActivityPath;
import com.everday.lib_base.utils.ActivityManagement;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseActivity<V extends ViewDataBinding,VM extends BaseModel> extends AppCompatActivity {
    private V viewDataBinding;
    private VM baseModel;
    private int viewModelId;
    private Unbinder unbinder;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intiViewDataBinding(savedInstanceState);

        initData();
        ActivityManagement.getInstance().addActivity(this);
    }

    /**
     * 依赖注入
     * @param savedInstanceState
     */
    private void intiViewDataBinding(Bundle savedInstanceState) {
        int layoutResID = initContentView(savedInstanceState);
        viewDataBinding = DataBindingUtil.setContentView(this, layoutResID);
        viewModelId = initVariableId();
        baseModel = initModel();
        viewDataBinding.setVariable(viewModelId,baseModel);
        unbinder = ButterKnife.bind(this);
        if(RouterActivityPath.OPEN_COMPONENT) {
            ARouter.getInstance().inject(this);
        }
    }

    public void setObjectVariable(int brId,Object ojb){
        viewDataBinding.setVariable(brId,ojb);
    }

    /**
     * 返回当前DataBinding
     * @return ViewDataBinding
     */
    public V getViewDataBinding(){
        return viewDataBinding;
    }

    /**
     * 初始化ViewModel的id
     *
     * @return BR的id
     */
    public abstract int initVariableId();

    /**
     * 初始化根布局
     *
     * @return 布局layout的id
     */
    public abstract int initContentView(Bundle savedInstanceState);

    /**
     * 页面数据加载
     */
    public void initData(){

    }

    public VM initModel(){
        return null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        //删除表达式变量的绑定侦听器。
        viewDataBinding.unbind();
        ActivityManagement.getInstance().finishActivity(this);
    }
}
