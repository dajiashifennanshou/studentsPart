package com.xdk.df.parentend.ui.login.classLife;

import android.view.View;
import android.widget.LinearLayout;

import com.xdk.df.parentend.R;
import com.xdk.df.parentend.base.BaseActivity;

public class ClassLifeActivity extends BaseActivity implements View.OnClickListener{

    private LinearLayout backll;
    @Override
    public void initView() {
        setContentView(R.layout.activity_class_life);
        findView();
        backll.setOnClickListener(this);
    }

    private void findView() {
        backll = (LinearLayout) findViewById(R.id.class_life_back);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.class_life_back:
                finish();
                break;
            case R.id.more_user_out:
                break;
        }
    }
}
