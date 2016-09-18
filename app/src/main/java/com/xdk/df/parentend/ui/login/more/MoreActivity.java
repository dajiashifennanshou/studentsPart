package com.xdk.df.parentend.ui.login.more;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.xdk.df.parentend.R;
import com.xdk.df.parentend.base.BaseActivity;
import com.xdk.df.parentend.ui.login.LoginActivity;
import com.xdk.df.parentend.ui.login.changePass.ChangePassActivity;
import com.xdk.df.parentend.ui.login.contactUs.ContactUsActivity;
import com.xdk.df.parentend.utils.DataCleanManager;
import com.xdk.df.parentend.utils.FileUtils;
import com.xdk.df.parentend.utils.SharedPreferenceHelper;
import com.xdk.df.parentend.utils.ToastUtils;

/**
 * Created by Administrator on 2016/8/17.
 */
public class MoreActivity extends BaseActivity implements View.OnClickListener {
    private Button outBt;
    private LinearLayout backll;

    @Override
    public void initView() {
        setContentView(R.layout.activity_more);
        findView();
        backll.setOnClickListener(this);
        outBt.setOnClickListener(this);
    }

    private void findView() {
        outBt = (Button) findViewById(R.id.more_user_out);
        backll = (LinearLayout) findViewById(R.id.more_back);
    }
    public void moreOnclick(View view){
        switch (view.getId()){
            case R.id.more_contact_us:
                goActivity(ContactUsActivity.class);
            break;
            case R.id.more_change_pass:
                goActivity(ChangePassActivity.class);
                break;
            case R.id.more_change_clear_cache:
                FileUtils fileUtils = new FileUtils(this);
                fileUtils.deleteFile();
                DataCleanManager.cleanExternalCache(this);
                ToastUtils.showShort(this,"已清除缓存！");
                break;
            case R.id.more_change_update:
                ToastUtils.showShort(this,"已是最新版本！");
                break;
        }
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.more_back:
                finish();
                break;
            case R.id.more_user_out:
                SharedPreferenceHelper.putCurrentUser(this, null);
                goActivity(LoginActivity.class);
                toastShort(getString(R.string.str_user_out));
                finish();
                break;
        }
    }
}
