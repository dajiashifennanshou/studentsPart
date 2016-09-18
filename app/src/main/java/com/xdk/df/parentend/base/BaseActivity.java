package com.xdk.df.parentend.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.widget.Toast;

import com.xdk.df.parentend.application.XdkApplication;
import com.xdk.df.parentend.http.okhttp.OkHttpUtils;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/8/5.
 */
public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        XdkApplication.getInstance().addActivity(this);
        initView();
    }

    public abstract void initView();

    public void goActivity(Class activity) {
        startActivity(new Intent(this, activity));
    }

    public void toastShort(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    public void toastShort(int s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    public void toastLong(String s) {
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }

    public void toastLong(int s) {
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void finish() {
        OkHttpUtils.getInstance().cancelTag(this);
        super.finish();
    }
}
