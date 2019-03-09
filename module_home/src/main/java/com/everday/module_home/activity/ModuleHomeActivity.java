package com.everday.module_home.activity;

import android.os.Bundle;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.everday.lib_base.base.BaseActivity;
import com.everday.lib_base.base.BaseModel;
import com.everday.lib_base.router.RouterActivityPath;
import com.everday.module_home.R;
import com.everday.module_home.BR;
import com.everday.module_home.databinding.HomeActivityModuleHomeBinding;
@Route(path = RouterActivityPath.MODULE_HOME.MODULE_HOMEACTVITY)
public class ModuleHomeActivity extends BaseActivity<HomeActivityModuleHomeBinding,BaseModel>{
//    @BindView(R2.id.home_tv)
//    TextView textView;
    @Autowired(name = "name")
    String name;
    @Autowired(name = "age")
    int age;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_module_home);
//    }

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.home_activity_module_home;
    }

    @Override
    public int initVariableId() {
        return 0;
    }

    @Override
    public void initData() {
        super.initData();
        setObjectVariable(BR.name,name);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(this, "姓名："+name+"   年龄："+age, Toast.LENGTH_SHORT).show();
    }
}
