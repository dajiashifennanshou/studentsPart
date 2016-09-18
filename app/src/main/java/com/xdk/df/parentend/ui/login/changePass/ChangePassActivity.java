package com.xdk.df.parentend.ui.login.changePass;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.xdk.df.parentend.R;
import com.xdk.df.parentend.base.BaseActivity;
import com.xdk.df.parentend.data.HttpData;
import com.xdk.df.parentend.http.HttpHelper;
import com.xdk.df.parentend.ui.login.LoginActivity;
import com.xdk.df.parentend.utils.SharedPreferenceHelper;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class ChangePassActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout backll;
    private Button saveChangeBt;
    private EditText oldPassTx, newPassTx, repeatPassTx;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
               toastShort(R.string.str_change_pass_fail);
            }else{
                toastShort(R.string.str_change_pass_success);
                SharedPreferenceHelper.putCurrentUser(ChangePassActivity.this,null);
                goActivity(LoginActivity.class);
                finish();
            }
        }
    };
    @Override
    public void initView() {
        setContentView(R.layout.activity_change_pass);
        findView();
        backll.setOnClickListener(this);
        saveChangeBt.setOnClickListener(this);
    }

    private void findView() {
        saveChangeBt = (Button) findViewById(R.id.change_pass_save_change);
        backll = (LinearLayout) findViewById(R.id.change_pass_back);
        oldPassTx = (EditText) findViewById(R.id.change_pass_oldpass);
        newPassTx = (EditText) findViewById(R.id.change_pass_newpass);
        repeatPassTx = (EditText) findViewById(R.id.change_pass_repeatpass);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.change_pass_back:
                finish();
                break;
            case R.id.change_pass_save_change:
                if(!isCanSave()){
                    break;
                }
                String sql = "update AppUser set userpwd = '" + newPassTx.getText().
                        toString() + "' where accountid = '" + SharedPreferenceHelper.getCurrentUser(this)
                        .getAccountid() + "'and schoolcode = '" + SharedPreferenceHelper.getCurrentUser(this).getSchoolcode() + "'";
                HttpHelper.changeDada(this, handler, sql);
                break;
        }
    }

    private boolean isCanSave() {
        if (TextUtils.isEmpty(oldPassTx.getText())) {
            toastShort(R.string.str_change_pass_nopass);
            return false;
        }
        if (TextUtils.isEmpty(newPassTx.getText())) {
            toastShort(R.string.str_change_pass_nonewpass);
            return false;
        }
        if (TextUtils.isEmpty(repeatPassTx.getText())) {
            toastShort(R.string.str_change_pass_repeat_newpass);
            return false;
        }
        if (!newPassTx.getText().toString().equals(repeatPassTx.getText().toString())) {
            toastShort(R.string.str_change_pass_repeat_wrong);
            return false;
        }
        if (!oldPassTx.getText().toString().equals(SharedPreferenceHelper.getCurrentUser(this).getUserpwd())) {
            toastShort(R.string.str_change_pass_wrong);
            return false;
        }
        return  true;
    }
}
