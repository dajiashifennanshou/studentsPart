package com.xdk.df.parentend.ui.login;

import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.xdk.df.parentend.MainActivity;
import com.xdk.df.parentend.R;
import com.xdk.df.parentend.base.BaseActivity;
import com.xdk.df.parentend.http.HttpHelper;
import com.xdk.df.parentend.utils.ExitAppHelper;
import com.xdk.df.parentend.utils.SharedPreferenceHelper;

public class LoginActivity extends BaseActivity implements View.OnClickListener{
    private EditText schoolCodeEdit;
    private EditText studentCodeEdit;
    private EditText passwordEdit;
    private Button loginBt;
    private ExitAppHelper appExitHelper;
    private Handler loginHnadler = new Handler();
    @Override
    public void initView() {
        setContentView(R.layout.activity_login);
        loginBt = (Button) findViewById(R.id.login_button_login);
        schoolCodeEdit = (EditText) findViewById(R.id.login_school_number);
        passwordEdit = (EditText) findViewById(R.id.login_user_password);
        studentCodeEdit = (EditText) findViewById(R.id.login_student_number);
        loginBt.setOnClickListener(this);
        appExitHelper=new ExitAppHelper(this);
        schoolCodeEdit.setText(SharedPreferenceHelper.getLoginSchoolCode(this));
        studentCodeEdit.setText(SharedPreferenceHelper.getLoginStudentCode(this));
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(appExitHelper.onClickBack(keyCode, event)) return true;
        return super.onKeyDown(keyCode, event);
    }
    @Override
    public void onClick(View view) {
        if(TextUtils.isEmpty(schoolCodeEdit.getText())){
            toastShort(R.string.str_login_no_school_code);
            return;
        }
        if(TextUtils.isEmpty(passwordEdit.getText())){
            toastShort(R.string.str_login_no_password);
            return;
        }
        if(TextUtils.isEmpty(studentCodeEdit.getText())){
            toastShort(R.string.str_login_no_user_name);
            return;
        }
        HttpHelper.userLogin(this,loginHnadler,schoolCodeEdit.getText().toString(), studentCodeEdit.getText().toString(), passwordEdit.getText().toString());
        SharedPreferenceHelper.putLoginSchoolCode(this,schoolCodeEdit.getText().toString());
        SharedPreferenceHelper.putLoginStudentCode(this,studentCodeEdit.getText().toString());
    }
}
