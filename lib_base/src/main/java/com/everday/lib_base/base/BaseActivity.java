package com.everday.lib_base.base;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import com.alibaba.android.arouter.launcher.ARouter;
import com.everday.lib_base.router.RouterActivityPath;
import com.everday.lib_base.utils.ActivityManagement;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseActivity<V extends ViewDataBinding,VM extends BaseViewModel> extends RxAppCompatActivity implements IBaseActivity {
    protected V binding;
    protected VM baseModel;
    private int viewModelId;
    private Unbinder unbinder;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //页面接收的参数方法
        initParam();
        //私有的初始化Databinding和ViewModel方法
        intiViewDataBinding(savedInstanceState);

        initData();
        //初始化界面观察者监听
        initViewObservable();
        ActivityManagement.getInstance().addActivity(this);
    }

    /**
     * 依赖注入
     * @param savedInstanceState
     */
    private void intiViewDataBinding(Bundle savedInstanceState) {
        int layoutResID = initContentView(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, layoutResID);
        viewModelId = initVariableId();
        baseModel = initModel();
        if(baseModel == null){
            Class clzz;
            //获取泛型类型
            Type type = getClass().getGenericSuperclass();
            if(type instanceof ParameterizedType){
                ParameterizedType parameterizedType = (ParameterizedType) type;
                clzz = (Class) parameterizedType.getActualTypeArguments()[1];
            }else{
                clzz = BaseViewModel.class;
            }
            baseModel = (VM) createViewModel(this, clzz);
        }
        binding.setVariable(viewModelId,baseModel);
        unbinder = ButterKnife.bind(this);
        if(RouterActivityPath.OPEN_COMPONENT) {
            ARouter.getInstance().inject(this);
        }
    }

    //注册ViewModel与View的契约UI回调时间
    private void registorUIChangeLiveDataCallBack(){

    }


    //TODO  可能要删除此方法
    public void setObjectVariable(int brId,Object ojb){
        binding.setVariable(brId,ojb);
    }

    /**
     * 返回当前DataBinding
     * @return ViewDataBinding
     */
    public V getBinding(){
        return binding;
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
    @Override
    public void initParam() {

    }
    public VM initModel(){
        return null;
    }


    //刷新布局
    public void refreshLayout() {
        if (binding != null) {
            binding.setVariable(viewModelId, viewModelId);
        }
    }

    /**
     * 创建viewModel
     * @param activity
     * @param clzz
     * @param <T>
     * @return
     */
    public <T extends BaseViewModel> T createViewModel(FragmentActivity activity, Class<T> clzz){
        return ViewModelProviders.of(activity).get(clzz);
    }

    @Override
    public void initViewObservable() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        //删除表达式变量的绑定侦听器。
        binding.unbind();
        ActivityManagement.getInstance().finishActivity(this);
    }
}
